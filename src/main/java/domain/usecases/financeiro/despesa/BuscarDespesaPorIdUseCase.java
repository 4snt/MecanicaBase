package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.Despesa;

/**
 * Caso de uso responsável por buscar uma despesa pelo seu identificador único.
 */
public class BuscarDespesaPorIdUseCase {

    /**
     * Busca uma despesa na lista de instâncias utilizando o ID fornecido.
     *
     * @param id UUID da despesa que deseja localizar.
     * @return A instância de {@link Despesa} correspondente ao ID ou
     * {@code null} se não encontrada.
     */
    public Despesa use(UUID id) {
        return Despesa.instances.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
