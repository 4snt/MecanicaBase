package mecanicabase.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Crud generificado com suporte opcional a Flyweight. Para usar, chame
 * setFlyweightFactory com uma função (chave, params) -> T
 */
public abstract class Crud<T> {

    protected abstract List<T> getInstancias();

    protected abstract UUID getId(T entidade);

    protected abstract T criarInstancia(Object... params);

    protected abstract void atualizarInstancia(T entidade, Object... params);

    protected boolean validarCriacao(Object... params) {
        return true;
    }

    protected boolean validarAtualizacao(T entidade, Object... params) {
        return true;
    }

    private final Map<UUID, T> indexPorId = new HashMap<>();

    // Flyweight opcional
    private boolean usarFlyweight = false;
    private BiFunction<String, Object[], T> flyweightFactory = null;
    private final Map<String, T> flyweightCache = new HashMap<>();

    public void setUsarFlyweight(boolean usarFlyweight, BiFunction<String, Object[], T> factory) {
        this.usarFlyweight = usarFlyweight;
        this.flyweightFactory = factory;
    }

    public int getTotalCompartilhadosFlyweight() {
        return flyweightCache.size();
    }

    public void limparFlyweight() {
        flyweightCache.clear();
    }

    public T buscarPorId(String id) {
        return buscarPorId(UUID.fromString(id));
    }

    public T buscarPorId(UUID id) {
        return indexPorId.get(id);
    }

    public List<T> listarTodos() {
        return getInstancias();
    }

    public boolean removerPorId(String id) {
        UUID uuid = UUID.fromString(id);
        T entidade = buscarPorId(uuid);
        if (entidade != null) {
            getInstancias().remove(entidade);
            indexPorId.remove(uuid);
            return true;
        }
        return false;
    }

    public T criar(boolean validar, Object... params) {
        if (validar && !validarCriacao(params)) {
            throw new IllegalArgumentException("Validação falhou ao criar entidade.");
        }

        T entidade;

        if (usarFlyweight && flyweightFactory != null) {
            String chave = gerarChaveFlyweight(params);
            entidade = flyweightCache.computeIfAbsent(chave, k -> flyweightFactory.apply(k, params));
        } else {
            entidade = criarInstancia(params);
        }

        if (!getInstancias().contains(entidade)) {
            getInstancias().add(entidade);
            indexPorId.put(getId(entidade), entidade);
        }
        return entidade;
    }

    public T atualizar(String id, boolean validar, Object... params) {
        T entidade = buscarPorId(id);
        if (entidade == null) {
            return null;
        }

        if (validar && !validarAtualizacao(entidade, params)) {
            throw new IllegalArgumentException("Validação falhou ao atualizar entidade.");
        }

        atualizarInstancia(entidade, params);
        indexPorId.put(getId(entidade), entidade);
        return entidade;
    }

    protected String gerarChaveFlyweight(Object... params) {
        return Arrays.toString(params); // pode ser sobrescrito
    }
}
