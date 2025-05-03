package domain.usecases.financeiro.categoria_despesa;

import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;

public class AtualizarCategoriaDespesaUseCase {

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
