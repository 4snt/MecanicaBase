package core;

import java.util.*;

public class Entity<T> {
    private UUID id;
    private Date criadoEm;
    private Date atualizadoEm;

    public Entity() {
        this.id = UUID.randomUUID();
        this.criadoEm = new Date();
        this.atualizadoEm = new Date();
    }

    protected void touch() {
        this.atualizadoEm = new Date();
    }

    public UUID getId() {
        return id;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public Date getAtualizadoEm() {
        return atualizadoEm;
    }
}