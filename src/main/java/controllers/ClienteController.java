package controllers;

import java.util.List;

import domain.entities.usuarios.Cliente;
import domain.usecases.usuarios.cliente.AtualizaClienteUseCase;
import domain.usecases.usuarios.cliente.BuscaPorIdClienteUseCase;
import domain.usecases.usuarios.cliente.CriarClienteUseCase;
import domain.usecases.usuarios.cliente.ListaClienteUseCase;
import domain.usecases.usuarios.cliente.RemoveClienteUseCase;

public class ClienteController {
    private final CriarClienteUseCase criar = new CriarClienteUseCase();
    private final ListaClienteUseCase listar = new ListaClienteUseCase();
    private final BuscaPorIdClienteUseCase buscar = new BuscaPorIdClienteUseCase();
    private final AtualizaClienteUseCase atualizar = new AtualizaClienteUseCase();
    private final RemoveClienteUseCase remover = new RemoveClienteUseCase();

    public Cliente criar(String nome, String endereco, String telefone, String email, String cpf) {
        return criar.use(nome, endereco, telefone, email, cpf);
    }

    public List<Cliente> listar(String filtro) {
        return listar.use(filtro);
    }

    public Cliente buscarPorId(String id) {
        return buscar.use(id);
    }

    public Cliente atualizar(String id, String nome, String endereco, String telefone, String email, String cpf) {
        return atualizar.use(id, nome, endereco, telefone, email, cpf);
    }

    public boolean remover(String id) {
        return remover.use(id);
    }
}
