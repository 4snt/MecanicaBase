package domain.usecases.usuarios.funcionario;

import java.util.UUID;

import domain.entities.usuarios.Funcionario;
import domain.entities.usuarios.TipoFuncionario;

public class AtualizaFuncionarioUseCase {

    public Funcionario use(String id, String nome, String email, String senha, String telefone, String endereco, TipoFuncionario funcao) {
        UUID uuid = UUID.fromString(id);

        for (Funcionario funcionario : Funcionario.instances) {
            if (funcionario.getId().equals(uuid)) {
                if (nome != null) funcionario.setNome(nome);
                if (email != null) funcionario.setEmail(email);
                if (senha != null) funcionario.setSenha(senha);
                if (telefone != null) funcionario.setTelefone(telefone);
                if (endereco != null) funcionario.setEndereco(endereco);
                if (funcao != null) funcionario.setFuncao(funcao);
                return funcionario;
            }
        }

        return null;
    }
}
