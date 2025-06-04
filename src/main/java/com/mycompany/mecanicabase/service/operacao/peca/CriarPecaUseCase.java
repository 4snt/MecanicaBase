package com.mycompany.mecanicabase.service.operacao.peca;

import com.mycompany.mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável pela criação de uma nova peça.
 */
public class CriarPecaUseCase {

    /**
     * Cria uma nova peça com os dados fornecidos e a adiciona à lista de
     * instâncias.
     *
     * @param nome Nome da peça.
     * @param valor Valor da peça.
     * @param quantidade Quantidade disponível da peça.
     * @return A instância da peça criada.
     */
    public Peca use(String nome, float valor, int quantidade) {
        Peca peca = new Peca(nome, valor, quantidade);
        Peca.instances.add(peca);
        return peca;
    }
}
