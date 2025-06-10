package mecanicabase.flyweight;

import java.util.HashMap;
import java.util.Map;
import mecanicabase.model.operacao.Peca;

public class PecaFlyweightFactory {

    private final Map<String, Peca> cache = new HashMap<>();

    public Peca getPeca(String nome, float preco) {
        String chave = nome.toLowerCase() + "|" + preco;
        return cache.computeIfAbsent(chave, k -> new Peca(nome, preco));
    }

    public int getTotalCompartilhados() {
        return cache.size();
    }

    public void limpar() {
        cache.clear();
    }
}
