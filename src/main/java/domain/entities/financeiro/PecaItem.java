package domain.entities.financeiro;

import core.Entity;
import domain.entities.operacao.Peca;

/**
 * Representa um item de peça associado a uma Ordem de Serviço.
 */
public class PecaItem extends Entity {

    private Peca peca;
    private int quantidade;
    private float valorUnitario;

    public PecaItem(Peca peca, int quantidade, float valorUnitario) {
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
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
}
