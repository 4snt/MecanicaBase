package domain.usecases.financeiro.categoria_despesa;

import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;

public class BuscarPorIdCategoriaDespesaUseCase {

    public CategoriaDespesa use(UUID id) {
        return CategoriaDespesa.buscarPorId(id);
    }
}
