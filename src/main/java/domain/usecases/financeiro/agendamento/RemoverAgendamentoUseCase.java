package domain.usecases.financeiro.agendamento;

import java.util.UUID;

import domain.entities.financeiro.Agendamento;

public class RemoverAgendamentoUseCase {

    public boolean use(UUID id) {
        return Agendamento.instances.removeIf(a -> a.getId().equals(id));
    }
}
