package domain.usecases.usuarios.cliente;

import domain.entities.usuarios.Cliente;
import infra.crypto.JasyptCrypto;

// instancia classe cliente para seus usecase
public class CriarClienteUseCase {
  public Cliente use(
    String nome,
    String endereco,
    String telefone,
    String email,
    String cpf
  ) {

    String cpfAnonimazado = JasyptCrypto.encrypt(cpf);

    Cliente cliente = new Cliente(nome, endereco, telefone, email, cpfAnonimazado);
    Cliente.instances.add(cliente);

    return cliente;
  } 
}