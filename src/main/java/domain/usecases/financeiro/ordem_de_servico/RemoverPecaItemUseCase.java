package domain.usecases.financeiro.ordem_de_servico;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.financeiro.StatusOrdemDeServico;

class RemoverPecaItemUseCase {

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
