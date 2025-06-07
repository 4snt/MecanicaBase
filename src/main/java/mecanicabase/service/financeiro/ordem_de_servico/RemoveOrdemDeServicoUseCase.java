package mecanicabase.service.financeiro.ordem_de_servico;

import java.util.UUID;
import mecanicabase.model.financeiro.OrdemDeServico;

/**
 * Caso de uso responsável por remover uma Ordem de Serviço existente a partir
 * do seu ID.
 */
public class RemoveOrdemDeServicoUseCase {

    /**
     * Remove uma Ordem de Serviço da lista de instâncias com base no ID
     * fornecido.
     *
     * @param id ID da Ordem de Serviço em formato String.
     * @return true se a ordem foi removida com sucesso; false se nenhuma ordem
     * foi encontrada.
     */
    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);
        return OrdemDeServico.instances.removeIf(os -> os.getId().equals(uuid));
    }
}
