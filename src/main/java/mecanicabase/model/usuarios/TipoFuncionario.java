package mecanicabase.model.usuarios;

/**
 * Enumeração que representa os cargos que um funcionário pode exercer na
 * oficina.
 */
public enum TipoFuncionario {
    /**
     * Responsável por tarefas simples como trocas de óleo e pneus.
     */
    MECANICO_BASICO,
    /**
     * Executa serviços técnicos mais avançados como correia dentada e freios.
     */
    MECANICO_INTERMEDIARIO,
    /**
     * Especialista em diagnósticos e serviços complexos como motor e injeção
     * eletrônica.
     */
    MECANICO_ESPECIALISTA,
    /**
     * Responsável pelo atendimento ao cliente e vendas de serviços.
     */
    ATENDENTE,
    /**
     * Gerencia a equipe, controla ordens e supervisiona os serviços.
     */
    SUPERVISOR
}
