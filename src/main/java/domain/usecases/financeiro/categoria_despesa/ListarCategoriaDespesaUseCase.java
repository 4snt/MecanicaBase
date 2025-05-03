package domain.usecases.financeiro.categoria_despesa;

import java.util.List;

import domain.entities.financeiro.CategoriaDespesa;

/**
 * Caso de uso responsável por listar todas as categorias de despesa
 * cadastradas.
 */
public class ListarCategoriaDespesaUseCase {

    /**
     * Retorna todas as instâncias de categorias de despesa existentes no
     * sistema.
     *
     * @return Lista contendo todas as categorias de despesa.
     */
    public List<CategoriaDespesa> use() {
        return CategoriaDespesa.instances;
    }
}
