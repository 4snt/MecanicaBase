package com.mycompany.mecanicabase.service.financeiro.ordem_de_servico;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;

/**
 * Caso de uso responsável por criar uma nova Ordem de Serviço.
 */
public class CriarOrdemDeServicoUseCase {

    /**
     * Cria uma nova Ordem de Serviço associada a um cliente.
     *
     * @param clienteId UUID do cliente relacionado à ordem de serviço.
     * @return A nova instância de Ordem de Serviço criada.
     */
    public OrdemDeServico use(UUID clienteId) {
        OrdemDeServico os = new OrdemDeServico(clienteId);
        OrdemDeServico.instances.add(os);
        return os;
    }
}
