package mecanicabase.service.financeiro.despesa;

import java.util.UUID;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;

/**
 * Caso de uso responsável por criar uma nova despesa.
 */
public class CriarDespesaUseCase {

    /**
     * Cria uma nova despesa associada a uma categoria existente.
     *
     * @param categoriaId UUID da categoria de despesa associada.
     * @param descricao Descrição da despesa.
     * @param valor Valor da despesa. Deve ser maior ou igual a zero.
     * @return A instância criada de {@link Despesa}.
     * @throws RuntimeException se a categoria não for encontrada ou se o valor
     * for negativo.
     */
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
