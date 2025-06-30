package mecanicabase.core;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Utilitário para percorrer listas com Iterator e foreach.
 */
public class GenericIterator {

    /**
     * Imprime os elementos da lista utilizando Iterator e foreach, mostrando os
     * resultados lado a lado para fins de comparação didática.
     *
     * @param lista Lista de elementos a serem exibidos
     * @param toStringFunc Função que transforma o item em String
     * @param explicacao Texto explicativo exibido ao final
     * @param <T> Tipo dos elementos
     */
    public static <T> void imprimirComIteratorEForeach(Iterable<T> lista, Function<T, String> toStringFunc, String explicacao) {
        System.out.println("🔁 Usando Iterator:");
        Iterator<T> it = lista.iterator();
        while (it.hasNext()) {
            T item = it.next();
            System.out.println("- " + toStringFunc.apply(item));
        }

        System.out.println("\n🔁 Usando foreach:");
        for (T item : lista) {
            System.out.println("- " + toStringFunc.apply(item));
        }

        System.out.println("\n📌 " + explicacao);
    }
}
