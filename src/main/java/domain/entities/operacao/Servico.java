package domain.entities.operacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um serviço oferecido pela oficina, como troca de óleo, revisão, etc.
 */
public class Servico {

    /**
     * Lista que simula um banco de dados em memória para persistência via JSON.
     */
    public static List<Servico> instances = new ArrayList<>();

    // =========================
    // Atributos do Serviço
    // =========================

    private String tipo;         // Tipo do serviço (ex: "Troca de óleo")
    private float preco;         // Preço do serviço
    private String descricao;    // Descrição detalhada do serviço
    private String prioridade;   // Prioridade do serviço (ex: "Alta", "Média", "Baixa")

    // =========================
    // Construtor
    // =========================

    /**
     * Construtor para criar um novo serviço.
     *
     * @param tipo Tipo do serviço
     * @param preco Preço do serviço
     * @param descricao Descrição do serviço
     * @param prioridade Prioridade do serviço
     */
    public Servico(String tipo, float preco, String descricao, String prioridade) {
        this.tipo = tipo;
        this.preco = preco;
        this.descricao = descricao;
        this.prioridade = prioridade;
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

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
