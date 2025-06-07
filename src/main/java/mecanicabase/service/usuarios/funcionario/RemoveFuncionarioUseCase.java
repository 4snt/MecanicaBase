package mecanicabase.service.usuarios.funcionario;

import java.util.UUID;
import mecanicabase.model.usuarios.Funcionario;

/**
 * Caso de uso responsável por remover um funcionário com base no ID fornecido.
 */
public class RemoveFuncionarioUseCase {

    /**
     * Remove um funcionário da lista de funcionários, identificando-o pelo ID.
     *
     * @param id O ID do funcionário a ser removido.
     * @return true se o funcionário foi removido com sucesso, false caso
     * contrário.
     */
    public boolean use(String id) {
        // Converte o ID para UUID
        UUID uuid = UUID.fromString(id);

        // Tenta remover o funcionário com o ID correspondente e retorna se a remoção foi bem-sucedida
        return Funcionario.instances.removeIf(funcionario -> funcionario.getId().equals(uuid));
    }
}
