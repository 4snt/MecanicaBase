package domain.entities.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cliente extends Pessoa {
    public static List<Cliente> instances = new ArrayList<>();

    private final List<UUID> veiculos;

    public Cliente(String nome, String telefone, String endereco, String email, String cpf) {
        super(nome, email, cpf, telefone, endereco);
        this.veiculos = new ArrayList<>();
    }

    public void addVeiculo(UUID veiculoId) {
        this.veiculos.add(veiculoId);
    }

    public void removeVeiculo(UUID veiculoId) {
        this.veiculos.remove(veiculoId);
    }

    public List<UUID> getVeiculos() {
        return veiculos;
    }
}
