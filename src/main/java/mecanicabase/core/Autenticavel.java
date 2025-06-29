package mecanicabase.core;

public interface Autenticavel {

    String getEmail();

    boolean compararSenha(String senha);

    void setSenha(String novaSenha);

    boolean trocarSenha(String senhaAntiga, String novaSenha);
}
