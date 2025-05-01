package domain.usecases.usuarios.cliente;

import java.util.UUID;

import domain.entities.usuarios.Cliente;

public class BuscaPorIdClienteUseCase {

    public Cliente use(String id) {
        UUID uuid = UUID.fromString(id);

        for (Cliente cliente : Cliente.instances) {
            if (cliente.getId().equals(uuid)) {
                return cliente;
            }
        }

        return null;
    }
}