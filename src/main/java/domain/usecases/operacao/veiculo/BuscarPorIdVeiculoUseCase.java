package domain.usecases.operacao.veiculo;

import java.util.UUID;

import domain.entities.operacao.Veiculo;

/**
 * Caso de uso responsável por buscar um veículo com base no seu identificador.
 */
public class BuscarPorIdVeiculoUseCase {

    /**
     * Busca um veículo a partir do ID fornecido.
     *
     * @param id ID do veículo em formato de string UUID.
     * @return A instância do veículo correspondente ou {@code null} se não
     * encontrado.
     */
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
