package com.mycompany.mecanicabase.service.usuarios.administrador;

import com.mycompany.mecanicabase.model.usuarios.Administrador;

/**
 * Caso de uso responsável por criar um novo administrador.
 */
public class CriarAdministradorUseCase {

    /**
     * Cria um novo administrador e o adiciona à lista de administradores.
     *
     * @param nome O nome do administrador.
     * @param email O email do administrador.
     * @param senha A senha do administrador.
     * @param cpf O CPF do administrador.
     * @param telefone O telefone do administrador.
     * @param endereco O endereço do administrador.
     * @return O administrador recém-criado.
     */
    public Administrador use(String nome, String email, String senha, String cpf, String telefone, String endereco) {
        // Cria um novo objeto Administrador com os dados fornecidos
        Administrador administrador = new Administrador(nome, email, senha, cpf, telefone, endereco);

        // Adiciona o novo administrador à lista de administradores
        Administrador.instances.add(administrador);

        // Retorna o administrador recém-criado
        return administrador;
    }
}
