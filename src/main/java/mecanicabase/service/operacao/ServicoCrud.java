package mecanicabase.service.operacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;

public class ServicoCrud extends Crud<Servico> {

    public ServicoCrud() {
        reindexar();

    }

    @Override
    protected List<Servico> getInstancias() {
        return Servico.instances;
    }

    @Override
    protected UUID getId(Servico entidade) {
        return entidade.getId();
    }
    private boolean validarDuplicidade = true;

    public void setValidarDuplicidade(boolean validarDuplicidade) {
        this.validarDuplicidade = validarDuplicidade;
    }

    @Override
    protected Servico criarInstancia(Object... params) {
        String tipo = (String) params[0];
        float preco = (Float) params[1];
        String descricao = (String) params[2];
        Integer duracao = (Integer) params[3];
        TipoFuncionario tipoFuncionario = (TipoFuncionario) params[4];
        TipoElevador tipoElevador = (TipoElevador) params[5];
        Boolean usaElevador = (Boolean) params[6];

        Servico servico = new Servico(tipo, preco, descricao);
        if (duracao != null) {
            servico.setDuracao(duracao);
        }
        if (tipoFuncionario != null) {
            servico.setTipoFuncionario(tipoFuncionario);
        }
        if (tipoElevador != null) {
            servico.setTipoElevador(tipoElevador);
        }
        if (usaElevador != null) {
            servico.setUsaElevador(usaElevador);
        }

        return servico;
    }

    @Override
    protected void atualizarInstancia(Servico servico, Object... params) {
        String tipo = (String) params[0];
        Float preco = (Float) params[1];
        String descricao = (String) params[2];
        Integer duracao = (Integer) params[3];

        if (tipo != null && !tipo.equalsIgnoreCase(servico.getTipo())) {
            if (validarDuplicidade) {
                boolean tipoRepetido = Servico.instances.stream()
                        .anyMatch(s -> s.getTipo().equalsIgnoreCase(tipo) && !s.getId().equals(servico.getId()));
                if (tipoRepetido) {
                    throw new RuntimeException("Já existe outro serviço com esse nome.");
                }
            }
            servico.setTipo(tipo);
        }

        if (preco != null) {
            servico.setPreco(preco);
        }
        if (descricao != null) {
            servico.setDescricao(descricao);
        }
        if (duracao != null) {
            servico.setDuracao(duracao);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String tipo = (String) params[0];
        Float preco = (Float) params[1];
        return tipo != null && !tipo.isBlank() && preco != null && preco >= 0;
    }

    @Override
    protected boolean validarAtualizacao(Servico servico, Object... params) {
        return servico != null;
    }

    public Map<UUID, Servico> index() {
        Map<UUID, Servico> index = new HashMap<>();
        for (Servico s : getInstancias()) {
            index.put(s.getId(), s);
        }
        return index;
    }

}
