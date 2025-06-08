package mecanicabase.service.financeiro;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;

public class DespesaCrud extends Crud<Despesa> {

    @Override
    protected List<Despesa> getInstancias() {
        return Despesa.instances;
    }

    @Override
    protected UUID getId(Despesa entidade) {
        return entidade.getId();
    }

    @Override
    protected Despesa criarInstancia(Object... params) {
        UUID categoriaId = (UUID) params[0];
        String descricao = (String) params[1];
        float valor = (Float) params[2];

        return new Despesa(categoriaId, descricao, valor);
    }

    @Override
    protected void atualizarInstancia(Despesa despesa, Object... params) {
        String novaDescricao = (String) params[0];
        Float novoValor = (Float) params[1];

        if (novaDescricao != null) {
            despesa.setDescricao(novaDescricao);
        }
        if (novoValor != null && novoValor >= 0) {
            despesa.setValor(novoValor);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        try {
            UUID categoriaId = (UUID) params[0];
            String descricao = (String) params[1];
            float valor = (Float) params[2];

            return categoriaId != null
                    && CategoriaDespesa.buscarPorId(categoriaId) != null
                    && descricao != null && !descricao.isBlank()
                    && valor >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean validarAtualizacao(Despesa despesa, Object... params) {
        try {
            String novaDescricao = (String) params[0];
            Float novoValor = (Float) params[1];

            boolean descricaoOk = novaDescricao == null || !novaDescricao.isBlank();
            boolean valorOk = novoValor == null || novoValor >= 0;

            return descricaoOk && valorOk;
        } catch (Exception e) {
            return false;
        }
    }
}
