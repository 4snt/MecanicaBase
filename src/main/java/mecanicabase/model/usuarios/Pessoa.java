// Pessoa.java
package mecanicabase.model.usuarios;

import mecanicabase.core.Entity;
import mecanicabase.infra.crypto.JasyptCrypto;

/**
 * Classe abstrata que representa uma pessoa no sistema. Pode ser estendida por
 * entidades como Cliente, Colaborador, etc. Possui atributos comuns como nome,
 * email, CPF, telefone e endereço.
 */
public abstract class Pessoa extends Entity {

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String endereco;

    /**
     * Construtor da classe Pessoa.
     *
     * @param nome Nome da pessoa
     * @param email Email da pessoa
     * @param cpf CPF da pessoa (criptografado)
     * @param telefone Telefone da pessoa
     * @param endereco Endereço da pessoa
     */
    public Pessoa(String nome, String email, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.email = email;
        String cpfAnonimazado = JasyptCrypto.encrypt(cpf); // Criptografa o CPF para segurança
        this.cpf = cpfAnonimazado;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    /**
     * Retorna o nome da pessoa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da pessoa.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o email da pessoa.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email da pessoa.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o CPF da pessoa (criptografado).
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define e criptografa o CPF da pessoa.
     */
    public void setCpf(String cpf) {
        String cpfAnonimazado = JasyptCrypto.encrypt(cpf);
        this.cpf = cpfAnonimazado;
    }

    /**
     * Retorna o telefone da pessoa.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone da pessoa.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o endereço da pessoa.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço da pessoa.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
