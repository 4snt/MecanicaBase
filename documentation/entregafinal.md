# ✅ Requisitos Implementados (1 a 14)

## 📋 Requisitos Funcionais

### 1. Incluir, Editar e Remover Cliente (com ID definido)

📁 `src/main/java/service/usuarios/clienteCrud.java`  
✔️ **Implementado**

### 2. Incluir, Editar e Remover Funcionário, Colaborador e Administrador

📁 `src/main/java/service/usuarios/*`  
✔️ **Implementado**

### 3. Alterar a senha de um Administrador

📁 `src/main/java/usuariocrud`  
✔️ **Implementado**

### 4. Verificar produto no estoque da loja

📁 `src/main/java/mecanicabase/service/operacao/PecaCrud.java`  
✔️ **Implementado**

### 5. Realizar agendamentos

📁 `src/main/java/mecanicabase/controller/AgendamentoController.java`  
✔️ **Implementado**

### 6. Cancelar agendamentos (com retenção de 20% do valor)

📁 `src/main/java/mecanicabase/service/financeiro/AgendamentoCrud.java`  
✔️ **Implementado**

### 7. Receber de fornecedores e atualizar ações no estoque

📁 `src/main/java/mecanicabase/model/operacao/EntradaPeca.java`  
✔️ **Implementado**

### 8. Emitir relatório de vendas e serviços de um dia e de um mês

📁 `src/main/java/mecanicabase/service/financeiro/relatorios/GerarRelatorioUseCase.java`  
✔️ **Implementado**

### 9. Gerar balanço mensal com estatísticas básicas

📁 `src/main/java/mecanicabase/service/financeiro/relatorios/GerarBalancoUseCase.java`  
✔️ **Implementado**

---

## 🔧 Requisitos Técnicos

### 10. Implementar todas as classes com base no diagrama de classes criado

📌 Revisar todas as classes geradas a partir do `.puml`  
✔️ **Presente na documentação**

### 11. Sobrescrever o método `toString()` em todas as classes

📌 Usar `@Override` nas entidades  
📁 `src\main\java\mecanicabase\core\Entity.java`
✔️ **Implementado nas entidades**

### 12. Utilizar `super(...)` nos construtores das subclasses

📁 Caminho: `src/main/java/mecanicabase/model/usuarios/Colaborador.java`  
📌 Ex: `Administrador`, `Funcionario`, `Colaborador`  
✔️ **Implementado**

### 13. Armazenar estaticamente os 3 elevadores da oficina

📁 Caminho: `src/main/java/mecanicabase/model/operacao/Elevador.java`  
🛠️ Implementação: `public static final List<Elevador> instances = new ArrayList<>(Collections.nCopies(3, null));`  
📌 Inicialização via método `Elevador.init()`  
✔️ **Implementado**

### 14. Verificar e imprimir dados das ordens de serviço por cliente

📌 Criar `imprimirOrdensDoCliente(Cliente c)`
📁 caminho: `src\main\java\mecanicabase\service\financeiro\OrdemDeServicoCrud.java`
✔️ **Implementado**

---

# 🚀 Questões Avançadas (15 a 18)

### 15. Iterador + `while(iterator.hasNext())`

- Instanciar `Iterator` para `ArrayList` de pessoas (cliente/funcionário).
- Testar no `main`.
- Explicar como isso funciona.
- Comparar com `for-each`.
  ✔️ **Implementado**

### 16. Uso de `Comparator` + `Collections.sort()`

- Criar `Comparator` para pessoa/cliente.
- Usar `Collections.sort()` duas vezes com diferentes critérios.
- Testar e apresentar resultado no `main`.

---

#### 📦 Implementação

Foi criado um comparator genérico para abstrair ordenações por qualquer campo da entidade. Esse comparator está localizado em:src\main\java\mecanicabase\core\GenericComparator.java

### 17. Criar método `find()` com `Iterator` + `Comparator`

- Testar no `main`.
- Comparar com `Collections.binarySearch()`.

### 18. Fluxo completo de atendimento de 10 clientes

- Cadastro do cliente ✅
- Agendamento e serviços ✅
- Baixas no estoque ✅
- Emissão de nota fiscal ✅  
  📌 Apresentar tudo funcionando no `main`
