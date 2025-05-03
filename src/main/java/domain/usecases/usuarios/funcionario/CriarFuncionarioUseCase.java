package domain.usecases.usuarios.funcionario;

import domain.entities.usuarios.Funcionario;
import domain.entities.usuarios.TipoFuncionario;

/**
 * Caso de uso responsável por criar um novo funcionário no sistema.
 */
public class CriarFuncionarioUseCase {

    /**
     * Cria um novo funcionário com os dados fornecidos e o adiciona à lista de
     * funcionários.
     *
     * @param nome Nome do funcionário.
     * @param funcao Tipo de função do funcionário (por exemplo, mecânico,
     * vendedor, etc.).
     * @param email E-mail do funcionário.
     * @param senha Senha do funcionário.
     * @param cpf CPF do funcionário.
     * @param telefone Telefone do funcionário.
     * @param endereco Endereço do funcionário.
     * @param salario Salário do funcionário.
     * @return O funcionário criado e adicionado à lista.
     */
    public Funcionario use(String nome, TipoFuncionario funcao, String email, String senha, String cpf, String telefone, String endereco, Float salario) {
        // Cria uma instância do funcionário com os dados fornecidos
        Funcionario funcionario = new Funcionario(nome, funcao, email, senha, cpf, telefone, endereco, salario);

        // Adiciona o funcionário à lista de funcionários
        Funcionario.instances.add(funcionario);

        // Retorna o funcionário recém-criado
        return funcionario;
    }
}
