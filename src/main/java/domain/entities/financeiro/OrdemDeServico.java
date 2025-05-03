package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.Entity;

public class OrdemDeServico extends Entity {

    public static List<OrdemDeServico> instances = new ArrayList<>();

    private UUID clienteId;
    private StatusOrdemDeServico status;

    private final List<UUID> servicos;
    private final List<UUID> pecas;
    private final List<UUID> agendamentos;

    public OrdemDeServico(UUID clienteId) {
        this.clienteId = clienteId;
        this.agendamentos = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.pecas = new ArrayList<>();
        this.status = StatusOrdemDeServico.ABERTO;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public List<ServicoItem> getServicos() {
        return servicos.stream()
            .map(id -> ServicoItem.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null))
            .filter(s -> s != null)
            .collect(Collectors.toList());
    }

    public List<PecaItem> getPecas() {
        return pecas.stream()
            .map(id -> PecaItem.instances.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null))
            .filter(p -> p != null)
            .collect(Collectors.toList());
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos.stream()
            .map(id -> Agendamento.instances.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null))
            .filter(a -> a != null)
            .collect(Collectors.toList());
    }

    public StatusOrdemDeServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemDeServico status) {
        this.status = status;
    }

    public void addServico(UUID servico) {
        if (!this.servicos.contains(servico)) {
            this.servicos.add(servico);
        }
    }

    public boolean removeServicoPor(UUID id) {
        return this.servicos.removeIf(s -> s.equals(id));
    }

    public void addPeca(UUID peca) {
        if (!this.pecas.contains(peca)) {
            this.pecas.add(peca);
        }
    }

    public boolean removePeca(UUID id) {
        return this.pecas.removeIf(p -> p.equals(id));
    }

    public void addAgendamento(UUID agendamento) {
        if (!this.agendamentos.contains(agendamento)) {
            this.agendamentos.add(agendamento);
        }
    }

    public boolean removeAgendamento(UUID id) {
        return this.agendamentos.removeIf(a -> a.equals(id));
    }

    public static OrdemDeServico buscarPorId(UUID Id){
        return OrdemDeServico.instances.stream()
        .filter(s -> s.getId().equals(Id))
        .findFirst()
        .orElse(null);
    }
}
