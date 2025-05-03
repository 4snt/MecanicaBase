package domain.entities.operacao;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import core.Entity;

/**
 * Representa uma peça disponível para uso em ordens de serviço.
 */
public class Peca extends Entity {

    public static List<Peca> instances = new ArrayList<>();

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

     public static Peca buscarPorId(UUID Id){
        return Peca.instances.stream()
        .filter(s -> s.getId().equals(Id))
        .findFirst()
        .orElse(null);
    }
}
