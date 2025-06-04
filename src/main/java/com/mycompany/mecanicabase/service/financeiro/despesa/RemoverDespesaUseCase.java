package com.mycompany.mecanicabase.service.financeiro.despesa;

import java.util.UUID;
import com.mycompany.mecanicabase.model.financeiro.Despesa;

/**
 * Caso de uso responsável por remover uma despesa do sistema.
 */
public class RemoverDespesaUseCase {

    /**
     * Remove uma despesa com o ID especificado.
     *
     * @param id O UUID da despesa a ser removida.
     * @return true se a despesa foi encontrada e removida; false caso
     * contrário.
     */
    public boolean use(UUID id) {
        return Despesa.instances.removeIf(d -> d.getId().equals(id));
    }
}
