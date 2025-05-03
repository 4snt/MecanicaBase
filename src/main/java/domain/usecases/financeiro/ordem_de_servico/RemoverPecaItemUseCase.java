package domain.usecases.financeiro.ordem_de_servico;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.StatusOrdemDeServico;

/**
 * Caso de uso responsável por remover uma peça associada a uma Ordem de
 * Serviço, desde que a mesma esteja com o status ABERTO.
 */
class RemoverPecaItemUseCase {

    /**
     * Remove um item de peça de uma Ordem de Serviço com base no ID do item.
     *
     * @param pecaItemId ID do {@link PecaItem} a ser removido.
     * @throws RuntimeException se a peça, a ordem de serviço ou as condições
     * não forem atendidas.
     */
    public void use(UUID pecaItemId) {
        PecaItem pecaItem = PecaItem.buscarPorId(pecaItemId);

        if (pecaItem == null) {
            throw new RuntimeException("PeçaItem não encontrado");
        }

        OrdemDeServico os = pecaItem.getOrdemDeServico();

        if (os == null) {
            throw new RuntimeException("Ordem de Serviço não encontrada");
        }

        if (os.getStatus() != StatusOrdemDeServico.ABERTO) {
            throw new RuntimeException("Esta Ordem de Serviço já foi fechada");
        }

        os.removePeca(pecaItem.getId());
    }
}
