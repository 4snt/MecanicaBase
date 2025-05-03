package domain.usecases.operacao.veiculo; 

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.entities.operacao.Veiculo;

public class ListaVeiculoUseCase{
    public List<Veiculo> use(String filtro){
        if (filtro == null || filtro.isBlank()){
            return Veiculo.instances;
        }

        String filtroLower = filtro.toLowerCase();
                return Veiculo.instances.stream()
                    .filter(veiculo ->
                            veiculo.getPlaca().toLowerCase().contains(filtroLower) ||
                            veiculo.getModelo().toLowerCase().contains(filtroLower) ||
                            veiculo.getCliente().getId().equals(UUID.fromString(filtroLower))
                    )
                    .collect(Collectors.toList());
    }


}