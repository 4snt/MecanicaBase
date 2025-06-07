package mecanicabase.service.usuarios.cliente;

import java.util.UUID;
import mecanicabase.model.usuarios.Cliente;

/**
 * Caso de uso responsável por buscar um cliente pelo seu ID.
 */
public class BuscaPorIdClienteUseCase {

    /**
     * Busca um cliente através do seu ID.
     *
     * @param id O ID do cliente a ser buscado.
     * @return O cliente encontrado, ou null caso não exista.
     */
    public Cliente use(String id) {
        // Converte o ID fornecido de String para UUID
        UUID uuid = UUID.fromString(id);

        // Percorre a lista de clientes e verifica se algum tem o ID correspondente
        for (Cliente cliente : Cliente.instances) {
            if (cliente.getId().equals(uuid)) {
                return cliente;  // Retorna o cliente encontrado
            }
        }

        return null;  // Retorna null se o cliente não for encontrado
    }
}
