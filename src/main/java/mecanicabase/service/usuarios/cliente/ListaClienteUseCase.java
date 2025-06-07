package mecanicabase.service.usuarios.cliente;

import java.util.List;
import java.util.stream.Collectors;
import mecanicabase.model.usuarios.Cliente;

/**
 * Caso de uso responsável por listar os clientes com a opção de filtro por nome
 * ou email.
 */
public class ListaClienteUseCase {

    /**
     * Lista todos os clientes, podendo aplicar um filtro para busca por nome ou
     * email.
     *
     * @param filtro O termo de busca, que será aplicado tanto ao nome quanto ao
     * email dos clientes.
     * @return Uma lista de clientes que correspondem ao filtro, ou todos os
     * clientes se o filtro for nulo ou em branco.
     */
    public List<Cliente> use(String filtro) {
        // Se o filtro for nulo ou em branco, retorna todos os clientes
        if (filtro == null || filtro.isBlank()) {
            return Cliente.instances;
        }

        // Converte o filtro para minúsculas para a busca case-insensitive
        String filtroLower = filtro.toLowerCase();

        // Filtra a lista de clientes onde o nome ou email contém o filtro fornecido
        return Cliente.instances.stream()
                .filter(cliente
                        -> cliente.getNome().toLowerCase().contains(filtroLower)
                || // Filtro por nome
                cliente.getEmail().toLowerCase().contains(filtroLower)) // Filtro por email
                .collect(Collectors.toList());  // Retorna a lista filtrada
    }
}
