package com.mycompany.mecanicabase.model.financeiro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mycompany.mecanicabase.core.Entity;

public class OrdemDeServico extends Entity {

    // Lista estática para simular persistência das ordens de serviço
    public static List<OrdemDeServico> instances = new ArrayList<>();

    // ID do cliente associado a essa ordem de serviço
    private UUID clienteId;

    // Status atual da ordem (ABERTO, CONCLUIDO, CANCELADO, etc.)
    private StatusOrdemDeServico status;

    // Data e hora em que a ordem foi finalizada
    private LocalDateTime finalizadoEm;

    // Lista de IDs dos serviços relacionados à ordem
    private final List<UUID> servicos;

    // Lista de IDs das peças relacionadas à ordem
    private final List<UUID> pecas;

    // Lista de IDs dos agendamentos relacionados à ordem
    private final List<UUID> agendamentos;

    // Construtor que inicializa a ordem com o cliente e status padrão ABERTO
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

    // Recupera os objetos ServicoItem a partir da lista de IDs
    public List<ServicoItem> getServicos() {
        return servicos.stream()
                .map(id -> ServicoItem.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null))
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    // Recupera os objetos PecaItem a partir da lista de IDs
    public List<PecaItem> getPecas() {
        return pecas.stream()
                .map(id -> PecaItem.instances.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null))
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    // Recupera os objetos Agendamento a partir da lista de IDs
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

    public LocalDateTime getFinalizadoEm() {
        return this.finalizadoEm;
    }

    // Define o status da ordem e, se for finalizado, registra o horário
    public void setStatus(StatusOrdemDeServico status) {
        List<StatusOrdemDeServico> statusFinalizados = List.of(StatusOrdemDeServico.CANCELADO, StatusOrdemDeServico.CONCLUIDO);
        boolean osFinalizada = statusFinalizados.contains(status);
        if (osFinalizada) {
            this.finalizadoEm = LocalDateTime.now();
        }
        this.status = status;
    }

    // Adiciona o ID de um serviço à ordem, se ainda não estiver presente
    public void addServico(UUID servico) {
        if (!this.servicos.contains(servico)) {
            this.servicos.add(servico);
        }
    }

    // Remove um serviço da ordem com base no ID
    public boolean removeServicoPor(UUID id) {
        return this.servicos.removeIf(s -> s.equals(id));
    }

    // Adiciona o ID de uma peça à ordem, se ainda não estiver presente
    public void addPeca(UUID peca) {
        if (!this.pecas.contains(peca)) {
            this.pecas.add(peca);
        }
    }

    // Remove uma peça da ordem com base no ID
    public boolean removePeca(UUID id) {
        return this.pecas.removeIf(p -> p.equals(id));
    }

    // Adiciona o ID de um agendamento à ordem, se ainda não estiver presente
    public void addAgendamento(UUID agendamento) {
        if (!this.agendamentos.contains(agendamento)) {
            this.agendamentos.add(agendamento);
        }
    }

    // Remove um agendamento da ordem com base no ID
    public boolean removeAgendamento(UUID id) {
        return this.agendamentos.removeIf(a -> a.equals(id));
    }

    // Busca uma ordem de serviço na lista pelo seu ID
    public static OrdemDeServico buscarPorId(UUID Id) {
        return OrdemDeServico.instances.stream()
                .filter(s -> s.getId().equals(Id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format(
                "OrdemDeServico [ID=%s, ClienteID=%s, Status=%s, FinalizadoEm=%s, Serviços=%d, Peças=%d, Agendamentos=%d]",
                getId(),
                clienteId,
                status.name(),
                finalizadoEm != null ? finalizadoEm.toString() : "N/A",
                servicos.size(),
                pecas.size(),
                agendamentos.size()
        );
    }
}
