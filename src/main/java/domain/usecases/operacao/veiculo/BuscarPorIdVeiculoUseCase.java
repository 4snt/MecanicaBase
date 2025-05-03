package domain.usecases.operacao.veiculo;

import java.util.UUID;

import domain.entities.operacao.Veiculo;

public class BuscarPorIdVeiculoUseCase {

    public Veiculo use(String id) {
        UUID uuid = UUID.fromString(id);

        for (Veiculo veiculo : Veiculo.instances) {
            if (veiculo.getId().equals(uuid)) {
                return veiculo;
            }
        }

        return null;
    }
}
