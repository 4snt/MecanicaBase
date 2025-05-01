package domain.entities.financeiro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import core.Entity;
import domain.entities.operacao.Elevador;
import domain.entities.operacao.Servico;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Cliente;
import domain.entities.usuarios.Funcionario;

/**
 * Representa um agendamento de serviço feito por um cliente.
 * Usa java.time.LocalDateTime para armazenar data e hora em um único campo.
 */
public class Agendamento extends Entity {

    public static List<Agendamento> instances = new ArrayList<>();

    private LocalDateTime data;               // Data e hora do agendamento
    private String descricaoProblema;         // Problema relatado
    private StatusAgendamento status;         // Status atual do agendamento
    private OrdemDeServico ordemDeServico;    // Ordem de serviço vinculada
    private Servico servico;
    private Elevador elevador;
    private Funcionario funcionario;          // Funcionário responsável
    private Cliente cliente;                  // Cliente que solicitou
    private Veiculo veiculo;                  // Veículo envolvido

    /**
     * Construtor do agendamento.
     *
     * @param data Data e hora do agendamento
     * @param descricaoProblema Descrição do problema
     * @param cliente Cliente que solicitou
     * @param veiculo Veículo envolvido
     */
    public Agendamento(
        LocalDateTime data,
        String descricaoProblema,
        Cliente cliente,
        Veiculo veiculo,
        Elevador elevador,
        Funcionario funcionario,
        Servico servico,
        OrdemDeServico ordemDeServico
    ) {
        this.data = data;
        this.descricaoProblema = descricaoProblema;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.elevador = elevador;
        this.funcionario = funcionario;
        this.servico = servico;
        this.ordemDeServico = ordemDeServico;
    }

    // Getters e Setters

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public OrdemDeServico getOrdemDeServico() {
        return ordemDeServico;
    }

    public void setOrdemDeServico(OrdemDeServico ordemDeServico) {
        this.ordemDeServico = ordemDeServico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Elevador getElevador() {
        return elevador;
    }

    public void setElevador(Elevador elevador) {
        this.elevador = elevador;
    }

    public Agendamento finalizarAgendamento(Agendamento agendamento) {
        agendamento.setStatus(StatusAgendamento.CONCLUIDO);

        ServicoItem servicoItem = new ServicoItem(
            servico,
            servico.getPreco(),
            agendamento.getOrdemDeServico()    
        );

        ServicoItem.instances.add(servicoItem);

        return agendamento;
    }

    public Agendamento cancelarAgendamento(Agendamento agendamento) {
        agendamento.setStatus(StatusAgendamento.CANCELADO);

        ServicoItem servicoItem = new ServicoItem(
            servico,
            servico.getPreco() * 0.2f,
            agendamento.getOrdemDeServico()    
        );

        ServicoItem.instances.add(servicoItem);

        return agendamento;
    }
}
