package mecanicabase.service.usuarios;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.usuarios.Cliente;

public class ClienteCrud extends Crud<Cliente> {

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
        String endereco = (String) params[1];
        String telefone = (String) params[2];
        String email = (String) params[3];
        String cpf = (String) params[4];
        Cliente cliente = new Cliente(nome, endereco, telefone, email, cpf);

        Cliente.instances.add(cliente);

        return cliente;
    }

    @Override
    protected void atualizarInstancia(Cliente cliente, Object... params) {
        String nome = (String) params[0];
        String endereco = (String) params[1];
        String telefone = (String) params[2];
        String email = (String) params[3];
        String cpf = (String) params[4];

        if (nome != null) {
            cliente.setNome(nome);
        }
        if (endereco != null) {
            cliente.setEndereco(endereco);
        }
        if (telefone != null) {
            cliente.setTelefone(telefone);
        }
        if (email != null) {
            cliente.setEmail(email);
        }
        if (cpf != null) {
            cliente.setCpf(cpf);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String nome = (String) params[0];
        String cpf = (String) params[4];
        return nome != null && !nome.isBlank() && cpf != null && !cpf.isBlank();
    }

    @Override
    protected boolean validarAtualizacao(Cliente cliente, Object... params) {
        // Evita sobrescrever com valores nulos ou em branco
        return cliente != null;
    }
}
