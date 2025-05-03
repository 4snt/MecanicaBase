package domain.usecases.usuarios.administrador;

import java.util.UUID;

import domain.entities.usuarios.Administrador;

public class BuscaPorIdAdministradorUseCase {

    public Administrador use(String id) {
        UUID uuid = UUID.fromString(id);

        for (Administrador administrador : Administrador.instances) {
            if (administrador.getId().equals(uuid)) {
                return administrador;
            }
        }

        return null;
    }
}
