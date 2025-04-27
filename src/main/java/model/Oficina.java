// Oficina.java
package model;

import java.util.List;

public class Oficina {
    private List<Cliente> clientes;
    private List<Veiculo> veiculos;
    private List<Funcionario> funcionarios;
    private List<Servico> servicos;
    private Caixa caixa;
    private Agenda agenda;

    public void registrarCliente(Cliente cliente) {}
    public void adicionarVeiculo(Cliente cliente, Veiculo veiculo) {}
    public void realizarServico(Agendamento agendamento) {}
    public void gerarBalancoMensal() {}
}
