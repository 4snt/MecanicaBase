package domain.usecases.operacao.veiculo;
import java.util.UUID;

import domain.entities.operacao.Veiculo;

public class RemoverVeiculoUsecase {
    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Veiculo.instances.removeIf(Veiculo -> Veiculo.getId().equals(uuid));
}
}