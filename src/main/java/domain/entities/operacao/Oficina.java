package domain.entities.operacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Entity;
import domain.entities.financeiro.Agendamento;
import domain.entities.usuarios.Cliente;
import domain.entities.usuarios.Funcionario;

/**
 * Representa a Oficina principal do sistema.
 * Gerencia clientes, veículos, funcionários, serviços e agendamentos.
 */
public class Oficina extends Entity {

    /**
     * Simula o banco de dados em memória da oficina.
     * A lista inicia com uma posição nula por exigência do projeto ou padrão de inicialização.
     */
    public static final List<Oficina> instances = new ArrayList<>(Collections.nCopies(1, null));

    // =========================
    // Atributos
    // =========================

    private final List<Cliente> clientes;             // Lista de clientes cadastrados
    private final List<Veiculo> veiculos;             //  final Lista de veículos da oficina
    private final List<Funcionario> funcionarios;     //  final Lista de funcionários, incluindo administradores
    private final List<Servico> servicos;             // Lista de serviços oferecidos
    private Agendamento agendamento;            // Agendamento atual ou controle de agendamentos

    // =========================
    // Construtor
    // =========================

    /**
     * Construtor da oficina. Inicializa todas as listas vazias.
     */
    public Oficina() {
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.agendamento = null;
    }

    // =========================
    // Métodos de Operação
    // =========================

    /**
     * Registra um cliente na lista da oficina.
     * @param cliente Cliente a ser adicionado
     */
    public void registrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    /**
     * Adiciona um veículo a um cliente específico.
     * Aqui você pode expandir para mapear veículos por cliente.
     * @param cliente Cliente dono do veículo
     * @param veiculo Veículo a ser vinculado
     */
    public void adicionarVeiculo(Cliente cliente, Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }

    /**
     * Marca um serviço como realizado com base no agendamento.
     * @param agendamento Agendamento referente ao serviço realizado
     */
    public void realizarServico(Agendamento agendamento) {
        this.agendamento = agendamento;
        // Lógica do serviço pode ser expandida conforme necessário
    }

    /**
     * Gera um balanço mensal da oficina.
     * Pode incluir total de serviços, ganhos, despesas etc.
     */
    public void gerarBalancoMensal() {
        // Lógica de relatório ou agregação de dados financeiros
    }

    // =========================
    // Inicialização
    // =========================

    /**
     * Inicializa a instância principal da oficina.
     * Por padrão, adiciona uma nova oficina na lista `instances`.
     */
    public static final void init() {
        Oficina.instances.add(new Oficina());
    }

    // =========================
    // Getters e Setters (opcional, se precisar usar os dados externamente)
    // =========================

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }
}
