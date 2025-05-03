// Pessoa.java
package domain.entities.usuarios;

import core.Entity;
import infra.crypto.JasyptCrypto;

public abstract class Pessoa extends Entity {

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String endereco;

    public Pessoa(String nome, String email, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.email = email;
        String cpfAnonimazado = JasyptCrypto.encrypt(cpf);
        this.cpf = cpfAnonimazado;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        String cpfAnonimazado = JasyptCrypto.encrypt(cpf);
        this.cpf = cpfAnonimazado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
