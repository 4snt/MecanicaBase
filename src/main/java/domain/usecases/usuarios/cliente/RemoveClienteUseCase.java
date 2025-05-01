package domain.usecases.usuarios.cliente;

import java.util.UUID;

import domain.entities.usuarios.Cliente;

public class RemoveClienteUseCase {

    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Cliente.instances.removeIf(cliente -> cliente.getId().equals(uuid));
    }
}