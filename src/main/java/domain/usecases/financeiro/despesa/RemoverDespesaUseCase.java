package domain.usecases.financeiro.despesa;

import java.util.UUID;

import domain.entities.financeiro.Despesa;

public class RemoverDespesaUseCase {

    public boolean use(UUID id) {
        return Despesa.instances.removeIf(d -> d.getId().equals(id));
    }
}
