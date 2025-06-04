package com.mycompany.mecanicabase.model.financeiro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import com.mycompany.mecanicabase.core.Entity;
import com.mycompany.mecanicabase.model.operacao.Elevador;
import com.mycompany.mecanicabase.model.operacao.Servico;
import com.mycompany.mecanicabase.model.operacao.Veiculo;
import com.mycompany.mecanicabase.model.usuarios.Funcionario;

/**
 * Representa um agendamento de serviço feito por um cliente. Usa
 * java.time.LocalDateTime para armazenar data e hora em um único campo.
 */
public class Agendamento extends Entity implements Comparable<Agendamento> {

    public static List<Agendamento> instances = new ArrayList<>();

    private LocalDateTime data;               // Data e hora do agendamento
    private String descricaoProblema;         // Problema relatado
    private StatusAgendamento status;         // Status atual do agendamento
    private UUID ordemDeServico;    // Ordem de serviço vinculada
    private UUID servico;
    private UUID elevador;
    private UUID funcionario;          // Funcionário responsável
    private UUID veiculo;                  // Veículo envolvido

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
            UUID veiculo,
            UUID elevador,
            UUID funcionario,
            UUID servico,
            UUID ordemDeServico
    ) {
        this.data = data;
        this.descricaoProblema = descricaoProblema;
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
        return Servico.instances.stream()
                .filter(s -> s.getId().equals(this.servico))
                .findFirst()
                .orElse(null);
    }

    public void setServico(UUID servico) {
        this.servico = servico;
    }

    public OrdemDeServico getOrdemDeServico() {
        return OrdemDeServico.instances.stream()
                .filter(o -> o.getId().equals(this.ordemDeServico))
                .findFirst()
                .orElse(null);
    }

    public void setOrdemDeServico(UUID ordemDeServico) {
        this.ordemDeServico = ordemDeServico;
    }

    public Funcionario getFuncionario() {
        return Funcionario.instances.stream()
                .filter(f -> f.getId().equals(this.funcionario))
                .findFirst()
                .orElse(null);
    }

    public void setFuncionario(UUID funcionario) {
        this.funcionario = funcionario;
    }

    public Veiculo getVeiculo() {
        return Veiculo.instances.stream()
                .filter(v -> v.getId().equals(this.veiculo))
                .findFirst()
                .orElse(null);
    }

    public void setVeiculo(UUID veiculo) {
        this.veiculo = veiculo;
    }

    public Elevador getElevador() {
        return Elevador.instances.stream()
                .filter(e -> e.getId().equals(this.elevador))
                .findFirst()
                .orElse(null);
    }

    public void setElevador(UUID elevador) {
        this.elevador = elevador;
    }

    // MÉTODOS DE REPOSITÓRIO
    public static Agendamento buscarPorId(UUID id) {
        return Agendamento.instances.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // IMPLEMENTAÇÃO COMPARATOR
    @Override
    public int compareTo(Agendamento outro) {
        return this.data.compareTo(outro.data); // Ordenação padrão por data
    }

    // Comparador por data (mais antigos primeiro)
    public static final Comparator<Agendamento> porDataAsc = Comparator.comparing(Agendamento::getData);

    // Comparador por data (mais recentes primeiro)
    public static final Comparator<Agendamento> porDataDesc = Comparator.comparing(Agendamento::getData).reversed();

    // Comparador por nome do funcionário
    public static final Comparator<Agendamento> porFuncionarioNome
            = Comparator.comparing(a -> a.getFuncionario().getNome(), String.CASE_INSENSITIVE_ORDER);

    // Comparador por descrição do problema
    public static final Comparator<Agendamento> porDescricaoProblema
            = Comparator.comparing(Agendamento::getDescricaoProblema, String.CASE_INSENSITIVE_ORDER);

    @Override
    public String toString() {
        return String.format(
                "Agendamento [ID=%s, Data=%s, Problema='%s', Funcionário='%s', Veículo='%s', Serviço='%s', Status='%s']",
                getId(),
                data,
                descricaoProblema,
                getFuncionario() != null ? getFuncionario().getNome() : "N/A",
                getVeiculo() != null ? getVeiculo().getModelo() : "N/A",
                getServico() != null ? getServico().getTipo() : "N/A",
                status != null ? status.name() : "N/A"
        );
    }
}
