package domain.usecases.operacao.peca;

import java.util.UUID;

import domain.entities.financeiro.OrdemDeServico;
import domain.entities.financeiro.PecaItem;
import domain.entities.operacao.Peca;

public class VenderPecaUseCase {

    public PecaItem use(UUID ordemDeServicoId, UUID pecaId, int quantidade) {
        OrdemDeServico ordemDeServico = OrdemDeServico.buscarPorId(ordemDeServicoId);
        if (ordemDeServico == null) {
            throw new RuntimeException("Ordem de Serviço não foi encontrado");
        }

        Peca peca = Peca.buscarPorId(pecaId);
        if (peca == null) {
            throw new RuntimeException("Peça não foi encontrado");
        }

        boolean quantidadeInsuficiente = peca.getQuantidade() - quantidade < 0;

        if (quantidadeInsuficiente) {
            throw new RuntimeException("Quantidade da peça insuficiente");
        }

        PecaItem pecaItem = new PecaItem(peca.getId(), quantidade, peca.getValor(), ordemDeServico.getId());

        PecaItem.instances.add(pecaItem);

        return pecaItem;
    }
}
