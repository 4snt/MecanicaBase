// Caixa.java
package model;

public class Caixa {
    private float montante;

    public void entrada(float valor) {
        this.montante += valor;
    }

    public void saida(float valor) {
        this.montante -= valor;
    }

    // Getters e Setters
}
