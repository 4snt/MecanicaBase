package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;

import core.Entity;
import domain.entities.operacao.Servico;

/**
 * Representa um item de serviço associado a uma Ordem de Serviço.
 */
public class ServicoItem extends Entity {
    
    public static List<ServicoItem> instances = new ArrayList<>();

    private Servico servico;
    private float valorUnitario;
    private final OrdemDeServico ordemDeServico;

    public ServicoItem(Servico servico, float valorUnitario, OrdemDeServico ordemDeServico) {
        this.servico = servico;
        this.valorUnitario = valorUnitario;
        this.ordemDeServico = ordemDeServico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public OrdemDeServico getOrdemDeServico() {
      return this.ordemDeServico;
    }
}
