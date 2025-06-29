# ✅ Checklist - Trabalho Prático Integrador (TPI)

## Questão 1

- [ ] Remodele todo o TPI utilizando o conhecimento adquirido nas aulas.
- [ ] Refaça o **Diagrama de Casos de Uso** do sistema.
- [ ] Crie os **cenários (fluxo de eventos)** para todos os casos de uso obrigatórios:
  - [x] Incluir, Editar e Remover Cliente, além de definir o ID do Cliente.
        //Incluir/editar: \src\main\java\service\usuarios\clienteCrud.java
  - [x] Incluir, Editar e Remover Funcionário, Colaborador e Administrador.
        // src/main/java/service/usuarios/\*
  - [x] Alterar a senha de um Administrador.
        //src/main/java//usuariocrud => extende funcionario e administrador
  - [x] Verificar produto no estoque da loja.
        // src/main/java//usecases/operacao/peca/BuscaPorIdPecaUseCase.java
  - [x] Realizar agendamentos.
        // src/main/java//usecases/financeiro/agendamento/CriarAgendamentoUseCase.java
  - [x] Receber de fornecedores e atualizar automaticamente todas as ações do estoque da loja.
        // src/main/java//usecases/operacao/peca/AtualizaPecaUseCase.java
  - [x] Cancelar agendamentos (com retenção de 20% do valor).
        // src/main/java//usecases/financeiro/agendamento/AtualizaAgendamentoUseCase.java
  - [x] Emitir relatório de vendas e serviços de um dia e de um mês.
        // src/main/java//usecases/financeiro/relatorios/GerarRelatorioUseCase.java
  - [x] Gerar balanço mensal com estatísticas básicas de serviços e vendas.
        // src/main/java//usecases/financeiro/relatorios/GerarBalancoUseCase.java
- [ ] Criar o **Diagrama de Classes COMPLETO** com atributos e métodos necessários para realizar todos os casos de uso do sistema.

## Questão 2

- [x] Implementar todas as **classes com base no Diagrama de Classes criado**, respeitando as relações e implementando corretamente atributos e métodos.
- [x] O sistema será utilizado por **colaboradores e pelo administrador**.
- [x] **Sobrescrever o método `toString()`** em todas as classes implementadas.
- [x] Utilizar a palavra-chave `super` para implementar os **construtores das subclasses**.
- [x] O sistema deverá armazenar **de forma estática (vetor com tamanho fixo)** as informações dos **3 elevadores da oficina**.
- [x] Deve ser possível:
  - [x] Cadastrar os colaboradores no sistema.
  - [x] Alterar ou editar atributos dos colaboradores.
  - [x] Cadastrar, alterar ou excluir clientes.
  - [x] Verificar e imprimir dados das ordens de serviço de cada cliente.
- [x] Ordens de serviço, ações do estoque e clientes devem ser **salvos de forma dinâmica** no sistema.
- [x] Cada serviço e venda efetuados devem ser impressos e salvos junto com as informações do cliente que contratou o serviço.
- [x] Criar **duas variáveis de classe (`static`)** para armazenar quantas instâncias do tipo `Veículo` foram criadas:
  - [x] Usar **encapsulamento (`private static` + métodos `get`/`set`)**.
  - [ ] Usar **controle de acesso `protected`**.
  - [ ] Explicar **vantagens e desvantagens** de cada abordagem.
- [x] Criar um **método de classe** na classe `Sistema` ou `Cliente` que **retorne quantas instâncias** do tipo `Veículo` foram criadas.
- [x] Implementar a **interface `Comparator`** para as classes `Agendamento` e `Cliente`, permitindo comparações por diferentes atributos.
- [x] Salvar e recuperar **todas as informações** (Clientes, Veículos, Agendamentos, Produtos, Relatórios de Vendas, Colaboradores, Estoque etc.) em **arquivos de texto**:
  - [x] Utilizar classes que trabalhem com **formato JSON** (podem ser reutilizadas da internet).
  - [ ] Utilizar **alocação e desalocação de recursos com segurança**, conforme aprendido em aula.
- [ ] **Gerar o JavaDoc** de todo o projeto.
