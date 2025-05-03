package domain.usecases.usuarios.administrador;

import domain.entities.usuarios.Administrador;

public class CriarAdministradorUseCase {
  public Administrador use(String nome,  String email, String senha, String cpf, String telefone, String endereco) {
      Administrador Administrador = new Administrador(nome, email, senha, cpf, telefone, endereco);
      
      Administrador.instances.add(Administrador);

      return Administrador;
  }
}