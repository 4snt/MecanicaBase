package domain.usecases.usuarios.funcionario;

import domain.entities.usuarios.Funcionario;
import domain.entities.usuarios.TipoFuncionario;

public class CriarFuncionarioUseCase {
  public Funcionario use(String nome, TipoFuncionario funcao, String email, String senha, String cpf, String telefone, String endereco) {
      Funcionario funcionario = new Funcionario(nome, funcao, email, senha, cpf, telefone, endereco);
      
      Funcionario.instances.add(funcionario);

      return funcionario;
  }
}