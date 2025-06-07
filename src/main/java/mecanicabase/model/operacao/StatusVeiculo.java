package mecanicabase.model.operacao;

/**
 * Enumeração que representa os possíveis status de um veículo no sistema da
 * oficina.
 */
public enum StatusVeiculo {
    /**
     * O veículo foi recebido pela oficina, mas o serviço ainda não foi
     * concluído.
     */
    RECEBIDO,
    /**
     * O serviço no veículo foi finalizado e ele está pronto para ser entregue.
     */
    PRONTO,
    /**
     * O veículo já foi entregue ao cliente.
     */
    ENTREGUE
}
