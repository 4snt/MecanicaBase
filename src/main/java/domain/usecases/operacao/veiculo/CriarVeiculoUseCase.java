package domain.usecases.operacao.veiculo;

import java.util.UUID;

import domain.entities.operacao.Veiculo;

public class CriarVeiculoUseCase {

    public Veiculo use(
        String cor,
        int anoFabricacao,
        String placa,
        String modelo,
        UUID clienteId
    ) {
        Veiculo veiculo = new Veiculo(clienteId, modelo, placa, anoFabricacao, cor);
        Veiculo.instances.add(veiculo);
        return veiculo;
    }
}
