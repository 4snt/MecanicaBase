package domain.usecases.operacao.servico;

import java.util.UUID;

import domain.entities.operacao.Servico;

public class BuscaPorIdServicoUseCase {

    /**
     * Busca um serviço pelo ID.
     *
     * @param id ID do serviço em formato String ou UUID.
     * @return o serviço correspondente ou null se não encontrado.
     */
    public Servico use(String id) {
        UUID uuid = UUID.fromString(id);

        return Servico.instances.stream()
                .filter(servico -> servico.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
