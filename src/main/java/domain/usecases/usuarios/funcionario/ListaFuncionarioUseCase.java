package domain.usecases.usuarios.funcionario;

import java.util.List;
import java.util.stream.Collectors;

import domain.entities.usuarios.Funcionario;

public class ListaFuncionarioUseCase {

    public List<Funcionario> use(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return Funcionario.instances;
        }

        String filtroLower = filtro.toLowerCase();

        return Funcionario.instances.stream()
                .filter(funcionario ->
                        funcionario.getNome().toLowerCase().contains(filtroLower) ||
                        funcionario.getEmail().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());
    }
}