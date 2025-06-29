package mecanicabase.flyweight;

import mecanicabase.model.operacao.Peca;

/**
 * Representa um Flyweight não compartilhado (estado exclusivo) para peças.
 */
public class UnsharedConcreteFlyweight extends Peca {

    private Object estadoExclusivo;

    /**
     * Construtor da peça exclusiva.
     *
     * @param nome nome da peça
     * @param valor valor da peça
     * @param quantidade quantidade disponível
     * @param estadoExclusivo estado exclusivo da peça
     */
    public UnsharedConcreteFlyweight(
            String nome,
            float valor,
            int quantidade,
            Object estadoExclusivo) {
        super(nome, valor, quantidade);
        this.estadoExclusivo = estadoExclusivo;
    }

    /**
     * Retorna o estado exclusivo da peça.
     *
     * @return estado exclusivo
     */
    public Object getEstadoExclusivo() {
        return estadoExclusivo;
    }

    /**
     * Define o estado exclusivo da peça.
     *
     * @param estadoExclusivo novo estado exclusivo
     */
    public void setEstadoExclusivo(Object estadoExclusivo) {
        this.estadoExclusivo = estadoExclusivo;
    }

    /**
     * Executa uma operação utilizando o estado externo e o estado exclusivo.
     *
     * @param estadoExterno estado externo a ser utilizado
     */
    @Override
    public void operar(Object estadoExterno) {
        System.out.println(
                "Peça exclusiva com estado: "
                + estadoExterno + " e interno: "
                + estadoExclusivo);
    }
}
