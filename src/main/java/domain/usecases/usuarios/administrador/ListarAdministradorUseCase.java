package domain.usecases.usuarios.administrador;

import java.util.List;
import java.util.stream.Collectors;

import domain.entities.usuarios.Administrador;

public class ListarAdministradorUseCase {
    public List<Administrador> use(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return Administrador.instances;
        }

        String filtroLower = filtro.toLowerCase();

        return Administrador.instances.stream()
                .filter(Administrador ->
                        Administrador.getNome().toLowerCase().contains(filtroLower) ||
                        Administrador.getEmail().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());
    }
}