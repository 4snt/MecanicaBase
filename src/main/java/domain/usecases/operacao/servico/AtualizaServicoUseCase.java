package domain.usecases.operacao.servico;

import java.util.UUID;

import domain.entities.operacao.Servico;
import domain.entities.operacao.TipoElevador;
import domain.entities.usuarios.TipoFuncionario;

public class AtualizaServicoUseCase {

    public Servico use(String id, String tipo, Float preco, String descricao, Integer duracao, TipoFuncionario tipoFuncionario, TipoElevador tipoElevador, Boolean usaElevador) {
        UUID uuid = UUID.fromString(id);

        for (Servico servico : Servico.instances) {
            if (servico.getId().equals(uuid)) {
                if (tipo != null) servico.setTipo(tipo);
                if (preco != null) servico.setPreco(preco);
                if (descricao != null) servico.setDescricao(descricao);
                if (duracao != null) servico.setDuracao(duracao);
                if (tipoFuncionario != null) servico.setTipoFuncionario(tipoFuncionario);
                if (tipoElevador != null) servico.setTipoElevador(tipoElevador);
                if (usaElevador != null) servico.setUsaElevador(usaElevador);
                return servico;
            }
        }

        return null;
    }
}
