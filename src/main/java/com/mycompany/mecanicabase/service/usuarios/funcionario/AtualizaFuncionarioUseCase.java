package com.mycompany.mecanicabase.service.usuarios.funcionario;

import java.util.UUID;
import com.mycompany.mecanicabase.model.usuarios.Funcionario;
import com.mycompany.mecanicabase.model.usuarios.TipoFuncionario;

/**
 * Caso de uso responsável por atualizar os dados de um funcionário existente.
 */
public class AtualizaFuncionarioUseCase {

    /**
     * Atualiza os dados de um funcionário identificado por seu UUID.
     *
     * @param id O ID do funcionário a ser atualizado.
     * @param nome Novo nome (ou null para manter o atual).
     * @param email Novo email (ou null para manter o atual).
     * @param senha Nova senha (ou null para manter a atual).
     * @param telefone Novo telefone (ou null para manter o atual).
     * @param endereco Novo endereço (ou null para manter o atual).
     * @param funcao Novo cargo ou função do funcionário (ou null para manter o
     * atual).
     * @return O funcionário atualizado, ou null se não encontrado.
     */
    public Funcionario use(String id, String nome, String email, String senha, String telefone, String endereco, TipoFuncionario funcao) {
        // Converte o ID fornecido de String para UUID
        UUID uuid = UUID.fromString(id);

        // Busca o funcionário pelo ID e atualiza seus dados
        for (Funcionario funcionario : Funcionario.instances) {
            if (funcionario.getId().equals(uuid)) {
                // Atualiza os dados, se fornecido
                if (nome != null) {
                    funcionario.setNome(nome);
                }
                if (email != null) {
                    funcionario.setEmail(email);
                }
                if (senha != null) {
                    funcionario.setSenha(senha);
                }
                if (telefone != null) {
                    funcionario.setTelefone(telefone);
                }
                if (endereco != null) {
                    funcionario.setEndereco(endereco);
                }
                if (funcao != null) {
                    funcionario.setFuncao(funcao);
                }

                // Retorna o funcionário atualizado
                return funcionario;
            }
        }

        // Retorna null se o funcionário não for encontrado
        return null;
    }
}
