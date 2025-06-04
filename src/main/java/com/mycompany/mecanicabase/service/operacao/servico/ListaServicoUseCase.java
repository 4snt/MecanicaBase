package com.mycompany.mecanicabase.service.operacao.servico;

import java.util.List;
import com.mycompany.mecanicabase.model.operacao.Servico;

/**
 * Caso de uso responsável por listar todos os serviços cadastrados.
 */
public class ListaServicoUseCase {

    /**
     * Retorna a lista de todos os serviços existentes.
     *
     * @return Lista de instâncias de {@link Servico}.
     */
    public List<Servico> use() {
        return Servico.instances;
    }
}
