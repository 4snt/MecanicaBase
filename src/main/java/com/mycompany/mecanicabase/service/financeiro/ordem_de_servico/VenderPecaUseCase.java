package com.mycompany.mecanicabase.service.financeiro.ordem_de_servico;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.OrdemDeServico;
import com.mycompany.mecanicabase.model.financeiro.PecaItem;
import com.mycompany.mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável por registrar a venda de uma peça em uma Ordem de
 * Serviço.
 */
public class VenderPecaUseCase {

    /**
     * Registra a venda de uma peça específica vinculada a uma Ordem de Serviço.
     *
     * @param ordemDeServicoId ID da {@link OrdemDeServico} onde a peça será
     * vendida.
     * @param pecaId ID da {@link Peca} que será vendida.
     * @param quantidade Quantidade da peça que será vendida.
     * @return Um novo {@link PecaItem} representando a venda registrada.
     * @throws RuntimeException se a ordem de serviço ou peça não forem
     * encontradas, ou se a quantidade for insuficiente.
     */
    public PecaItem use(UUID ordemDeServicoId, UUID pecaId, int quantidade) {
        OrdemDeServico ordemDeServico = OrdemDeServico.buscarPorId(ordemDeServicoId);
        if (ordemDeServico == null) {
            throw new RuntimeException("Ordem de Serviço não foi encontrada");
        }

        Peca peca = Peca.buscarPorId(pecaId);
        if (peca == null) {
            throw new RuntimeException("Peça não foi encontrada");
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
