package com.mycompany.mecanicabase.service.usuarios.administrador;

import java.util.UUID;
import com.mycompany.mecanicabase.model.usuarios.Administrador;

/**
 * Caso de uso responsável por atualizar os dados de um administrador existente.
 */
public class AtualizarAdministradorUseCase {

    /**
     * Atualiza os dados de um administrador identificado por seu UUID.
     *
     * @param id O ID do administrador a ser atualizado.
     * @param nome Novo nome (ou null para manter o atual).
     * @param email Novo email (ou null para manter o atual).
     * @param senha Nova senha (ou null para manter a atual).
     * @param telefone Novo telefone (ou null para manter o atual).
     * @param endereco Novo endereço (ou null para manter o atual).
     * @return O administrador atualizado, ou null se não encontrado.
     */
    public Administrador use(String id, String nome, String email, String senha, String telefone, String endereco) {
        UUID uuid = UUID.fromString(id);

        for (Administrador administrador : Administrador.instances) {
            if (administrador.getId().equals(uuid)) {
                if (nome != null) {
                    administrador.setNome(nome);
                }
                if (email != null) {
                    administrador.setEmail(email);
                }
                if (senha != null) {
                    administrador.setSenha(senha);
                }
                if (telefone != null) {
                    administrador.setTelefone(telefone);
                }
                if (endereco != null) {
                    administrador.setEndereco(endereco);
                }
                return administrador;
            }
        }

        return null;
    }
}
