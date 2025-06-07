package mecanicabase.service.usuarios.cliente;

import java.util.UUID;
import mecanicabase.model.usuarios.Cliente;

/**
 * Caso de uso responsável por remover um cliente a partir de seu ID.
 */
public class RemoveClienteUseCase {

    /**
     * Remove um cliente da lista de clientes utilizando seu ID.
     *
     * @param id O ID do cliente a ser removido.
     * @return Um valor booleano indicando se o cliente foi removido com
     * sucesso.
     */
    public boolean use(String id) {
        // Converte o ID fornecido de String para UUID
        UUID uuid = UUID.fromString(id);

        // Remove o cliente da lista se o ID corresponder ao cliente a ser removido
        return Cliente.instances.removeIf(cliente -> cliente.getId().equals(uuid));
    }
}
