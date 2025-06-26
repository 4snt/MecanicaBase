package mecanicabase.core;

import java.util.List;

public class ListIterator<T> {

    private final List<T> lista;
    private int index = 0;

    public ListIterator(List<T> lista) {
        this.lista = lista;
    }

    public boolean hasNext() {
        return index < lista.size();
    }

    public T next() {
        if (!hasNext()) {
            throw new IllegalStateException("Não há mais elementos.");
        }
        return lista.get(index++);
    }

    public void reset() {
        index = 0;
    }
}
