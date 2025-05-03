package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;
import domain.entities.operacao.Peca;

public class PecaItem extends Entity {

    public static List<PecaItem> instances = new ArrayList<>();

    private UUID peca;
    private int quantidade;
    private float valorUnitario;

    private final UUID ordemDeServico;

    public PecaItem(UUID peca, int quantidade, float valorUnitario, UUID ordemDeServico) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.ordemDeServico = ordemDeServico;
    }

    public Peca getPeca() {
        return Peca.instances.stream()
            .filter(p -> p.getId().equals(this.peca))
            .findFirst()
            .orElse(null);
    }

    public void setPeca(UUID peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public OrdemDeServico getOrdemDeServico() {
        return OrdemDeServico.buscarPorId(this.ordemDeServico);
    }
}
