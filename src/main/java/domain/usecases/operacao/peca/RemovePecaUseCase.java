package domain.usecases.operacao.peca;

import java.util.UUID;

import domain.entities.operacao.Peca;

public class RemovePecaUseCase {
    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);
        return Peca.instances.removeIf(p -> p.getId().equals(uuid));
    }
}
