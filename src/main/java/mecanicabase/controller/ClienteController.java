package mecanicabase.controller;

import java.util.List;

import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.usuarios.ClienteCrud;

/**
 * Controller responsável por operações relacionadas a clientes.
 * <p>
 * Documentação detalhada será adicionada futuramente.
 * </p>
 */
public class ClienteController {

    private final ClienteCrud crud;

    /**
     * Construtor que recebe o ClienteCrud vindo do ApplicationContext.
     */
    public ClienteController(ClienteCrud crud) {
        this.crud = crud;
    }

    /**
     * Cria um novo cliente com os dados fornecidos.
     */
    public Cliente criar(String nome, String telefone, String endereco, String email, String cpf) {
        return crud.criar(true, nome, telefone, endereco, email, cpf);
    }

    /**
     * Lista todos os clientes (sem filtro).
     */
    public List<Cliente> listar() {
        return crud.listarTodos();
    }

    /**
     * Lista clientes por filtro (nome ou email).
     */
    public List<Cliente> listar(String filtro) {
        return crud.buscarPorFiltro(filtro);
    }

    /**
     * Busca um cliente pelo ID.
     */
    public Cliente buscarPorId(String id) {
        return crud.buscarPorId(id);
    }

    /**
     * Atualiza os dados de um cliente com base no ID.
     */
    public Cliente atualizar(String id, String nome, String telefone, String endereco, String email, String cpf) {
        return crud.atualizar(id, true, nome, telefone, endereco, email, cpf);
    }

    /**
     * Remove um cliente com base no ID.
     */
    public boolean remover(String id) {
        return crud.removerPorId(id);
    }
}
