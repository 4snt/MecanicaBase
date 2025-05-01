package domain.entities.financeiro;

import java.time.LocalDate;

import core.Entity;

/**
 * Representa uma despesa registrada no sistema financeiro.
 */
public class Despesa extends Entity {

    private String descricao;
    private float valor;
    private LocalDate data;

    public Despesa(String descricao, float valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
