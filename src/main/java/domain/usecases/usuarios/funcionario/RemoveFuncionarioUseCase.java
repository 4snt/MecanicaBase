package domain.usecases.usuarios.funcionario;

import java.util.UUID;

import domain.entities.usuarios.Funcionario;

public class RemoveFuncionarioUseCase {

    public boolean use(String id) {
        UUID uuid = UUID.fromString(id);

        return Funcionario.instances.removeIf(funcionario -> funcionario.getId().equals(uuid));
    }
}