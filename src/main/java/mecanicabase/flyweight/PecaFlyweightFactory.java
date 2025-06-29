package mecanicabase.flyweight;

import java.util.HashMap;
import java.util.Map;
import mecanicabase.model.operacao.Peca;

/**
 * Fábrica Flyweight para instanciar e gerenciar objetos Peca compartilhados.
 */
public class PecaFlyweightFactory {

    private final Map<String, Peca> cache = new HashMap<>();

    /**
     * Retorna uma instância compartilhada de Peca com base no nome e preço.
     *
     * @param nome nome da peça
     * @param preco preço da peça
     * @return instância compartilhada de Peca
     */
    public Peca getPeca(String nome, float preco) {
        String chave = nome.toLowerCase() + "|" + preco;
        return cache.computeIfAbsent(chave, k -> new Peca(nome, preco));
    }

    /**
     * Retorna o total de objetos Peca compartilhados no cache.
     *
     * @return quantidade de objetos compartilhados
     */
    public int getTotalCompartilhados() {
        return cache.size();
    }

    /**
     * Limpa o cache de objetos compartilhados.
     */
    public void limpar() {
        cache.clear();
    }
}
