package domain.usecases.usuarios.administrador;

import java.util.UUID;

import domain.entities.usuarios.Administrador;

public class AtualizarAdministradorUseCase {

    public Administrador use(String id, String nome, String email, String senha, String telefone, String endereco) {
        UUID uuid = UUID.fromString(id);

        for (Administrador Administrador : Administrador.instances) {
            if (Administrador.getId().equals(uuid) && Administrador instanceof Administrador adm) {
                if (nome != null) adm.setNome(nome);
                if (email != null) adm.setEmail(email);
                if (senha != null) adm.setSenha(senha);
                if (telefone != null) adm.setTelefone(telefone);
                if (endereco != null) adm.setEndereco(endereco);
                return adm;
            }
        }

        return null;
    }
}
