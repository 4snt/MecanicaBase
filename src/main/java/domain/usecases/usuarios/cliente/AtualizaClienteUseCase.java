package domain.usecases.usuarios.cliente;

import java.util.UUID;

import domain.entities.usuarios.Cliente;

public class AtualizaClienteUseCase {

    public Cliente use(String id, String nome, String endereco, String telefone, String email, String cpf) {
        UUID uuid = UUID.fromString(id);

        for (Cliente cliente : Cliente.instances) {
            if (cliente.getId().equals(uuid)) {
                cliente.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setCpf(cpf);
                return cliente;
            }
        }

        return null;
    }
}