// Administrador.java
package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Funcionario {
    private static List<Administrador> instances = new ArrayList<>();

    public Administrador(String nome, String login, String senha) {
        super(nome, "Administrador", login, senha);
    }

    // Métodos específicos de gerente
}
