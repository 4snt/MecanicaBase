package domain.entities.financeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.Entity;

/**
 * Representa uma despesa registrada no sistema financeiro.
 */
public class CategoriaDespesa extends Entity {

    public static List<CategoriaDespesa> instances = new ArrayList<>();

    private final String titulo;

    public CategoriaDespesa(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public static CategoriaDespesa buscarPorId(UUID id) {
        return CategoriaDespesa.instances.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format("CategoriaDespesa [ID=%s, Título='%s']", getId(), titulo);
    }
}
