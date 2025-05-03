package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um administrador do sistema. Administradores são responsáveis por
 * ações de gerenciamento mais amplas. Herda propriedades e comportamentos de
 * {@link Colaborador}.
 */
public class Administrador extends Colaborador {

    /**
     * Lista estática que simula uma base de dados em memória para armazenar os
     * administradores.
     */
    public static List<Administrador> instances = new ArrayList<>();

    /**
     * Construtor do administrador.
     *
     * @param nome Nome do administrador.
     * @param email Email do administrador.
     * @param senha Senha do administrador.
     * @param cpf CPF do administrador.
     * @param telefone Telefone do administrador.
     * @param endereco Endereço do administrador.
     */
    public Administrador(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        super(nome, email, senha, cpf, telefone, endereco);
    }

    /**
     * Inicializa o sistema com um administrador padrão.
     */
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

    /**
     * Busca um administrador pelo email.
     *
     * @param email Email do administrador a ser buscado.
     * @return Instância de {@link Administrador} correspondente ou {@code null}
     * se não encontrado.
     */
    public static Administrador buscarPorEmail(String email) {
        return Administrador.instances.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return String.format(
                "Administrador [ID=%s, Nome='%s', Email='%s', CPF='%s', Telefone='%s', Endereço='%s']",
                getId(),
                getNome(),
                getEmail(),
                getCpf() != null ? getCpf() : "N/A",
                getTelefone() != null ? getTelefone() : "N/A",
                getEndereco() != null ? getEndereco() : "N/A"
        );
    }
}
