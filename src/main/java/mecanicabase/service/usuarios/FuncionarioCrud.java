package mecanicabase.service.usuarios;

import java.util.List;
import java.util.UUID;
import mecanicabase.infra.crypto.JasyptCrypto;
import mecanicabase.model.usuarios.Funcionario;
import mecanicabase.model.usuarios.TipoFuncionario;

public class FuncionarioCrud extends UsuarioCrud<Funcionario> {

    @Override
    protected List<Funcionario> getInstancias() {
        return Funcionario.instances;
    }

    @Override
    protected UUID getId(Funcionario entidade) {
        return entidade.getId();
    }

    @Override
    protected Funcionario criarInstancia(Object... params) {
        String nome = (String) params[0];
        TipoFuncionario funcao = (TipoFuncionario) params[1];
        String email = (String) params[2];
        String senha = (String) params[3];
        String cpfOriginal = (String) params[4];
        String telefone = (String) params[5];
        String endereco = (String) params[6];
        float salario = (float) params[7];

        String cpf = JasyptCrypto.encrypt(cpfOriginal);

        return new Funcionario(nome, funcao, email, senha, cpf, telefone, endereco, salario);
    }

    @Override
    protected void atualizarInstancia(Funcionario funcionario, Object... params) {
        String nome = (String) params[0];
        String senha = (String) params[1];
        String telefone = (String) params[2];
        String endereco = (String) params[3];
        TipoFuncionario funcao = (TipoFuncionario) params[4];
        Float salario = (Float) params[5];

        if (nome != null && !nome.isBlank()) {
            funcionario.setNome(nome);
        }
        if (senha != null && !senha.isBlank()) {
            funcionario.setSenha(senha);
        }
        if (telefone != null && !telefone.isBlank()) {
            funcionario.setTelefone(telefone);
        }
        if (endereco != null && !endereco.isBlank()) {
            funcionario.setEndereco(endereco);
        }
        if (funcao != null) {
            funcionario.setFuncao(funcao);
        }
        if (salario != null) {
            funcionario.setSalario(salario);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String email = (String) params[2];
        String cpfOriginal = (String) params[4];

        boolean emailExiste = Funcionario.instances.stream()
                .anyMatch(f -> f.getEmail().equalsIgnoreCase(email));

        boolean cpfExiste = Funcionario.instances.stream()
                .anyMatch(f -> JasyptCrypto.compareTo(cpfOriginal, f.getCpf()));

        if (emailExiste) {
            throw new IllegalArgumentException("Já existe um funcionário com este email.");
        }
        if (cpfExiste) {
            throw new IllegalArgumentException("Já existe um funcionário com este CPF.");
        }

        return true;
    }

    @Override
    protected boolean validarAtualizacao(Funcionario funcionario, Object... params) {
        if (funcionario == null) {
            return false;
        }

        String novoEmail = (String) params[0];
        if (novoEmail != null && !novoEmail.equalsIgnoreCase(funcionario.getEmail())) {
            boolean emailExiste = Funcionario.instances.stream()
                    .anyMatch(f -> f.getEmail().equalsIgnoreCase(novoEmail));
            if (emailExiste) {
                throw new IllegalArgumentException("Outro funcionário já usa este email.");
            }
        }

        return true;
    }
}
