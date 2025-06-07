package mecanicabase.service.operacao;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;

public class ServicoCrud extends Crud<Servico> {

    @Override
    protected List<Servico> getInstancias() {
        return Servico.instances;
    }

    @Override
    protected UUID getId(Servico entidade) {
        return entidade.getId();
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
        TipoFuncionario tipoFuncionario = (TipoFuncionario) params[4];
        TipoElevador tipoElevador = (TipoElevador) params[5];
        Boolean usaElevador = (Boolean) params[6];

        if (tipo != null) {
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
        if (tipoFuncionario != null) {
            servico.setTipoFuncionario(tipoFuncionario);
        }
        if (tipoElevador != null) {
            servico.setTipoElevador(tipoElevador);
        }
        if (usaElevador != null) {
            servico.setUsaElevador(usaElevador);
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
}
