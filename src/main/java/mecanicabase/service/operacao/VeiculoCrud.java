package mecanicabase.service.operacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import mecanicabase.core.Crud;
import mecanicabase.model.operacao.Sistema;
import mecanicabase.model.operacao.StatusVeiculo;
import mecanicabase.model.operacao.Veiculo;

public class VeiculoCrud extends Crud<Veiculo> {

    @Override
    protected List<Veiculo> getInstancias() {
        return Veiculo.instances;
    }

    @Override
    protected UUID getId(Veiculo entidade) {
        return entidade.getId();
    }

    public List<Veiculo> buscarPorFiltro(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return listarTodos();
        }

        String filtroLower = filtro.toLowerCase();

        return listarTodos().stream()
                .filter(veiculo
                        -> veiculo.getPlaca().toLowerCase().contains(filtroLower)
                || veiculo.getModelo().toLowerCase().contains(filtroLower)
                || veiculo.getCliente().getId().toString().equalsIgnoreCase(filtroLower)
                )
                .collect(Collectors.toList());
    }

    @Override
    protected Veiculo criarInstancia(Object... params) {
        String cor = (String) params[0];
        int anoFabricacao = (int) params[1];
        String placa = (String) params[2];
        String modelo = (String) params[3];
        UUID clientId = (UUID) params[4];

        Veiculo veiculo = new Veiculo(clientId, modelo, placa, anoFabricacao, cor);
        Veiculo.instances.add(veiculo);
        Sistema.incrementarTotalVeiculos();
        return veiculo;
    }

    @Override
    protected void atualizarInstancia(Veiculo veiculo, Object... params) {
        String cor = (String) params[0];
        Integer anoFabricacao = (Integer) params[1];
        String placa = (String) params[2];
        String modelo = (String) params[3];
        UUID clientId = (UUID) params[4];
        StatusVeiculo status = (StatusVeiculo) params[5];

        if (clientId != null) {
            veiculo.setCliente(clientId);
        }
        if (modelo != null) {
            veiculo.setModelo(modelo);
        }
        if (placa != null) {
            veiculo.setPlaca(placa);
        }
        if (anoFabricacao != null) {
            veiculo.setAnoFabricacao(anoFabricacao);
        }
        if (cor != null) {
            veiculo.setCor(cor);
        }
        if (status != null) {
            veiculo.setStatus(status);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String cor = (String) params[0];
        Integer anoFabricacao = (Integer) params[1];
        String placa = (String) params[2];
        String modelo = (String) params[3];
        UUID clientId = (UUID) params[4];

        return cor != null && !cor.isBlank()
                && modelo != null && !modelo.isBlank()
                && placa != null && !placa.isBlank()
                && clientId != null
                && anoFabricacao != null && anoFabricacao >= 1900;
    }

    @Override
    protected boolean validarAtualizacao(Veiculo veiculo, Object... params) {
        return veiculo != null;
    }

    @Override
    public boolean removerPorId(String id) {
        Veiculo veiculo = buscarPorId(id);
        if (veiculo != null) {
            boolean removido = Veiculo.instances.remove(veiculo);
            if (removido) {
                Sistema.reduzirTotalVeiculos(); // se existir
            }
            return removido;
        }
        return false;
    }
}
