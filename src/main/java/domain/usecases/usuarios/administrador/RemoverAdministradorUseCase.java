package domain.usecases.usuarios.administrador;
import java.util.UUID;

import domain.entities.usuarios.Administrador;

public class RemoverAdministradorUseCase {

    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Administrador.instances.removeIf(Administrador -> Administrador.getId().equals(uuid));
    }
}