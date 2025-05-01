package domain.usecases.usuarios.cliente;

import java.util.List;
import java.util.stream.Collectors;

import domain.entities.usuarios.Cliente;

public class ListaClienteUseCase {

    public List<Cliente> use(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return Cliente.instances;
        }

        String filtroLower = filtro.toLowerCase();

        return Cliente.instances.stream()
                .filter(cliente ->
                        cliente.getNome().toLowerCase().contains(filtroLower) ||
                        cliente.getEmail().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());
    }
}