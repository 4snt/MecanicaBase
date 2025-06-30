# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

### [5.0.9](https://github.com/4snt/MecanicaBase/compare/v5.0.8...v5.0.9) (2025-06-30)

### [5.0.8](https://github.com/4snt/MecanicaBase/compare/v5.0.7...v5.0.8) (2025-06-30)

### [5.0.7](https://github.com/4snt/MecanicaBase/compare/v5.0.6...v5.0.7) (2025-06-30)

### [5.0.6](https://github.com/4snt/MecanicaBase/compare/v5.0.5...v5.0.6) (2025-06-30)

### [5.0.5](https://github.com/4snt/MecanicaBase/compare/v5.0.4...v5.0.5) (2025-06-30)

### [5.0.4](https://github.com/4snt/MecanicaBase/compare/v5.0.3...v5.0.4) (2025-06-30)

### [5.0.3](https://github.com/4snt/MecanicaBase/compare/v5.0.2...v5.0.3) (2025-06-30)

### [5.0.2](https://github.com/4snt/MecanicaBase/compare/v5.0.1...v5.0.2) (2025-06-30)

### [5.0.1](https://github.com/4snt/MecanicaBase/compare/v5.0.0...v5.0.1) (2025-06-30)

## [5.0.0](https://github.com/4snt/MecanicaBase/compare/v4.1.4...v5.0.0) (2025-06-30)


### ⚠ BREAKING CHANGES

* **teste:** a criação da Ordem de Serviço foi movida para dentro do try após o agendamento, podendo impactar fluxos que assumem OS criada antecipadamente.

### Features

