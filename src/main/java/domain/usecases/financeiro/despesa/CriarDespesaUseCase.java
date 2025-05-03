package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;
import domain.entities.financeiro.Despesa;

public class CriarDespesaUseCase {

    public Despesa use(UUID categoriaId, String descricao, float valor) {
        if (CategoriaDespesa.buscarPorId(categoriaId) == null) {
            throw new RuntimeException("Categoria não encontrada");
        }

        if (valor < 0) {
            throw new RuntimeException("Valor inválido");
        }

        Despesa d = new Despesa(categoriaId, descricao, valor);
        Despesa.instances.add(d);
        return d;
    }
}
