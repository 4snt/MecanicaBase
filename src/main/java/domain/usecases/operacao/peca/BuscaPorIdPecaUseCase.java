package domain.usecases.operacao.peca;

import java.util.UUID;

import domain.entities.operacao.Peca;

public class BuscaPorIdPecaUseCase {
    public Peca use(String id) {
        UUID uuid = UUID.fromString(id);
        return Peca.instances.stream()
                .filter(p -> p.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
