package mecanicabase.core;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe base genérica para entidades do sistema.
 *
 * Responsável por fornecer um identificador único (UUID) e metadados de criação
 * e atualização. Todas as entidades do sistema devem estender esta classe.
 *
 * @param <T> Tipo genérico que pode ser usado por subclasses para
 * especialização, se necessário.
 */
public class Entity<T> {

    /**
     * Identificador único da entidade.
     */
    private final UUID id;

    /**
     * Data e hora em que a entidade foi criada.
     */
    private final LocalDateTime criadoEm;

    /**
     * Data e hora da última atualização da entidade.
     */
    private LocalDateTime atualizadoEm;

    /**
     * Construtor padrão que gera um UUID aleatório e define os campos de data
     * como o momento atual.
     */
    public Entity() {
        this.id = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Atualiza o campo {@code atualizadoEm} com o horário atual. Deve ser
     * chamado sempre que a entidade for modificada.
     */
    protected void touch() {
        this.atualizadoEm = LocalDateTime.now();
    }

    /**
     * Retorna o identificador único da entidade.
     *
     * @return UUID da entidade
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retorna a data/hora de criação da entidade.
     *
     * @return Data de criação
     */
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    /**
     * Retorna a data/hora da última atualização da entidade.
     *
     * @return Data da última modificação
     */
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
