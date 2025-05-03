package domain.usecases.operacao.veiculo;

import java.util.UUID;

import domain.entities.operacao.Sistema;
import domain.entities.operacao.Veiculo;

public class RemoverVeiculoUsecase {

    public void use(String id) {
        UUID uuid = UUID.fromString(id);

        Veiculo.instances.removeIf(Veiculo -> Veiculo.getId().equals(uuid));

        Sistema.reduzirTotalVeiculos();
    }
}
