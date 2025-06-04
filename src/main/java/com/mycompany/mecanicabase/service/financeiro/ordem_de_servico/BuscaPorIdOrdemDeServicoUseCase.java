package com.mycompany.mecanicabase.service.financeiro.ordem_de_servico;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;

/**
 * Caso de uso responsável por buscar uma Ordem de Serviço a partir de seu ID.
 */
public class BuscaPorIdOrdemDeServicoUseCase {

    /**
     * Busca uma instância de Ordem de Serviço pelo seu identificador.
     *
     * @param id ID da Ordem de Serviço (formato UUID em string).
     * @return A Ordem de Serviço correspondente ou null se não encontrada.
     */
    public OrdemDeServico use(String id) {
        UUID uuid = UUID.fromString(id);

        for (OrdemDeServico os : OrdemDeServico.instances) {
            if (os.getId().equals(uuid)) {
                return os;
            }
        }

        return null;
    }
}
