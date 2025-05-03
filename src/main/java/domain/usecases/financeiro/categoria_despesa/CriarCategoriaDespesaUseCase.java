package domain.usecases.financeiro.categoria_despesa;

import domain.entities.financeiro.CategoriaDespesa;

public class CriarCategoriaDespesaUseCase {

    public CategoriaDespesa use(String titulo) {
        CategoriaDespesa categoria = new CategoriaDespesa(titulo);
        CategoriaDespesa.instances.add(categoria);
        return categoria;
    }
}
