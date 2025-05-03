package domain.usecases.financeiro.categoria_despesa;

import java.util.UUID;

import domain.entities.financeiro.CategoriaDespesa;

/**
 * Caso de uso responsável por buscar uma categoria de despesa pelo seu
 * identificador único.
 */
public class BuscarPorIdCategoriaDespesaUseCase {

    /**
     * Busca uma instância de CategoriaDespesa com base no ID fornecido.
     *
     * @param id O UUID da categoria de despesa a ser buscada.
     * @return A categoria de despesa correspondente ao ID, ou null se não for
     * encontrada.
     */
    public CategoriaDespesa use(UUID id) {
        return CategoriaDespesa.buscarPorId(id);
    }
}
