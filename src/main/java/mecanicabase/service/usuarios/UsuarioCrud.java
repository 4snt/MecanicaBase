package mecanicabase.service.usuarios;

import mecanicabase.core.Autenticavel;
import mecanicabase.core.Crud;

public abstract class UsuarioCrud<T extends Autenticavel> extends Crud<T> {

    public T login(String email, String senha) {
        return getInstancias().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.compararSenha(senha))
                .findFirst()
                .orElse(null);
    }

    public boolean trocarSenha(T usuario, String senhaAntiga, String novaSenha) {
        return usuario.trocarSenha(senhaAntiga, novaSenha);
    }
}
