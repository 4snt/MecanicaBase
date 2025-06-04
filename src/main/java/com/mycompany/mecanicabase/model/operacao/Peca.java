package com.mycompany.mecanicabase.model.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.mycompany.mecanicabase.core.Entity;

/**
 * Representa uma peça utilizada em ordens de serviço dentro da oficina. Cada
 * peça possui um nome, valor e quantidade em estoque.
 */
public class Peca extends Entity {

    /**
     * Lista que simula o banco de dados em memória com todas as instâncias de
     * peças.
     */
    public static List<Peca> instances = new ArrayList<>();

    private String nome;
    private float valor;
    private int quantidade;

    /**
     * Construtor da classe Peca.
     *
     * @param nome Nome da peça
     * @param valor Valor unitário da peça
     * @param quantidade Quantidade disponível em estoque
     */
    public Peca(String nome, float valor, int quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o nome da peça.
     *
     * @return Nome da peça
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da peça.
     *
     * @param nome Novo nome da peça
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor unitário da peça.
     *
     * @return Valor da peça
     */
    public float getValor() {
        return valor;
    }

    /**
     * Define o valor unitário da peça.
     *
     * @param valor Novo valor da peça
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * Retorna a quantidade disponível em estoque.
     *
     * @return Quantidade em estoque
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade disponível em estoque.
     *
     * @param quantidade Nova quantidade em estoque
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Reduz a quantidade em estoque da peça, se houver quantidade suficiente.
     *
     * @param quantidade Quantidade a ser reduzida
     * @throws IllegalArgumentException se a quantidade solicitada for maior que
     * a disponível
     */
    public void reduzirEstoque(int quantidade) {
        if (quantidade <= this.quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade em estoque insuficiente.");
        }
    }

    /**
     * Adiciona a quantidade especificada ao estoque da peça.
     *
     * @param quantidade Quantidade a ser adicionada
     */
    public void adicionarEstoque(int quantidade) {
        this.quantidade += quantidade;
    }

    /**
     * Busca uma peça pelo seu ID na lista de instâncias.
     *
     * @param id UUID da peça
     * @return A peça correspondente ou {@code null} se não encontrada
     */
    public static Peca buscarPorId(UUID id) {
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format(
                "Peca [ID=%s, Nome='%s', Valor=%.2f, Quantidade=%d]",
                getId(),
                nome,
                valor,
                quantidade
        );
    }
}
