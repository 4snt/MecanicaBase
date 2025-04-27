// Estoque.java
package model;

public class Estoque {
    private String descricao;
    private int quantidade;

    public void adicionar(int quantidade) {
        this.quantidade += quantidade;
    }

    public void remover(int quantidade) {
        this.quantidade -= quantidade;
    }

    // Getters e Setters
}
