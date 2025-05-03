package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.Despesa;

public class BuscarDespesaPorIdUseCase {

    public Despesa use(UUID id) {
        return Despesa.instances.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
