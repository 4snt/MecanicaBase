package core;

import java.time.LocalDateTime;
import java.util.UUID;

public class Entity<T> {

    private final UUID id;
    private final LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Entity() {
        this.id = UUID.randomUUID();
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    protected void touch() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
