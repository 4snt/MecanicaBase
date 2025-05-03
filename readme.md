# ✅ Checklist - Projeto Oficina Mecânica (POO)

## 📦 Estrutura de Classes
- [ ] Todas as classes implementadas com base no diagrama UML.
- [x] Relações corretamente aplicadas (herança, composição, associação).
- [x] Uso de `super()` nos construtores das subclasses.
- [ ] Método `toString()` sobrescrito em todas as classes.

## 🛗 Armazenamento Estático dos Elevadores
- [x] Criar vetor fixo `Elevador[] elevadores = new Elevador[3]`.
- [x] Inicializar os 3 elevadores no início da aplicação.

## 👤 Funcionalidades dos Colaboradores e Administrador
- [ ] Criar menu/sistema para diferenciar permissões.
- [x] Cadastrar, editar e excluir colaboradores.

## 👥 Gerenciamento de Clientes
- [x] Cadastrar, editar e excluir clientes.
- [x] Armazenamento dinâmico com `List<Cliente>`.
- [x] Visualizar ordens de serviço associadas a cada cliente.

## 📋 Ordens de Serviço e Ações do Estoque
- [x] Salvar ordens de serviço dinamicamente.
- [ ] Gerar extrato de cada serviço/venda e associar ao cliente.
- [ ] Implementar sistema de estoque e registrar alterações.

## 🚗 Contagem de Instâncias de Veículos
### Estratégia 1: Encapsulamento Padrão
- [x] `private static int totalVeiculos;`

### Estratégia 2: Controle com `protected`
- [ ] `protected static int contadorVeiculos;` acessado diretamente.

### Comparação
- [ ] Explicar vantagens e desvantagens:
  - Encapsulamento (segurança, flexibilidade).
  - Acesso direto (simplicidade, menor controle).

- [ ] Criar método de classe `getTotalVeiculos()` para consultar instâncias criadas.

## 🔍 Interface Comparator
- [ ] Implementar `Comparator<Agendamento>` por data, cliente, etc.
- [ ] Implementar `Comparator<Cliente>` por nome, CPF, etc.

## 💾 Persistência de Dados com JSON
- [ ] Usar biblioteca externa (ex: Gson).
- [ ] Salvar/carregar os dados:
  - `clientes.json`
  - `veiculos.json`
  - `agendamentos.json`
  - `colaboradores.json`
  - `estoque.json`
  - `relatorios.json`
- [ ] Utilizar `try-with-resources` para segurança na manipulação de arquivos.

## 📚 Documentação com JavaDoc
- [ ] Adicionar JavaDoc em:
  - Classes
  - Métodos
  - Atributos relevantes
- [ ] Gerar documentação com:
  ```bash
  javadoc -d doc src/*
