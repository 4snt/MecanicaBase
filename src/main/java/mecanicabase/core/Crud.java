package mecanicabase.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Classe base para operações CRUD (Create, Read, Update, Delete) genéricas.
 * <p>
 * Para garantir o funcionamento correto dos métodos de atualização e remoção, é
 * necessário que todas as entidades já existentes na lista retornada por
 * {@link #getInstancias()} estejam indexadas no mapa interno
 * {@code indexPorId}. Recomenda-se que subclasses chamem o método
 * {@link #reindexar()} no construtor.
 *
 * Exemplo de uso no construtor da subclasse:
 * <pre>
 *   public MinhaEntidadeCrud() {
 *       reindexar();
 *   }
 * </pre>
 *
 * @param <T> Tipo da entidade
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

    // Novo método para reindexar entidades já existentes
    protected void reindexar() {
        indexPorId.clear();
        for (T entidade : getInstancias()) {
            indexPorId.put(getId(entidade), entidade);
        }
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
            System.out.println("⛔ [CRUD] Validação falhou ao criar entidade: " + getClass().getSimpleName());
            for (int i = 0; i < params.length; i++) {
                System.out.println("🔍 Param[" + i + "]: " + params[i]);
            }
            throw new IllegalArgumentException("Validação falhou ao criar entidade.");
        }

        T entidade = criarInstancia(params);

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
            return null;
        }
        atualizarInstancia(entidade, params);
        // Se usar Map, reindexe se necessário:
        indexPorId.put(getId(entidade), entidade);
        return entidade;
    }
}
