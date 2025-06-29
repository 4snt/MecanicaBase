package mecanicabase.flyweight;

/**
 * Interface Flyweight para operações com estado externo.
 */
public interface Flyweight {

    /**
     * Executa uma operação utilizando o estado externo.
     *
     * @param estadoExterno estado externo a ser utilizado
     */
    void operar(Object estadoExterno);
}
