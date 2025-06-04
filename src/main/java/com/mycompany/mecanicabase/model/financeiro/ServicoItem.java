package com.mycompany.mecanicabase.model.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.mycompany.mecanicabase.core.Entity;
import com.mycompany.mecanicabase.model.operacao.Servico;

/**
 * Representa um item de serviço associado a uma Ordem de Serviço.
 */
public class ServicoItem extends Entity {

    public static List<ServicoItem> instances = new ArrayList<>();

    private UUID servico;
    private float valorUnitario;
    private final UUID ordemDeServico;

    public ServicoItem(UUID servico, float valorUnitario, UUID ordemDeServico) {
        this.servico = servico;
        this.valorUnitario = valorUnitario;
        this.ordemDeServico = ordemDeServico;
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

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public OrdemDeServico getOrdemDeServico() {
        return OrdemDeServico.instances.stream()
                .filter(o -> o.getId().equals(this.ordemDeServico))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format(
                "ServicoItem [ID=%s, Serviço='%s', Valor Unitário=%.2f, Ordem de Serviço=%s]",
                getId(),
                getServico() != null ? getServico().getTipo() : "N/A",
                valorUnitario,
                ordemDeServico
        );
    }
}
