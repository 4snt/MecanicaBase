package domain.usecases.usuarios.funcionario;

import java.util.UUID;

import domain.entities.usuarios.Funcionario;

public class BuscaPorIdFuncionarioUseCase {

    public Funcionario use(String id) {
        return Funcionario.buscarPorId(UUID.fromString(id));
    }
}