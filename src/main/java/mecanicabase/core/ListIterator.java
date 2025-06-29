package mecanicabase.core;

import java.util.List;

/**
 * Iterador simples para listas genéricas. Permite percorrer uma lista
 * sequencialmente e reiniciar a iteração.
 *
 * @param <T> tipo dos elementos da lista
 */
public class ListIterator<T> {

    private final List<T> lista;
    private int index = 0;

    /**
     * Cria um novo iterador para a lista informada.
     *
     * @param lista lista a ser iterada
     */
    public ListIterator(List<T> lista) {
        this.lista = lista;
    }

    /**
     * Verifica se há próximo elemento na lista.
     *
     * @return true se houver próximo elemento, false caso contrário
     */
    public boolean hasNext() {
        return index < lista.size();
    }

    /**
     * Retorna o próximo elemento da lista.
     *
     * @return próximo elemento
     * @throws IllegalStateException se não houver mais elementos
     */
    public T next() {
        if (!hasNext()) {
            throw new IllegalStateException("Não há mais elementos.");
        }
        return lista.get(index++);
    }

    /**
     * Reinicia o iterador para o início da lista.
     */
    public void reset() {
        index = 0;
    }
}
