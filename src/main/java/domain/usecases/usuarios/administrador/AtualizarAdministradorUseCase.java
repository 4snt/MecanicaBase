package domain.usecases.usuarios.administrador;

import java.util.UUID;

import domain.entities.usuarios.Administrador;
import domain.entities.usuarios.Funcionario;
import domain.entities.usuarios.TipoFuncionario;

public class AtualizarAdministradorUseCase {

    public Administrador use(String id, String nome, String email, String senha, String telefone, String endereco, TipoFuncionario funcao) {
        UUID uuid = UUID.fromString(id);

        for (Funcionario funcionario : Funcionario.instances) {
            if (funcionario.getId().equals(uuid) && funcionario instanceof Administrador adm) {
                if (nome != null) adm.setNome(nome);
                if (email != null) adm.setEmail(email);
                if (senha != null) adm.setSenha(senha);
                if (telefone != null) adm.setTelefone(telefone);
                if (endereco != null) adm.setEndereco(endereco);
                if (funcao != null) adm.setFuncao(funcao);
                return adm;
            }
        }

        return null;
    }
}
