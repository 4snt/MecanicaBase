package com.mycompany.mecanicabase.service.usuarios.funcionario;

import java.util.UUID;
import com.mycompany.mecanicabase.model.usuarios.Funcionario;

/**
 * Caso de uso responsável por buscar um funcionário pelo seu ID.
 */
public class BuscaPorIdFuncionarioUseCase {

    /**
     * Busca um funcionário a partir de seu ID único (UUID).
     *
     * @param id O ID do funcionário a ser buscado.
     * @return O funcionário encontrado, ou null se não houver nenhum
     * funcionário com o ID fornecido.
     */
    public Funcionario use(String id) {
        // Converte o ID de String para UUID e busca o funcionário correspondente
        return Funcionario.buscarPorId(UUID.fromString(id));
    }
}
