package mecanicabase.service.financeiro.categoria_despesa;

import mecanicabase.model.financeiro.CategoriaDespesa;

/**
 * Caso de uso responsável por criar uma nova categoria de despesa.
 */
public class CriarCategoriaDespesaUseCase {

    /**
     * Cria uma nova instância de CategoriaDespesa com o título informado e a
     * adiciona à lista de instâncias existentes.
     *
     * @param titulo O título da nova categoria de despesa.
     * @return A instância criada de CategoriaDespesa.
     */
    public CategoriaDespesa use(String titulo) {
        CategoriaDespesa categoria = new CategoriaDespesa(titulo);
        CategoriaDespesa.instances.add(categoria);
        return categoria;
    }
}
