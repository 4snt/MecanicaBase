package mecanicabase.core;

import java.util.List;
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

    public T buscarPorId(String id) {
        UUID uuid = UUID.fromString(id);
        return getInstancias().stream()
                .filter(entidade -> getId(entidade).equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public List<T> listarTodos() {
        return getInstancias();
    }

    public boolean removerPorId(String id) {
        UUID uuid = UUID.fromString(id);
        return getInstancias().removeIf(entidade -> getId(entidade).equals(uuid));
    }

    public T criar(boolean validar, Object... params) {
        if (validar && !validarCriacao(params)) {
            throw new IllegalArgumentException("Validação falhou ao criar entidade.");
        }

        T entidade = criarInstancia(params);
        getInstancias().add(entidade);
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
        return entidade;
    }
}
