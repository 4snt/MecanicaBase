# gerar_diagrama.py
import argparse, os, re, subprocess

RESERVADAS = {"entity", "class", "interface", "enum", "abstract"}
ESC = lambda n: f'"{n}"' if n.lower() in RESERVADAS else n

PAT_TIPO     = re.compile(r'(?:public\s+)?(?:abstract\s+)?(class|interface|enum)\s+(\w+)')
PAT_PKG      = re.compile(r'package\s+([\w\.]+);')
PAT_ATTR     = re.compile(r'(public|protected|private)\s+([\w<>\[\], ?]+)\s+(\w+);')
PAT_MTD      = re.compile(r'(public|protected|private)\s+[\w<>\[\]]+\s+(\w+)\s*\(')
PAT_TODOS_MTD = re.compile(r'(public|protected|private)\s+([\w<>\[\]]+)\s+(\w+)\s*\(([^)]*)\)')
PAT_EXTENDS  = re.compile(r'extends\s+(\w+)')
PAT_IMPL     = re.compile(r'implements\s+([\w,\s]+)')

def extrair_infos(src_dir: str):
    nomes, tipos, classes = set(), {}, {}
    herancas, comps, dependencias = [], [], []

    for root, _, files in os.walk(src_dir):
        for f in files:
            if f.endswith(".java"):
                with open(os.path.join(root, f), encoding="utf-8") as arq:
                    txt = arq.read()
                    for tipo, nome in PAT_TIPO.findall(txt):
                        nomes.add(nome)
                        tipos[nome] = tipo

    for root, _, files in os.walk(src_dir):
        for f in files:
            if f.endswith(".java"):
                with open(os.path.join(root, f), encoding="utf-8") as arq:
                    txt = arq.read()
                    m_cl = PAT_TIPO.search(txt)
                    if not m_cl: continue
                    tipo, nome = m_cl.groups()
                    attrs = PAT_ATTR.findall(txt)
                    mtdds = [m[1] for m in PAT_MTD.findall(txt)]
                    exts  = PAT_EXTENDS.findall(txt)
                    impls = PAT_IMPL.findall(txt)
                    pais  = exts + ([x.strip() for i in impls for x in i.split(",")] if impls else [])
                    for p in pais: herancas.append((p, nome))

                    for _, tipo_attr, _ in attrs:
                        m = re.search(r'List\s*<\s*(\w+)\s*>', tipo_attr)
                        if m and m.group(1) in nomes: comps.append((nome, m.group(1), "*"))
                        elif tipo_attr in nomes:      comps.append((nome, tipo_attr, "1"))

                    for vis, tipo_retorno, nome_mtd, parametros in PAT_TODOS_MTD.findall(txt):
                        for param in parametros.split(","):
                            param = param.strip()
                            if not param: continue
                            tipo_param = param.split()[0].replace("[]", "").replace("<", "").replace(">", "")
                            if tipo_param in nomes and tipo_param != nome:
                                dependencias.append((nome, tipo_param))
                        tipo_ret = tipo_retorno.replace("[]", "").replace("<", "").replace(">", "")
                        if tipo_ret in nomes and tipo_ret != nome:
                            dependencias.append((nome, tipo_ret))

                    pkg = PAT_PKG.search(txt)
                    classes[nome] = {
                        "tipo": tipos.get(nome, "class"),
                        "attrs": attrs,
                        "mtdds": mtdds,
                        "pkg": pkg.group(1) if pkg else "default"
                    }

    return classes, herancas, comps, dependencias

def gerar_puml_completo(classes, herancas, comps, dependencias, outfile):
    with open(outfile, "w", encoding="utf-8") as f:
        f.write("@startuml\nskinparam dpi 150\n")
        f.write("skinparam classAttributeIconSize 0\n")
        f.write("skinparam classFontSize 10\n")
        f.write("skinparam wrapWidth 100\ntop to bottom direction\n\n")

        for nome, info in classes.items():
            estereotipo = info['tipo']
            if estereotipo == "interface":
                f.write(f"interface {ESC(nome)} <<interface>> {{\n")
            elif estereotipo == "enum":
                f.write(f"enum {ESC(nome)} <<enum>> {{\n")
            else:
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
        for origem, destino in dependencias:
            f.write(f"{ESC(origem)} ..> {ESC(destino)} : <<use>> #888888\n")

        f.write("\n@enduml\n")
    print("✅  uml_diagrama_completo.puml gerado")

def gerar_puml_relacoes(classes, herancas, comps, dependencias, outfile):
    with open(outfile, "w", encoding="utf-8") as f:
        f.write("@startuml\nskinparam dpi 150\n")
        f.write("top to bottom direction\n\n")

        for nome in classes:
            f.write(f"class {ESC(nome)}\n")

        for p, f_ in herancas:
            f.write(f"{ESC(p)} <|-- {ESC(f_)}\n")
        for o, d, mult in comps:
            f.write(f'{ESC(o)} --> "{mult}" {ESC(d)}\n')
        for origem, destino in dependencias:
            f.write(f"{ESC(origem)} ..> {ESC(destino)} : <<use>> #888888\n")

        f.write("\n@enduml\n")
    print("📄  uml_diagrama_relacoes.puml gerado")

def gerar_svg(puml_file, maven_plantuml):
    if not os.path.isfile(maven_plantuml):
        print("⚠️  plantuml.jar não encontrado:", maven_plantuml)
        return
    subprocess.run(["java", "-jar", maven_plantuml, "-tsvg", puml_file], check=True)
    print(f"🖼️  SVG gerado para: {puml_file}")

def main():
    ap = argparse.ArgumentParser(description="Gera dois diagramas PlantUML: completo e apenas relações")
    ap.add_argument("-s", "--src",  default="src/main/java", help="Diretório raiz dos .java")
    ap.add_argument("--outdir", default="documentation/diagrama_de_classe", help="Pasta de saída")
    ap.add_argument("--svg", action="store_true", help="Também gerar SVGs")
    ap.add_argument("--jar", default="target/plantuml/plantuml-1.2024.3.jar", help="plantuml.jar")
    args = ap.parse_args()

    os.makedirs(args.outdir, exist_ok=True)
    completo = os.path.join(args.outdir, "/documentation/diagrama_de_classe/pumls/uml_diagrama_completo.puml")
    relacoes = os.path.join(args.outdir, "/documentation/diagrama_de_classe/pumls/uml_diagrama_relacoes.puml")

    classes, herancas, comps, dependencias = extrair_infos(args.src)

    gerar_puml_completo(classes, herancas, comps, dependencias, completo)
    gerar_puml_relacoes(classes, herancas, comps, dependencias, relacoes)

    if args.svg:
        gerar_svg(completo, args.jar)
        gerar_svg(relacoes, args.jar)

if __name__ == "__main__":
    main()
