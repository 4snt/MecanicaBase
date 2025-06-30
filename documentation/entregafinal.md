# ✅ Requisitos Implementados (1 a 14)

1. **Incluir, Editar e Remover Cliente, além de definir o ID do Cliente**  
   📁 Caminho: `src/main/java/service/usuarios/clienteCrud.java`  
   ✔️ Status: Implementado

2. **Incluir, Editar e Remover Funcionário, Colaborador e Administrador**  
   📁 Caminho: `src/main/java/service/usuarios/*`  
   ✔️ Status: Implementado

3. **Alterar a senha de um Administrador**  
   📁 Caminho: `src/main/java/usuariocrud`  
   ✔️ Status: Implementado

4. **Verificar produto no estoque da loja**  
   📁 Caminho: `src/main/java/mecanicabase/service/operacao/PecaCrud.java`  
   ✔️ Status: Implementado

5. **Realizar agendamentos**  
   📁 Caminho: `src/main/java/mecanicabase/controller/AgendamentoController.java`  
   ✔️ Status: Implementado
6. **Cancelar agendamentos (com retenção de 20% do valor)**  
   📁 Caminho: `src/main/java/mecanicabase/service/financeiro/AgendamentoCrud.java`  
   ✔️ Status: Implementado

7. **Receber de fornecedores e atualizar automaticamente todas as ações do estoque da loja**  
   📁 Caminho: `src/main/java/mecanicabase/model/operacao/EntradaPeca.java`  
   ✔️ Status: Implementado

8. **Emitir relatório de vendas e serviços de um dia e de um mês**  
   📁 Caminho: `src/main/java/mecanicabase/service/financeiro/relatorios/GerarRelatorioUseCase.java`  
   ✔️ Status: Implementado

9. **Gerar balanço mensal com estatísticas básicas de serviços e vendas**  
   📁 Caminho: `src/main/java/mecanicabase/service/financeiro/relatorios/GerarBalancoUseCase.java`  
   ✔️ Status: Implementado

10. **Implementar todas as classes com base no diagrama de classes criado**  
    📌 Revisar classes geradas a partir do `.puml`  
    ❌ Status: Em aberto

11. **Sobrescrever o método `toString()` de todas as classes implementadas**  
     📌 Implementar `@Override toString()` em todas as entidades  
     ❌ Status: Pendented
    // IMPLEMENTADO NAS ENTIDADES
12. **Utilizar a palavra-chave `super` nos construtores das subclasses**  
     📌 Exemplo: `super(...)` em `Administrador`, `Funcionario`, etc.  
     ❌ Status: Parcial
    // aplication context
13. **Armazenar estaticamente os 3 elevadores da oficina (vetor fixo)**  
    📌 Criar: `Elevador[] elevadores = new Elevador[3];`  
    ❌ Status: Não implementado

14. **Verificar e imprimir dados das ordens de serviço de cada cliente**  
    📌 Criar método como `imprimirOrdensDoCliente(Cliente c)`  
    ❌ Status: Pendente
