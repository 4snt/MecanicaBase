package mecanicabase.model.usuarios;

import mecanicabase.core.Entity;
import mecanicabase.infra.crypto.HashUtil;
import mecanicabase.infra.crypto.JasyptCrypto;

/**
 * Classe abstrata que representa uma pessoa no sistema. Pode ser estendida por
 * entidades como Cliente, Colaborador, etc.
 */
public abstract class Pessoa extends Entity {

    private String nome;
    private String email;
    private String cpfHash;             // Hash determinístico para busca
    private String cpfCriptografado;    // Criptografado para segurança
    private String telefone;
    private String endereco;

    /**
     * Construtor completo da classe Pessoa.
     *
     * @param nome Nome da pessoa
     * @param email Email
     * @param cpf CPF em texto claro (será armazenado como hash + criptografado)
     * @param telefone Telefone
     * @param endereco Endereço
     */
    public Pessoa(String nome, String email, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.email = email;
        setCpf(cpf); // chama o setter que gera hash + criptografa
        this.telefone = telefone;
        this.endereco = endereco;
        System.out.println("[DEBUG Pessoa] cpf recebido: '" + cpf + "'");
    }

    // ============================ GETTERS ============================
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    /**
     * Retorna o hash determinístico do CPF (para buscas).
     */
    public String getCpf() {
        return cpfHash;
    }

    /**
     * Retorna apenas os últimos 4 dígitos do CPF original (para exibição).
     */
    public String getCpfParcial() {
        try {
            String original = getCpfOriginal();
            return "****" + original.substring(original.length() - 4);
        } catch (Exception e) {
            return "****????";
        }
    }

    /**
     * Retorna o CPF original em texto claro (somente se necessário).
     */
    public String getCpfOriginal() {
        return JasyptCrypto.decrypt(cpfCriptografado);
    }

    public String getCpfCriptografado() {
        return cpfCriptografado;
    }

    public String getCpfHash() {
        return cpfHash;
    }

    // ============================ SETTERS ============================
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Define o CPF da pessoa, armazenando de forma segura. Gera tanto o hash
     * (para busca) quanto a criptografia (para privacidade).
     */
    public void setCpf(String cpf) {
        this.cpfHash = HashUtil.hash(cpf);                 // determinístico para busca
        this.cpfCriptografado = JasyptCrypto.encrypt(cpf); // criptografado para segurança
    }

    public String getCpfSeguro() {
        try {
            String original = JasyptCrypto.decrypt(cpfCriptografado);
            return "****" + original.substring(original.length() - 4);
        } catch (Exception e) {
            return "****????";
        }
    }
}
