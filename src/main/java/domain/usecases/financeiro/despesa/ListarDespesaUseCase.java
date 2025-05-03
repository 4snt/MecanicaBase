package domain.usecases.financeiro.despesa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.financeiro.Despesa;

/**
 * Caso de uso responsável por listar as despesas cadastradas, com possibilidade
 * de aplicar filtros por data, categoria e valor.
 */
public class ListarDespesaUseCase {

    /**
     * Retorna uma lista de despesas com base nos filtros fornecidos.
     *
     * @param dataInicio Data inicial do filtro (inclusive). Pode ser null.
     * @param dataFinal Data final do filtro (inclusive). Pode ser null.
     * @param categoriaId UUID da categoria para filtrar. Pode ser null.
     * @param valorMin Valor mínimo para filtrar. Pode ser null.
     * @param valorMax Valor máximo para filtrar. Pode ser null.
     * @return Lista de despesas que atendem aos critérios informados.
     */
    public List<Despesa> use(
            LocalDateTime dataInicio,
            LocalDateTime dataFinal,
            UUID categoriaId,
            Float valorMin,
            Float valorMax
    ) {
        return Despesa.instances.stream()
                .filter(d -> dataInicio == null || !d.getCriadoEm().isBefore(dataInicio))
                .filter(d -> dataFinal == null || !d.getCriadoEm().isAfter(dataFinal))
                .filter(d -> categoriaId == null || d.getCategoria().getId().equals(categoriaId))
                .filter(d -> valorMin == null || d.getValor() >= valorMin)
                .filter(d -> valorMax == null || d.getValor() <= valorMax)
                .collect(Collectors.toList());
    }
}
