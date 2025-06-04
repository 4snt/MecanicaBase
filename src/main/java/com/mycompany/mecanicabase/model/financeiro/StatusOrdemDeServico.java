package com.mycompany.mecanicabase.model.financeiro;

/**
 * Representa os possíveis status de uma ordem de serviço.
 */
public enum StatusOrdemDeServico {

    /**
     * Ordem de serviço foi aberta e está em andamento.
     */
    ABERTO,
    /**
     * Ordem de serviço foi cancelada antes da conclusão.
     */
    CANCELADO,
    /**
     * Ordem de serviço foi finalizada com sucesso.
     */
    CONCLUIDO
}
