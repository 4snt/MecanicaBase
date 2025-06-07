# gerar_diagrama.py  (coloque onde quiser)
import argparse, os, re, subprocess, sys
from collections import defaultdict

RESERVADAS = {"entity", "class", "interface", "enum", "abstract"}
ESC = lambda n: f'"{n}"' if n.lower() in RESERVADAS else n

PAT_CLASSE  = re.compile(r'(?:public\s+)?(?:abstract\s+)?class\s+(\w+)')
PAT_PKG     = re.compile(r'package\s+([\w\.]+);')
PAT_ATTR    = re.compile(r'(public|protected|private)\s+([\w<>]+)\s+(\w+);')
PAT_MTD     = re.compile(r'(public|protected|private)\s+[\w<>\[\]]+\s+(\w+)\s*\(')
PAT_EXTENDS = re.compile(r'extends\s+(\w+)')
PAT_IMPL    = re.compile(r'implements\s+([\w,\s]+)')

def extrair_infos(src_dir: str):
    """varre .java e devolve dicionários de classes, heranças, composições"""
    nomes, classes = set(), {}
    # primeiro coletar todos os nomes de classe
    for root, _, files in os.walk(src_dir):
        for f in files:
            if f.endswith(".java"):
                txt = open(os.path.join(root, f), encoding="utf-8").read()
                m = PAT_CLASSE.search(txt)
                if m: nomes.add(m.group(1))

    herancas, comps = [], []
    for root, _, files in os.walk(src_dir):
        for f in files:
            if f.endswith(".java"):
                path = os.path.join(root, f)
                txt   = open(path, encoding="utf-8").read()
                m_cl  = PAT_CLASSE.search(txt)
                if not m_cl: continue
                nome  = m_cl.group(1)
                attrs = PAT_ATTR.findall(txt)
                mtdds = [m[1] for m in PAT_MTD.findall(txt)]
                exts  = PAT_EXTENDS.findall(txt)
                impls = PAT_IMPL.findall(txt)
                pais  = exts + ([x.strip() for i in impls for x in i.split(",")] if impls else [])
                for p in pais: herancas.append((p, nome))

                for _, tipo, _ in attrs:
                    m = re.search(r'List<(\w+)>', tipo)
                    if m and m.group(1) in nomes: comps.append((nome, m.group(1), "*"))
                    elif tipo in nomes:          comps.append((nome, tipo, "1"))

                pkg = PAT_PKG.search(txt)
                classes[nome] = {"attrs": attrs, "mtdds": mtdds, "pkg": pkg.group(1) if pkg else "default"}

    return classes, herancas, comps

def gerar_puml(classes, herancas, comps, outfile):
    with open(outfile, "w", encoding="utf-8") as f:
        f.write("@startuml\nskinparam dpi 150\n")
        f.write("skinparam classAttributeIconSize 0\n")
        f.write("skinparam classFontSize 10\n")
        f.write("skinparam wrapWidth 100\ntop to bottom direction\n\n")

        for nome, info in classes.items():
            f.write(f"class {ESC(nome)} {{\n")
            for vis, tipo, var in info["attrs"]:
                simb = "+" if vis == "public" else "-" if vis == "private" else "#"
                f.write(f"  {simb} {var} : {tipo}\n")
            for m in info["mtdds"]:
                f.write(f"  +{m}()\n")
            f.write("}\n")

        for p, f_ in herancas:
            f.write(f"{ESC(p)} <|-- {ESC(f_)}\n")
        for o, d, mult in comps:
            f.write(f'{ESC(o)} --> "{mult}" {ESC(d)}\n')

        f.write("\n@enduml\n")
    print("✅  uml_diagrama.puml gerado")

def gerar_svg(puml_file, maven_plantuml):
    jar = maven_plantuml
    if not os.path.isfile(jar):
        print("⚠️  plantuml.jar não encontrado:", jar)
        return
    subprocess.run(["java", "-jar", jar, "-tsvg", puml_file], check=True)
    print("🖼️  uml_diagrama.svg criado")

def main():
    ap = argparse.ArgumentParser(description="Gera diagrama de classes PlantUML")
    ap.add_argument("-s", "--src",  default="src/main/java", help="Diretório raiz dos .java")
    ap.add_argument("-o", "--out",  default="documentation/diagrama_de_classe/uml_diagrama.puml")
    ap.add_argument("--svg", action="store_true", help="Também gerar SVG")
    ap.add_argument("--jar", default="target/plantuml/plantuml-1.2024.3.jar",
                    help="Caminho do plantuml.jar (quando usar --svg)")
    args = ap.parse_args()

    classes, herancas, comps = extrair_infos(args.src)
    gerar_puml(classes, herancas, comps, args.out)
    if args.svg:
        gerar_svg(args.out, args.jar)

if __name__ == "__main__":
    main()
