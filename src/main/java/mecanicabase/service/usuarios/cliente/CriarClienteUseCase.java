package mecanicabase.service.usuarios.cliente;

import mecanicabase.model.usuarios.Cliente;

/**
 * Caso de uso responsável por criar um novo cliente.
 */
public class CriarClienteUseCase {

    /**
     * Cria um novo cliente com os dados fornecidos e adiciona à lista de
     * clientes.
     *
     * @param nome O nome do cliente.
     * @param endereco O endereço do cliente.
     * @param telefone O telefone do cliente.
     * @param email O email do cliente.
     * @param cpf O CPF do cliente.
     * @return O cliente criado.
     */
    public Cliente use(String nome, String endereco, String telefone, String email, String cpf) {
        // Cria um novo cliente com os dados fornecidos
        Cliente cliente = new Cliente(nome, endereco, telefone, email, cpf);

        // Adiciona o cliente à lista de instâncias de clientes
        Cliente.instances.add(cliente);

        return cliente;  // Retorna o cliente recém-criado
    }
}
