// Funcionario.java
package domain.entities.usuarios;

import infra.crypto.JasyptCrypto;

public class colaborador extends Pessoa {

    private String senha;

    public colaborador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, cpf, telefone, endereco);

        String senhaEncriptada = JasyptCrypto.encrypt(senha);
        this.senha = senhaEncriptada;
    }
    
    public void setSenha(String senha) {
        this.senha = JasyptCrypto.encrypt(senha);
    }

    public boolean compararSenha(String senha) {
        return JasyptCrypto.compareTo(senha, this.senha);
    }
}
