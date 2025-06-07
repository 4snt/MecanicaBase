package mecanicabase.service.operacao.veiculo;

import java.util.UUID;
import mecanicabase.model.operacao.Sistema;
import mecanicabase.model.operacao.Veiculo;

/**
 * Caso de uso responsável pela criação de um novo veículo.
 */
public class CriarVeiculoUseCase {

    /**
     * Cria um novo veículo com os dados fornecidos e o adiciona à lista de
     * instâncias. Além disso, incrementa o total de veículos no sistema.
     *
     * @param cor Cor do veículo.
     * @param anoFabricacao Ano de fabricação do veículo.
     * @param placa Placa do veículo.
     * @param modelo Modelo do veículo.
     * @param clienteId ID do cliente proprietário do veículo.
     * @return A instância do veículo criado.
     */
    public Veiculo use(
            String cor,
            int anoFabricacao,
            String placa,
            String modelo,
            UUID clienteId
    ) {
        Veiculo veiculo = new Veiculo(clienteId, modelo, placa, anoFabricacao, cor);
        Veiculo.instances.add(veiculo);
        Sistema.incrementarTotalVeiculos();
        return veiculo;
    }
}
