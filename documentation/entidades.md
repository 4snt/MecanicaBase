# Documentação das Entidades

## Pacote `model.financeiro`

### Agendamento

Representa o agendamento de um serviço na oficina. Contém informações como data, hora, veículo, serviço e status.

### CategoriaDespesa

Define as categorias possíveis para as despesas financeiras da oficina, como "Material", "Serviço Terceirizado", etc.

### Despesa

Registra uma despesa financeira realizada pela oficina, vinculada a uma categoria e contendo valor, descrição e data.

### OrdemDeServico

Representa a ordem de serviço da oficina, agrupando peças, serviços, veículo, status, data de abertura e encerramento.

### PecaItem

Relaciona uma peça usada em uma ordem de serviço com a quantidade e valor unitário.

### ServicoItem

Relaciona um serviço executado em uma ordem de serviço com seu valor.

### StatusAgendamento

Enumeração que define os possíveis status de um agendamento: PENDENTE, CONFIRMADO, CANCELADO, CONCLUIDO.

### StatusOrdemDeServico

Enumeração que define os status possíveis para uma ordem de serviço: ABERTA, EM_EXECUCAO, FINALIZADA.

---

## Pacote `model.operacao`

### Elevador

Entidade que representa os elevadores da oficina, podendo ser associados a agendamentos.

### EntradaPeca

Controla a entrada de peças no estoque da oficina, com data, peça e quantidade.

### Peca

Representa uma peça disponível na oficina, com nome, descrição, valor unitário e quantidade em estoque.

### Servico

Define os serviços prestados pela oficina, com descrição e valor associado.

### Sistema

Entidade que representa o sistema operacional do veículo (ex: freios, suspensão).

### StatusVeiculo

Enumeração que representa os estados do veículo dentro do fluxo da oficina (ex: AGUARDANDO_PECA, EM_REPARO, PRONTO).

### TipoElevador

Enumeração que representa os tipos de elevadores:

- `ALINHAMENTO_E_BALANCEAMENTO`: Usado para serviços específicos de alinhamento e balanceamento.
- `GERAL`: Elevador versátil para serviços diversos.

### Veiculo

Entidade que representa o veículo do cliente, contendo informações como placa, marca, modelo, ano, e tipo de combustível.

---

## Pacote `model.usuarios`

### Administrador

Extensão de `Funcionario` que representa usuários com privilégios administrativos no sistema.

### Cliente

Representa o cliente da oficina, com nome, CPF e lista de veículos.

### Colaborador

Classe base abstrata para `Funcionario` e `Administrador`, contendo campos comuns como nome, e-mail e senha.

### Funcionario

Classe que representa os funcionários que executam serviços na oficina.

### Pessoa

Classe base que contém dados pessoais básicos, como nome e CPF. É estendida por `Colaborador` e `Cliente`.

### TipoFuncionario

Enumeração que define o tipo de função do funcionário:

- `MECANICO_ESPECIFICO`: Mecânico responsável por tarefas simples ou determinadas.
- `MECANICO_ESPECIALISTA`: Profissional que realiza serviços mais complexos e especializados.
- `VENDEDOR`: Responsável por atendimento e vendas ao cliente.
