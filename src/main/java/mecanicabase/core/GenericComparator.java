package mecanicabase.core;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Comparator genérico que permite comparar objetos de qualquer tipo com base em
 * uma chave extraída via Function.
 */
public class GenericComparator<T, R> implements Comparator<T> {

    private final Function<T, R> keyExtractor;
    private final Comparator<R> comparator;

    public GenericComparator(Function<T, R> keyExtractor, Comparator<R> comparator) {
        this.keyExtractor = keyExtractor;
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        return comparator.compare(keyExtractor.apply(o1), keyExtractor.apply(o2));
    }
}
