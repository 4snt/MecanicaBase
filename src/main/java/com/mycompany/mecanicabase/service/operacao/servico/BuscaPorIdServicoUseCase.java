package com.mycompany.mecanicabase.service.operacao.servico;

import java.util.UUID;
import com.mycompany.mecanicabase.model.operacao.Servico;

public class BuscaPorIdServicoUseCase {

    /**
     * Busca um serviço pelo ID.
     *
     * @param id ID do serviço em formato String ou UUID.
     * @return o serviço correspondente ou null se não encontrado.
     */
    public Servico use(String id) {
        UUID uuid = UUID.fromString(id);

        return Servico.instances.stream()
                .filter(servico -> servico.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
