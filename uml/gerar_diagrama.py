import os
import re
import subprocess
from math import ceil

CAMINHO_JAVA = r"C:\Users\Murilo Santiago\Documents\NetBeansProjects\MecanicaBase\src\main\java"
DIR_SAIDA = os.path.join(os.getcwd(), "uml_blocos")
CLASSES_POR_BLOCO = 8
GERAR_SVG = True

PALAVRAS_RESERVADAS_PLANTUML = {"entity", "class", "interface", "enum", "abstract"}

def escapar_nome(nome):
    return f'"{nome}"' if nome.lower() in PALAVRAS_RESERVADAS_PLANTUML else nome

def extrair_infos_classe(content):
    nome_classe = re.search(r'(public\s+)?(abstract\s+)?class\s+(\w+)', content)
    if not nome_classe:
        return None, [], [], [], None

    nome = nome_classe.group(3)
    pacote = re.search(r'package\s+([\w\.]+);', content)
    pacote_nome = pacote.group(1) if pacote else "default"

    atributos = re.findall(r'(public|protected|private)\s+([\w<>]+)\s+(\w+);', content)
    metodos = re.findall(r'(public|protected|private)\s+[\w<>\[\]]+\s+(\w+)\s*\(', content)

    herancas = []
    extends = re.search(r'extends\s+(\w+)', content)
    if extends:
        herancas.append(extends.group(1))

    implements = re.search(r'implements\s+([\w,\s]+)', content)
    if implements:
        herancas += [x.strip() for x in implements.group(1).split(",")]

    return nome, atributos, [m[1] for m in metodos], herancas, pacote_nome

def gerar_blocos(classes, relacoes, dir_saida):
    os.makedirs(dir_saida, exist_ok=True)
    nomes_classes = list(classes.keys())
    total_blocos = ceil(len(nomes_classes) / CLASSES_POR_BLOCO)

    for i in range(total_blocos):
        inicio = i * CLASSES_POR_BLOCO
        fim = inicio + CLASSES_POR_BLOCO
        nomes = nomes_classes[inicio:fim]
        bloco_classes = {k: classes[k] for k in nomes}

        bloco_herancas = [(pai, filho) for pai, filho in relacoes["heranca"] if pai in bloco_classes and filho in bloco_classes]
        bloco_composicoes = [(origem, destino, mult) for origem, destino, mult in relacoes["composicao"] if origem in bloco_classes and destino in bloco_classes]

        caminho_saida = os.path.join(dir_saida, f"bloco_{i+1}.puml")
        with open(caminho_saida, "w", encoding="utf-8") as f:
            f.write("@startuml\n")
            f.write("skinparam dpi 150\n")
            f.write("skinparam classAttributeIconSize 0\n")
            f.write("skinparam classFontSize 10\n")
            f.write("skinparam classFontName Courier\n")
            f.write("skinparam wrapWidth 100\n")
            f.write("top to bottom direction\n")
            f.write("skinparam linetype ortho\n\n")

            for nome, dados in bloco_classes.items():
                f.write(f"class {escapar_nome(nome)} {{\n")
                for vis, tipo, var in dados["atributos"]:
                    simbolo = "+" if vis == "public" else "-" if vis == "private" else "#"
                    f.write(f"  {simbolo} {var} : {tipo}\n")
                for metodo in dados["metodos"]:
                    f.write(f"  +{metodo}()\n")
                f.write("}\n\n")

            for pai, filho in bloco_herancas:
                f.write(f"{escapar_nome(pai)} <|-- {escapar_nome(filho)}\n")
            for origem, destino, mult in bloco_composicoes:
                f.write(f'{escapar_nome(origem)} --> "{mult}" {escapar_nome(destino)}\n')

            f.write("\n@enduml\n")

        print(f"✅ Gerado: bloco_{i+1}.puml")

        if GERAR_SVG:
            try:
                subprocess.run(["java", "-jar", "plantuml.jar", "-tsvg", caminho_saida], check=True)
                print(f"🖼️  SVG gerado para bloco_{i+1}")
            except subprocess.CalledProcessError:
                print(f"⚠️ Erro ao gerar SVG para bloco_{i+1}. Verifique o plantuml.jar")

def extrair_classes(caminho):
    classes = {}
    herancas = []
    composicoes = []
    nomes_classes = set()

    for root, _, files in os.walk(caminho):
        for file in files:
            if file.endswith(".java"):
                with open(os.path.join(root, file), encoding="utf-8") as f:
                    content = f.read()
                    match = re.search(r'class\s+(\w+)', content)
                    if match:
                        nomes_classes.add(match.group(1))

    for root, _, files in os.walk(caminho):
        for file in files:
            if file.endswith(".java"):
                with open(os.path.join(root, file), encoding="utf-8") as f:
                    content = f.read()
                    nome, atributos, metodos, pais, pacote = extrair_infos_classe(content)
                    if not nome:
                        continue
                    classes[nome] = {
                        "atributos": atributos,
                        "metodos": list(set(metodos)),
                        "pacote": pacote,
                    }

                    for pai in pais:
                        herancas.append((pai, nome))

                    for _, tipo, _ in atributos:
                        match = re.search(r'List<(\w+)>', tipo)
                        if match and match.group(1) in nomes_classes:
                            composicoes.append((nome, match.group(1), "*"))
                        elif tipo in nomes_classes:
                            composicoes.append((nome, tipo, "1"))

    return classes, {"heranca": herancas, "composicao": composicoes}

if __name__ == "__main__":
    classes, relacoes = extrair_classes(CAMINHO_JAVA)
    gerar_blocos(classes, relacoes, DIR_SAIDA)
