package com.mycompany.mecanicabase.service.operacao.peca;

import java.util.UUID;
import com.mycompany.mecanicabase.model.operacao.Peca;

/**
 * Caso de uso responsável por atualizar os dados de uma peça existente.
 */
public class AtualizaPecaUseCase {

    /**
     * Atualiza os dados de uma peça com base no ID fornecido.
     *
     * @param id ID da peça a ser atualizada (em formato de string UUID).
     * @param nome Novo nome da peça (pode ser null para não alterar).
     * @param valor Novo valor da peça (pode ser null para não alterar).
     * @param quantidade Nova quantidade da peça (pode ser null para não
     * alterar).
     * @return A peça atualizada, ou {@code null} se nenhuma peça for encontrada
     * com o ID fornecido.
     */
    public Peca use(String id, String nome, Float valor, Integer quantidade) {
        UUID uuid = UUID.fromString(id);

        for (Peca peca : Peca.instances) {
            if (peca.getId().equals(uuid)) {
                if (nome != null) {
                    peca.setNome(nome);
                }
                if (valor != null) {
                    peca.setValor(valor);
                }
                if (quantidade != null) {
                    peca.setQuantidade(quantidade);
                }
                return peca;
            }
        }

        return null;
    }
}
