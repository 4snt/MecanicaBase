package domain.usecases.usuarios.cliente;

import java.util.UUID;

import domain.entities.usuarios.Cliente;

public class AtualizaClienteUseCase {

    public Cliente use(String id, String nome, String endereco, String telefone, String email, String cpf) {
        UUID uuid = UUID.fromString(id);

        for (Cliente cliente : Cliente.instances) {
            if (cliente.getId().equals(uuid)) {
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
                return cliente;
            }
        }

        return null;
    }
}
