package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;

import core.Entity;

/**
 * Representa uma Ordem de Serviço, criada a partir de um agendamento concluído ou cancelado.
 */
public class OrdemDeServico extends Entity {

    public static List<OrdemDeServico> instances = new ArrayList<>();

    private List<ServicoItem> servicos;
    private List<PecaItem> pecas;
    private Agendamento agendamento;
    private StatusOrdemDeServico status;

    public OrdemDeServico(Agendamento agendamento, List<ServicoItem> servicos, List<PecaItem> pecas) {
        this.agendamento = agendamento;
        this.servicos = servicos;
        this.pecas = pecas;
        this.status = StatusOrdemDeServico.ABERTO;
    }

    public List<ServicoItem> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoItem> servicos) {
        this.servicos = servicos;
    }

    public List<PecaItem> getPecas() {
        return pecas;
    }

    public void setPecas(List<PecaItem> pecas) {
        this.pecas = pecas;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public StatusOrdemDeServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemDeServico status) {
        this.status = status;
    }
}
