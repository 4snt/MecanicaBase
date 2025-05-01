package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    public static List<Cliente> instances = new ArrayList<>();
    private String telefone;
    private String endereco;
    private String email;
    private String cpf;

    public Cliente(String nome, String telefone, String endereco, String email, String cpf) {
        super(nome);
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.cpf = cpf;
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
        this.cpf = cpf;
    }
}
