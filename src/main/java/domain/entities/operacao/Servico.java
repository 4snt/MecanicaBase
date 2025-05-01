package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;

import core.Entity;
import domain.entities.usuarios.TipoFuncionario;

/**
 * Representa um serviço oferecido pela oficina, como troca de óleo, revisão, etc.
 */
public class Servico extends Entity {

    /**
     * Lista que simula um banco de dados em memória para persistência via JSON.
     */
    public static List<Servico> instances = new ArrayList<>();

    // =========================
    // Atributos do Serviço
    // =========================

    private String tipo;
    private float preco;
    private String descricao;
    private int duracao;
    private TipoFuncionario tipoFuncionario;
    private TipoElevador tipoElevador;
    private boolean usaElevador;

    // =========================
    // Construtor
    // =========================

    public Servico(String tipo, float preco, String descricao) {
        this.tipo = tipo;
        this.preco = preco;
        this.descricao = descricao;
    }

    // =========================
    // Getters e Setters
    // =========================

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public TipoFuncionario getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public TipoElevador getTipoElevador() {
        return tipoElevador;
    }

    public void setTipoElevador(TipoElevador tipoElevador) {
        this.tipoElevador = tipoElevador;
    }

    public boolean usaElevador() {
        return usaElevador;
    }

    public void setUsaElevador(boolean usaElevador) {
        this.usaElevador = usaElevador;
    }
}
