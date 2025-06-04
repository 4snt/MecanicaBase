package com.mycompany.mecanicabase.service.operacao.veiculo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.mycompany.mecanicabase.model.operacao.Veiculo;

/**
 * Caso de uso responsável por listar os veículos com base em um filtro de
 * pesquisa.
 */
public class ListaVeiculoUseCase {

    /**
     * Retorna uma lista de veículos filtrados de acordo com o filtro fornecido.
     * Se o filtro for {@code null} ou vazio, retorna todos os veículos. O
     * filtro pode ser usado para buscar pela placa, modelo ou ID do cliente.
     *
     * @param filtro Filtro de pesquisa (placa, modelo ou ID do cliente).
     * @return Lista de veículos que atendem ao critério de filtro.
     */
    public List<Veiculo> use(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return Veiculo.instances;
        }

        String filtroLower = filtro.toLowerCase();
        return Veiculo.instances.stream()
                .filter(veiculo
                        -> veiculo.getPlaca().toLowerCase().contains(filtroLower)
                || veiculo.getModelo().toLowerCase().contains(filtroLower)
                || veiculo.getCliente().getId().equals(UUID.fromString(filtroLower))
                )
                .collect(Collectors.toList());
    }
}