* **agendamento, estoque:** implementar cancelamento e entrada de peça com atualização de estoque ([2b43b17](https://github.com/4snt/MecanicaBase/commit/2b43b17d1f9bf8277a0ed85959e3822bfa986d62))
* **core:** implementar CPF seguro e ordenação com Comparator ([2eca6c7](https://github.com/4snt/MecanicaBase/commit/2eca6c7d533f81fc1e1f4ca21154d3d6fb169e79))
* implementar cancelamento e conclusão de agendamento com exibição de ordens (Questões 5.7, 7.5 e 14) ([313e3df](https://github.com/4snt/MecanicaBase/commit/313e3df1a592f6233b158cde366e0c350b2b1681))
* **swing:** aplicar nova interface de login com seleção de tipo de usuário ([e2a996c](https://github.com/4snt/MecanicaBase/commit/e2a996c2b08d200e15d976ff331a0480849e816d))
* **teste:** finaliza classe RequisitosImplementadosTest com fluxo completo de atendimento ([4b3d158](https://github.com/4snt/MecanicaBase/commit/4b3d15888660cce2f8a4ab54f0bd725522fa277d))
* **ui:** formulário de veículos com labels claras, filtro de cliente, e prevenção de placas duplicadas ([4687077](https://github.com/4snt/MecanicaBase/commit/468707794ef6cafcf5d6c2d2e3cdc8cee0f186e4))


### Bug Fixes

* **cliente-panel:** corrigida ordem dos campos telefone, endereço e email no formulário e tabela do ClientePanel para evitar troca de dados na edição e visualização ([bd2c3d8](https://github.com/4snt/MecanicaBase/commit/bd2c3d8a8c65b4a8bd9430aa172607ce62dd79b5))
* **correção de bugs:** atualizações de cruds ([3888735](https://github.com/4snt/MecanicaBase/commit/38887357d627b96796e9da930f5122d58fef7a45))

### [4.1.4](https://github.com/4snt/MecanicaBase/compare/v4.1.3...v4.1.4) (2025-06-10)

### [4.1.3](https://github.com/4snt/MecanicaBase/compare/v4.1.2...v4.1.3) (2025-06-10)

### [4.1.2](https://github.com/4snt/MecanicaBase/compare/v4.1.1...v4.1.2) (2025-06-09)

### [4.1.1](https://github.com/4snt/MecanicaBase/compare/v4.1.0...v4.1.1) (2025-06-09)

## [4.1.0](https://github.com/4snt/MecanicaBase/compare/v4.0.0...v4.1.0) (2025-06-08)


### Features

* **benchmark:** adiciona medição realista de OS com base em PecaItem e estoque ([c61b542](https://github.com/4snt/MecanicaBase/commit/c61b54224cdfa713550766443eb64522fff7bcd9))
* **flyweight:** aplica padrão Flyweight na entidade Peca com reutilização via PecaFactory ([f9e85de](https://github.com/4snt/MecanicaBase/commit/f9e85de06a29f0ac3b644cf23b4e4b190172146f))


### Bug Fixes

* **database:** ajusta persistência para salvar fora do .jar em produção e manter leitura no projeto no modo desenvolvimento ([a166fb9](https://github.com/4snt/MecanicaBase/commit/a166fb950abde82b7815677afaed6a6c7e059eeb))

## [4.0.0](https://github.com/4snt/MecanicaBase/compare/v3.0.4...v4.0.0) (2025-06-08)


### ⚠ BREAKING CHANGES

* **core:** todos os serviços agora estendem a classe abstrata Crud<T>,
eliminando os antigos casos de uso manuais (use cases) para criar, atualizar, listar e remover entidades.

- Criação da classe Crud<T> com métodos genéricos para:
  - listarTodos()
  - buscarPorId(String e UUID)
  - criar(Object... params)
  - atualizar(Object... params)
  - removerPorId()

- Refatorados os seguintes serviços para extender Crud<T>:
  - CategoriaDespesaCrud
  - DespesaCrud
  - OrdemDeServicoCrud
  - AgendamentoCrud
  - ServicoCrud
  - PecaCrud (e outros conforme necessário)

- Remoção dos antigos UseCases:
  - Criar/Listar/Atualizar/RemoverCategoriaDespesaUseCase
  - Criar/Listar/Atualizar/RemoverDespesaUseCase
  - Criar/Listar/Atualizar/RemoverOrdemDeServicoUseCase
  - VenderPecaUseCase, RemoverPecaItemUseCase etc.

- Atualização dos TerminalHandlers para utilizar diretamente os novos CRUDs:
  - AgendamentoTerminalHandler
  - CategoriaDespesaTerminalHandler
  - DespesaTerminalHandler
  - OrdemDeServicoTerminalHandler

- database.json agora é persistido em `data/database.json` para facilitar visualização e envio ao professor.
- Atualização de `Database.java` para refletir nova localização do arquivo.

Essa mudança quebra compatibilidade com qualquer código antigo que dependia dos casos de uso específicos. A partir de agora, toda persistência e lógica de entidades devem ser feitas via extensão do CRUD genérico.

### Features

* **core:** centraliza lógica de CRUD com classe genérica ([c477767](https://github.com/4snt/MecanicaBase/commit/c477767f701ff02c1813a645c7a8217e0b8ce1c7))

### [3.0.4](https://github.com/4snt/MecanicaBase/compare/v3.0.3...v3.0.4) (2025-06-07)


### Bug Fixes

* **package.json:** Inclusão do envio automatico da tag de release ([193582c](https://github.com/4snt/MecanicaBase/commit/193582c6751c7c1853bf8b5112773c26f9d7cfbd))

### [3.0.3](https://github.com/4snt/MecanicaBase/compare/v3.0.2...v3.0.3) (2025-06-07)

### [3.0.2](https://github.com/4snt/MecanicaBase/compare/v3.0.1...v3.0.2) (2025-06-07)

### [3.0.1](https://github.com/4snt/MecanicaBase/compare/v3.0.0...v3.0.1) (2025-06-07)

## [3.0.0](https://github.com/4snt/MecanicaBase/compare/v2.1.3...v3.0.0) (2025-06-07)


### ⚠ BREAKING CHANGES

* quem usar scripts externos precisa atualizar a referência
à classe principal para mecanicabase.MecanicaBase ou utilizar mvn exec:java
🐾 refactor(core)!: move pacotes para raiz mecanicabase

### ✨ Novidades
* estrutura de diretórios simplificada ─ removidos níveis com.mycompany
* main class agora é mecanicabase.MecanicaBase

### 🛠️ Ajustes
* pom.xml: <main.class> atualizado + plugins JavaFX/exec/assembly
* nbactions.xml: exec.mainClass corrigido
* scripts externos que apontavam para
com.mycompany.mecanicabase.MecanicaBase devem usar mecanicabase.MecanicaBase

### Features

* **Atualização da docuemntação:** Criação de entidades.md e atualização do redme Geral ([e1489c0](https://github.com/4snt/MecanicaBase/commit/e1489c051eaf6f478cd5c92dc910621edf0d3b55))


* 🏗️ build(pom)!: tornar execução portátil e simplificar ações NB ([e2516d8](https://github.com/4snt/MecanicaBase/commit/e2516d8d85fab5fbc01a39bccb51499acc37d194))

### [2.1.3](https://github.com/4snt/MecanicaBase/compare/v2.1.2...v2.1.3) (2025-06-04)

### [2.1.2](https://github.com/4snt/MecanicaBase/compare/v2.1.1...v2.1.2) (2025-06-04)

### [2.1.1](https://github.com/4snt/MecanicaBase/compare/v2.0.7...v2.1.1) (2025-06-04)

### [2.0.7](https://github.com/4snt/MecanicaBase/compare/v2.0.6...v2.0.7) (2025-06-04)

### [2.0.6](https://github.com/4snt/MecanicaBase/compare/v2.0.5...v2.0.6) (2025-06-04)

### [2.0.5](https://github.com/4snt/MecanicaBase/compare/v2.0.4...v2.0.5) (2025-06-04)

### [2.0.4](https://github.com/4snt/MecanicaBase/compare/v2.0.3...v2.0.4) (2025-06-04)

### [2.0.3](https://github.com/4snt/MecanicaBase/compare/v2.0.2...v2.0.3) (2025-06-04)


### Bug Fixes

* **vercel:** upload do javadoc no vercel ([4c45e9b](https://github.com/4snt/MecanicaBase/commit/4c45e9bc11c3f5640aaefc5a9c5f017abf3b55e1))

### [2.0.2](https://github.com/4snt/MecanicaBase/compare/v2.0.1...v2.0.2) (2025-06-04)

### [2.0.1](https://github.com/4snt/MecanicaBase/compare/v2.0.0...v2.0.1) (2025-06-04)


### Bug Fixes

* **gitignore:** correção da geração do site javadoc ([14c24fc](https://github.com/4snt/MecanicaBase/commit/14c24fca34685a65481b58312a72b60483cd96bb))

## 2.0.0 (2025-06-04)


### ⚠ BREAKING CHANGES

* substitui estrutura baseada em usecases e handlers por camadas tradicionais (model, service, controller, view, infra). Adiciona versionamento automático, geração de Javadoc e pacote .zip compatível com NetBeans."

### Features

* **cliente:** adiciona módulo de atualização com layout moderno ✨🛠️ ([c4ab0c0](https://github.com/4snt/MecanicaBase/commit/c4ab0c079d109446891125705c7e56813f8ea5d6))
* **core:** iniciar projeto e adicionar entidades base da oficina ([9a52dd6](https://github.com/4snt/MecanicaBase/commit/9a52dd661e534d20f6d4e6d664115c20d36de8cb))


* git add . ([6869b4c](https://github.com/4snt/MecanicaBase/commit/6869b4ccd7e18634bb3b316d5b868feea79a1700))
