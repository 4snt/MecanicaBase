package domain.usecases.financeiro.agendamento;

import java.util.UUID;

import domain.entities.financeiro.Agendamento;

public class BuscarPorIdAgendamentoUseCase {

    public Agendamento use(UUID id) {
        return Agendamento.buscarPorId(id);
    }
}
