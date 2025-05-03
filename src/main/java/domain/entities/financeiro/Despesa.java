package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;

/**
 * Representa uma despesa registrada no sistema financeiro.
 */
public class Despesa extends Entity {

    public static List<Despesa> instances = new ArrayList<>();

    private String descricao;
    private float valor;

    private final UUID categoriaId;

    public Despesa(UUID categoriaId, String descricao, float valor) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoriaId = categoriaId;
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

    public CategoriaDespesa getCategoria() {
        return CategoriaDespesa.buscarPorId(this.categoriaId);
    }
}
