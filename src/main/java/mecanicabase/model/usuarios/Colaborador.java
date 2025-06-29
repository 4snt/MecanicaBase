package mecanicabase.model.usuarios;

import mecanicabase.core.Autenticavel;
import mecanicabase.infra.crypto.JasyptCrypto;

/**
 * Representa um colaborador do sistema, como um Funcionário ou Administrador.
 * Esta classe estende {@link Pessoa} e adiciona autenticação via senha
 * criptografada.
 */
public class Colaborador extends Pessoa implements Autenticavel {

    protected String senha;

    public Colaborador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, cpf, telefone, endereco);
        this.senha = JasyptCrypto.encrypt(senha);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public boolean compararSenha(String senha) {
        return JasyptCrypto.compareTo(senha, this.senha);
    }

    @Override
    public void setSenha(String senha) {
        this.senha = JasyptCrypto.encrypt(senha);
    }

    protected String getSenha() {
        return this.senha;
    }

    @Override
    public boolean trocarSenha(String senhaAntiga, String novaSenha) {
        if (compararSenha(senhaAntiga)) {
            setSenha(novaSenha);
            return true;
        }
        return false;
    }

}
