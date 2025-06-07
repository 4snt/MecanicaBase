package mecanicabase.service.financeiro.ordem_de_servico;

import java.util.List;
import mecanicabase.model.financeiro.OrdemDeServico;

/**
 * Caso de uso responsável por listar todas as Ordens de Serviço registradas.
 */
public class ListaOrdemDeServicoUseCase {

    /**
     * Retorna a lista completa de Ordens de Serviço armazenadas em memória.
     *
     * @return Lista de instâncias de {@link OrdemDeServico}.
     */
    public List<OrdemDeServico> use() {
        return OrdemDeServico.instances;
    }
}
