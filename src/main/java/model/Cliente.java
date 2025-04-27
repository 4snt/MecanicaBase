// Cliente.java
package model;

public class Cliente extends Pessoa {
    private String telefone;
    private String endereco;
    private String email;
    private String cpfAnon;

    public Cliente(String nome, String telefone, String endereco, String email, String cpfAnon) {
        super(nome);
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.cpfAnon = cpfAnon;
    }

    // Getters e Setters
}
