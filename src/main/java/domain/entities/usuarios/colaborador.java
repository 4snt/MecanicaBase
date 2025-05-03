package domain.entities.usuarios;

import infra.crypto.JasyptCrypto;

/**
 * Representa um colaborador do sistema, como um Funcionário ou Administrador.
 * Esta classe estende {@link Pessoa} e adiciona autenticação via senha
 * criptografada.
 */
public class Colaborador extends Pessoa {

    /**
     * Senha criptografada do colaborador.
     */
    private String senha;

    /**
     * Construtor do colaborador.
     *
     * @param nome Nome do colaborador
     * @param email Email do colaborador (usado para login)
     * @param senha Senha em texto plano (será criptografada)
     * @param cpf CPF do colaborador
     * @param telefone Telefone do colaborador
     * @param endereco Endereço do colaborador
     */
    public Colaborador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, cpf, telefone, endereco);
        String senhaEncriptada = JasyptCrypto.encrypt(senha);
        this.senha = senhaEncriptada;
    }

    /**
     * Define uma nova senha para o colaborador, criptografando-a.
     *
     * @param senha Nova senha em texto plano
     */
    public void setSenha(String senha) {
        this.senha = JasyptCrypto.encrypt(senha);
    }

    /**
     * Compara a senha informada com a senha armazenada (criptografada).
     *
     * @param senha Senha em texto plano
     * @return true se a senha coincidir, false caso contrário
     */
    public boolean compararSenha(String senha) {
        return JasyptCrypto.compareTo(senha, this.senha);
    }
}
