# 🧱 Arquitetura do Projeto

Este documento descreve a evolução da arquitetura do sistema **Mecânica Base**, partindo de uma estrutura inicial baseada em CRUD e separação por casos de uso, até uma arquitetura tradicionalmente adotada em projetos Java.

---

## 📌 Estrutura Anterior

A estrutura inicial foi baseada em uma separação típica de projetos Node.js:

- Divisão por casos de uso (`usecases`)
- Granularidade excessiva: cada operação (CRUD) em uma classe separada
- Lógica duplicada em `TerminalHandler`s
- Pacotes muito profundos e redundantes
- Dificuldade de manutenção na IDE (NetBeans)

```
src/
└── main/java/com/mycompany/mecanicabase/
    ├── domain.usecases.financeiro.agendamento
    ├── domain.usecases.financeiro.ordem_de_servico
    ├── domain.entities.financeiro
    ├── presentation.Terminal
    └── ...
```

---

## ✅ Estrutura Atual (proposta final)

Com a refatoração, adotamos uma arquitetura mais idiomática para Java desktop:

- Agrupamento por **entidade e responsabilidade**
- Redução da granularidade de classes
- Camadas mais claras: `model`, `repository`, `service`, `controller`, `view`
- Views centralizadas (CLI ou GUI)
- Menor acoplamento entre camadas
- Banco de dados simulado via `database.json` (requisito da disciplina)

```
src/
└── main/java/com/mycompany/mecanicabase/
    ├── model/
    ├── repository/
    ├── service/
    ├── controller/
    ├── view/
    └── Main.java
```

---

## 🧠 Princípios adotados

- Separação de responsabilidades (SRP)
- Inversão de dependência por interfaces (Repository Pattern)
- Redução de classes com responsabilidades mínimas
- Adoção de JavaFX ou Terminal como view desacoplada
- Simulação de persistência via arquivo JSON (`database.json`)

---

## 🗺️ Progresso da Refatoração

| Módulo           | Status        | Observações                        |
|------------------|---------------|------------------------------------|
| Cliente          | ✅ Feito       | `ClienteService`, `ClienteView`    |
| Funcionário      | 🔧 Em andamento | Será refatorado em breve           |
| Ordem de Serviço | ⏳ Planejado   |                                    |
| Terminal Handlers| ❌ Removido    | Substituído por `view.*`           |

---

## 🧩 Próximos passos

- Finalizar refatoração dos serviços restantes
- Refatorar `Main.java` para injetar as dependências
- Atualizar documentação das entidades e casos de uso