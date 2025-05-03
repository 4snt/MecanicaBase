package domain.entities.financeiro;

/**
 * Representa os possíveis status de um agendamento.
 */
public enum StatusAgendamento {

    /**
     * Indica que o agendamento está confirmado e previsto para execução.
     */
    AGENDADO,
    /**
     * Indica que o agendamento foi cancelado e não será executado.
     */
    CANCELADO,
    /**
     * Indica que o serviço agendado foi concluído com sucesso.
     */
    CONCLUIDO
}
