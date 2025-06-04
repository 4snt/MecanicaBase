package com.mycompany.mecanicabase.service.usuarios.administrador;

import java.util.UUID;
import com.mycompany.mecanicabase.model.usuarios.Administrador;

/**
 * Caso de uso responsável por buscar um administrador pelo seu ID.
 */
public class BuscaPorIdAdministradorUseCase {

    /**
     * Busca um administrador pelo seu UUID.
     *
     * @param id O ID do administrador a ser buscado.
     * @return O administrador encontrado ou null se não encontrado.
     */
    public Administrador use(String id) {
        // Converte o ID de String para UUID
        UUID uuid = UUID.fromString(id);

        // Percorre todos os administradores registrados
        for (Administrador administrador : Administrador.instances) {
            // Verifica se o administrador atual possui o ID desejado
            if (administrador.getId().equals(uuid)) {
                // Retorna o administrador encontrado
                return administrador;
            }
        }

        // Retorna null caso o administrador com o ID fornecido não seja encontrado
        return null;
    }
}
