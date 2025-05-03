package domain.usecases.usuarios.funcionario;

import java.util.UUID;

import domain.entities.usuarios.Funcionario;

public class BuscaPorIdFuncionarioUseCase {

    public Funcionario use(String id) {
        UUID uuid = UUID.fromString(id);

        for (Funcionario Funcionario : Funcionario.instances) {
            if (Funcionario.getId().equals(uuid)) {
                return Funcionario;
            }
        }

        return null;
    }
}