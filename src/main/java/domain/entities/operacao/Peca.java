package domain.entities.operacao;

import core.Entity;

/**
 * Representa uma peça disponível para uso em ordens de serviço.
 */
public class Peca extends Entity {

    private String nome;
    private float valor;
    private int quantidade;

    public Peca(String nome, float valor, int quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade em estoque insuficiente.");
        }
    }

    public void adicionarEstoque(int quantidade) {
        this.quantidade += quantidade;
    }
}
