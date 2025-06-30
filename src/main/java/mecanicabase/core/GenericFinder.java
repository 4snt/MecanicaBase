package mecanicabase.core;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Utilitário para busca genérica usando Iterator e Comparator.
 */
public class GenericFinder {

    /**
     * Realiza uma busca sequencial usando Iterator e Comparator.
     *
     * @param lista lista de elementos
     * @param chave objeto a ser encontrado
     * @param comparator critério de comparação
     * @param <T> tipo dos elementos
     * @return objeto encontrado ou null
     */
    public static <T> T find(List<T> lista, T chave, Comparator<T> comparator) {
        Iterator<T> it = lista.iterator();
        while (it.hasNext()) {
            T atual = it.next();
            if (comparator.compare(atual, chave) == 0) {
                return atual;
            }
        }
        return null;
    }
}
