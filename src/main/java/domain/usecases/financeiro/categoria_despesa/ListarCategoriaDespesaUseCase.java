package domain.usecases.financeiro.categoria_despesa;

import java.util.List;

import domain.entities.financeiro.CategoriaDespesa;

public class ListarCategoriaDespesaUseCase {

    public List<CategoriaDespesa> use() {
        return CategoriaDespesa.instances;
    }
}
