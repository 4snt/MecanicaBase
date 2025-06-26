package mecanicabase.service.usuarios;

import java.util.List;
import java.util.UUID;

import mecanicabase.core.Crud;
import mecanicabase.model.usuarios.Cliente;

/**
 * CRUD específico para a entidade Cliente.
 * <p>
 * Garante que os métodos de atualização e exclusão funcionem corretamente ao
 * reindexar todos os clientes existentes no construtor.
 * </p>
 * <p>
 * Caso outros módulos usem listas estáticas para armazenar entidades,
 * recomenda-se aplicar o mesmo padrão de reindexação no construtor.
 * </p>
 */
public class ClienteCrud extends Crud<Cliente> {

    public ClienteCrud() {
        reindexar();
    }

    @Override
    protected List<Cliente> getInstancias() {
        return Cliente.instances;
    }

    @Override
    protected UUID getId(Cliente entidade) {
        return entidade.getId();
    }

    public List<Cliente> buscarPorFiltro(String termo) {
        return Cliente.instances.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termo.toLowerCase())
                || c.getCpf().contains(termo))
                .toList();
    }

    @Override
    protected Cliente criarInstancia(Object... params) {
        String nome = (String) params[0];
        String telefone = (String) params[1];
        String endereco = (String) params[2];
        String email = (String) params[3];
        String cpf = (String) params[4];
        return new Cliente(nome, telefone, endereco, email, cpf);
    }

    @Override
    protected void atualizarInstancia(Cliente cliente, Object... params) {
        String nome = (String) params[0];
        String telefone = (String) params[1];
        String endereco = (String) params[2];
        String email = (String) params[3];
        String cpf = (String) params[4];

        if (nome != null) {
            cliente.setNome(nome);
        }
        if (telefone != null) {
            cliente.setTelefone(telefone);
        }
        if (endereco != null) {
            cliente.setEndereco(endereco);
        }
        if (email != null) {
            cliente.setEmail(email);
        }
        if (cpf != null) {
            cliente.setCpf(cpf);
        }
        System.out.println("Atualizando cliente: " + cliente.getId() + " - " + nome + ", " + telefone + ", " + endereco + ", " + email + ", " + cpf);
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String nome = (String) params[0];
        String cpf = (String) params[4];
        return nome != null && !nome.isBlank() && cpf != null && !cpf.isBlank();
    }

    @Override
    protected boolean validarAtualizacao(Cliente cliente, Object... params) {
        return cliente != null;
    }
}
