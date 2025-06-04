package com.mycompany.mecanicabase.service.operacao.veiculo;

import java.util.UUID;
import com.mycompany.mecanicabase.model.operacao.Sistema;
import com.mycompany.mecanicabase.model.operacao.Veiculo;

/**
 * Caso de uso responsável por remover um veículo com base no seu identificador.
 */
public class RemoverVeiculoUsecase {

    /**
     * Remove um veículo da lista de instâncias com base no ID fornecido. Além
     * disso, reduz o total de veículos no sistema.
     *
     * @param id ID do veículo em formato de string UUID.
     */
    public void use(String id) {
        UUID uuid = UUID.fromString(id);

        Veiculo.instances.removeIf(Veiculo -> Veiculo.getId().equals(uuid));

        Sistema.reduzirTotalVeiculos();
    }
}
