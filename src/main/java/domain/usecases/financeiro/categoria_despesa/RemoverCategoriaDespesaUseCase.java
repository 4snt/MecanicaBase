package domain.usecases.financeiro.categoria_despesa;

import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;

public class RemoverCategoriaDespesaUseCase {

    public boolean use(UUID id) {
        CategoriaDespesa categoria = CategoriaDespesa.buscarPorId(id);
        if (categoria == null) {
            return false;
        }
        return CategoriaDespesa.instances.remove(categoria);
    }
}
