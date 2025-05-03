package domain.usecases.operacao.servico;

import java.util.UUID;

import domain.entities.operacao.Servico;

/**
 * Caso de uso responsável por remover um serviço com base no seu identificador.
 */
public class RemoveServicoUseCase {

    /**
     * Remove um serviço da lista de instâncias com base no ID fornecido.
     *
     * @param id ID do serviço em formato de string UUID.
     * @return {@code true} se o serviço foi removido com sucesso, {@code false}
     * se não foi encontrado.
     */
    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Servico.instances.removeIf(servico -> servico.getId().equals(uuid));
    }
}
