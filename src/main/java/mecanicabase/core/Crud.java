package mecanicabase.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public T buscarPorId(String id) {
        UUID uuid = UUID.fromString(id);
        return buscarPorId(uuid);
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

        T entidade = criarInstancia(params);
        getInstancias().add(entidade);
        indexPorId.put(getId(entidade), entidade);
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
}
