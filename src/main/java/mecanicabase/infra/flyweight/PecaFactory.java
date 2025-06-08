package mecanicabase.infra.flyweight;

import java.util.HashMap;
import java.util.Map;
import mecanicabase.model.operacao.Peca;

public class PecaFactory {

    private static final Map<String, Peca> cache = new HashMap<>();

    public static Peca getPeca(String nome, float valor) {
        String chave = nome.toLowerCase() + "|" + valor;
        return cache.computeIfAbsent(chave, k -> new Peca(nome, valor, 0));
    }

    public static int getTotalInstanciasCompartilhadas() {
        return cache.size();
    }

    public static void limparCache() {
        cache.clear();
    }
}
