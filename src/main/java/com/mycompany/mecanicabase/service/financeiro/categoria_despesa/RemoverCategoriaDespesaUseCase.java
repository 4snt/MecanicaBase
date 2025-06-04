package com.mycompany.mecanicabase.service.financeiro.categoria_despesa;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.CategoriaDespesa;

/**
 * Caso de uso responsável por remover uma categoria de despesa existente.
 */
public class RemoverCategoriaDespesaUseCase {

    /**
     * Remove a categoria de despesa com o ID especificado, se existir.
     *
     * @param id O identificador único da categoria de despesa a ser removida.
     * @return true se a categoria foi removida com sucesso, false se não foi
     * encontrada.
     */
    public boolean use(UUID id) {
        CategoriaDespesa categoria = CategoriaDespesa.buscarPorId(id);
        if (categoria == null) {
            return false;
        }
        return CategoriaDespesa.instances.remove(categoria);
    }
}
