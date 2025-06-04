package com.mycompany.mecanicabase.service.financeiro.categoria_despesa;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.CategoriaDespesa;

/**
 * Caso de uso responsável por atualizar o título de uma categoria de despesa.
 */
public class AtualizarCategoriaDespesaUseCase {

    /**
     * Atualiza o título de uma categoria de despesa identificada pelo ID. Como
     * o atributo título é final, a categoria antiga é removida e uma nova é
     * criada.
     *
     * @param id O UUID da categoria a ser atualizada.
     * @param novoTitulo O novo título a ser atribuído à categoria.
     * @return A nova instância de CategoriaDespesa com o título atualizado, ou
     * null se a categoria não for encontrada.
     */
    public CategoriaDespesa use(UUID id, String novoTitulo) {
        CategoriaDespesa categoria = CategoriaDespesa.buscarPorId(id);
        if (categoria == null) {
            return null;
        }

        // Como `titulo` é final, não pode ser alterado. Teria que recriar:
        CategoriaDespesa nova = new CategoriaDespesa(novoTitulo);
        CategoriaDespesa.instances.remove(categoria);
        CategoriaDespesa.instances.add(nova);

        return nova;
    }
}
