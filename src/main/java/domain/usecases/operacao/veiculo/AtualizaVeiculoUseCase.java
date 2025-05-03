package domain.usecases.operacao.veiculo;

import java.util.UUID;

import domain.entities.operacao.StatusVeiculo;
import domain.entities.operacao.Veiculo;
import domain.entities.usuarios.Cliente;

public class AtualizaVeiculoUseCase {

    public Veiculo use(String id, Cliente cliente, String modelo, String placa, Integer anoFabricacao, String cor, StatusVeiculo status) {
        UUID uuid = UUID.fromString(id);

        // Validação do cliente, se for fornecido
        if (cliente != null && !Cliente.instances.contains(cliente)) {
            throw new RuntimeException("Cliente não existe");
        }

        for (Veiculo veiculo : Veiculo.instances) {
            if (veiculo.getId().equals(uuid)) {
                // Se cliente for passado, atualiza o ID do cliente no veículo
                if (cliente != null) veiculo.setCliente(cliente.getId());

                if (modelo != null) veiculo.setModelo(modelo);
                if (placa != null) veiculo.setPlaca(placa);
                if (anoFabricacao != null) veiculo.setAnoFabricacao(anoFabricacao);
                if (cor != null) veiculo.setCor(cor);
                if (status != null) veiculo.setStatus(status);
                return veiculo;
            }
        }
        return null;
    }
}
