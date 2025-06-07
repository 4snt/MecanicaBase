package mecanicabase.controller;

import java.util.List;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.usuarios.cliente.AtualizaClienteUseCase;
import mecanicabase.service.usuarios.cliente.BuscaPorIdClienteUseCase;
import mecanicabase.service.usuarios.cliente.CriarClienteUseCase;
import mecanicabase.service.usuarios.cliente.ListaClienteUseCase;
import mecanicabase.service.usuarios.cliente.RemoveClienteUseCase;

/**
 * Controller responsável por gerenciar as operações relacionadas a clientes.
 *
 * Atua como intermediário entre a camada de apresentação (UI) e os casos de uso
 * (domínio).
 */
public class ClienteController {

    private final CriarClienteUseCase criar = new CriarClienteUseCase();
    private final ListaClienteUseCase listar = new ListaClienteUseCase();
    private final BuscaPorIdClienteUseCase buscar = new BuscaPorIdClienteUseCase();
    private final AtualizaClienteUseCase atualizar = new AtualizaClienteUseCase();
    private final RemoveClienteUseCase remover = new RemoveClienteUseCase();

    /**
     * Cria um novo cliente com os dados fornecidos.
     *
     * @param nome Nome do cliente
     * @param endereco Endereço do cliente
     * @param telefone Telefone do cliente
     * @param email Email do cliente
     * @param cpf CPF do cliente
     * @return Cliente criado
     */
    public Cliente criar(String nome, String endereco, String telefone, String email, String cpf) {
        return criar.use(nome, endereco, telefone, email, cpf);
    }

    /**
     * Lista os clientes que correspondem ao filtro fornecido.
     *
     * @param filtro Texto usado para filtrar os clientes (pode ser nome, CPF,
     * etc.)
     * @return Lista de clientes filtrados
     */
    public List<Cliente> listar(String filtro) {
        return listar.use(filtro);
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id ID do cliente
     * @return Cliente encontrado ou null se não existir
     */
    public Cliente buscarPorId(String id) {
        return buscar.use(id);
    }

    /**
     * Atualiza os dados de um cliente com base no ID.
     *
     * @param id ID do cliente
     * @param nome Novo nome
     * @param endereco Novo endereço
     * @param telefone Novo telefone
     * @param email Novo email
     * @param cpf Novo CPF
     * @return Cliente atualizado
     */
    public Cliente atualizar(String id, String nome, String endereco, String telefone, String email, String cpf) {
        return atualizar.use(id, nome, endereco, telefone, email, cpf);
    }

    /**
     * Remove um cliente com base no ID.
     *
     * @param id ID do cliente
     * @return true se o cliente foi removido com sucesso, false caso contrário
     */
    public boolean remover(String id) {
        return remover.use(id);
    }
}
