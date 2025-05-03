package domain.usecases.usuarios.cliente;

import java.util.UUID;

import domain.entities.usuarios.Cliente;

/**
 * Caso de uso responsável por atualizar os dados de um cliente existente.
 */
public class AtualizaClienteUseCase {

    /**
     * Atualiza os dados de um cliente identificado por seu UUID.
     *
     * @param id O ID do cliente a ser atualizado.
     * @param nome Novo nome (ou null para manter o atual).
     * @param endereco Novo endereço (ou null para manter o atual).
     * @param telefone Novo telefone (ou null para manter o atual).
     * @param email Novo email (ou null para manter o atual).
     * @param cpf Novo CPF (ou null para manter o atual).
     * @return O cliente atualizado, ou null se não encontrado.
     */
    public Cliente use(String id, String nome, String endereco, String telefone, String email, String cpf) {
        // Converte o ID fornecido para UUID
        UUID uuid = UUID.fromString(id);

        // Percorre a lista de clientes e busca o cliente pelo ID
        for (Cliente cliente : Cliente.instances) {
            if (cliente.getId().equals(uuid)) {
                // Atualiza os dados do cliente, se os novos valores não forem nulos
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
                return cliente;  // Retorna o cliente atualizado
            }
        }

        return null;  // Retorna null se o cliente não for encontrado
    }
}
