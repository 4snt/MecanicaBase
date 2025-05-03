package domain.usecases.operacao.servico;

import java.util.UUID;

import domain.entities.operacao.Servico;

public class RemoveServicoUseCase {

    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Servico.instances.removeIf(servico -> servico.getId().equals(uuid));
    }
}
