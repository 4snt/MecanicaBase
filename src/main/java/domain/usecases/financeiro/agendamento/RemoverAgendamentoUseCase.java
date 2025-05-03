package domain.usecases.financeiro.agendamento;

import java.util.UUID;

import domain.entities.financeiro.Agendamento;

/**
 * Caso de uso responsável por remover um agendamento a partir de seu ID.
 */
public class RemoverAgendamentoUseCase {

    /**
     * Remove um agendamento com o ID especificado.
     *
     * @param id O UUID do agendamento a ser removido.
     * @return true se o agendamento foi encontrado e removido; false caso
     * contrário.
     */
    public boolean use(UUID id) {
        return Agendamento.instances.removeIf(a -> a.getId().equals(id));
    }
}
