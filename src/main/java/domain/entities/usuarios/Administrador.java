// Administrador.java
package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Funcionario {
    public static final List<Administrador> instances = new ArrayList<>();

    public Administrador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, TipoFuncionario.ADMINISTRADOR, email, senha, cpf, telefone, endereco);
    }

    // Métodos específicos de gerente
}

