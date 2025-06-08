package mecanicabase.service.financeiro;

import java.util.List;
import java.util.UUID;
import mecanicabase.core.Crud;
import mecanicabase.model.financeiro.CategoriaDespesa;

public class CategoriaDespesaCrud extends Crud<CategoriaDespesa> {

    @Override
    protected List<CategoriaDespesa> getInstancias() {
        return CategoriaDespesa.instances;
    }

    @Override
    protected UUID getId(CategoriaDespesa entidade) {
        return entidade.getId();
    }

    @Override
    protected CategoriaDespesa criarInstancia(Object... params) {
        String titulo = (String) params[0];
        return new CategoriaDespesa(titulo);
    }

    @Override
    protected void atualizarInstancia(CategoriaDespesa categoria, Object... params) {
        String novoTitulo = (String) params[0];

        CategoriaDespesa nova = new CategoriaDespesa(novoTitulo);
        CategoriaDespesa.instances.remove(categoria);
        CategoriaDespesa.instances.add(nova);
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        try {
            String titulo = (String) params[0];
            return titulo != null && !titulo.isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean validarAtualizacao(CategoriaDespesa categoria, Object... params) {
        try {
            String novoTitulo = (String) params[0];
            return novoTitulo != null && !novoTitulo.isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}
