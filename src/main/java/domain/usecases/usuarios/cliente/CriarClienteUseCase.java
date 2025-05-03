package domain.usecases.usuarios.cliente;

import domain.entities.usuarios.Cliente;

// instancia classe cliente para seus usecase
public class CriarClienteUseCase {
  public Cliente use(
    String nome,
    String endereco,
    String telefone,
    String email,
    String cpf
  ) {
    Cliente cliente = new Cliente(nome, endereco, telefone, email, cpf);
    Cliente.instances.add(cliente);

    return cliente;
  } 
}