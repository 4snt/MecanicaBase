package mecanicabase.model.operacao;

/**
 * Classe utilitária que mantém o controle global da quantidade total de
 * veículos registrados no sistema. Essa contagem é incrementada ou reduzida
 * conforme veículos são adicionados ou removidos.
 */
public class Sistema {

    /**
     * Contador estático que representa o total de veículos registrados.
     */
    private static int totalVeiculos;

    /**
     * Retorna o número atual de veículos registrados no sistema.
     *
     * @return Quantidade total de veículos
     */
    public static int getTotalVeiculos() {
        return Sistema.totalVeiculos;
    }

    /**
     * Incrementa o total de veículos em 1.
     *
     * @return Novo total de veículos após o incremento
     */
    public static int incrementarTotalVeiculos() {
        Sistema.totalVeiculos += 1;
        return Sistema.totalVeiculos;
    }

    /**
     * Reduz o total de veículos em 1.
     *
     * @return Novo total de veículos após a redução
     */
    public static int reduzirTotalVeiculos() {
        Sistema.totalVeiculos -= 1;
        return Sistema.totalVeiculos;
    }

    /**
     * Insere um novo valor para total de veículos.
     *
     * @param quantidade Novo total de veículos a ser definido
     * @return Novo total de veículos
     */
    public static int setTotalVeiculos(int quantidade) {
        Sistema.totalVeiculos = quantidade;
        return Sistema.totalVeiculos;
    }

    @Override
    public String toString() {
        return "Total de veículos registrados: " + totalVeiculos;
    }
}
