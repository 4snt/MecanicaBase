// Administrador.java
package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends colaborador {
    public static List<Administrador> instances = new ArrayList<>();

    public Administrador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, senha, cpf, telefone, endereco);
    }

    public static void init() {
        Administrador.instances.add(new Administrador(
            "Admin",
            "Admin",
            "admin",
            null,
            null,
            null
        ));
    }

    public static Administrador buscarPorEmail(String email) {
        return Administrador.instances.stream()
            .filter(s -> s.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }
}

