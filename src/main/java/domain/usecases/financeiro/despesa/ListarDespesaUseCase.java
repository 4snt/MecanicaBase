package domain.usecases.financeiro.despesa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.financeiro.Despesa;

public class ListarDespesaUseCase {

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
