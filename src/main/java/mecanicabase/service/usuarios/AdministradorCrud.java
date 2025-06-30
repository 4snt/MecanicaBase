package mecanicabase.service.usuarios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mecanicabase.infra.crypto.JasyptCrypto;
import mecanicabase.model.usuarios.Administrador;

public class AdministradorCrud extends UsuarioCrud<Administrador> {

    public AdministradorCrud() {
        reindexar();
    }

    @Override
    protected List<Administrador> getInstancias() {
        return Administrador.instances;
    }

    @Override
    protected UUID getId(Administrador entidade) {
        return entidade.getId();
    }

    @Override
    protected Administrador criarInstancia(Object... params) {
        String nome = (String) params[0];
        String email = (String) params[1];
        String senha = (String) params[2];
        String cpfOriginal = (String) params[3];
        String telefone = (String) params[4];
        String endereco = (String) params[5];

        String cpf = JasyptCrypto.encrypt(cpfOriginal);

        return new Administrador(nome, email, senha, cpf, telefone, endereco);
    }

    @Override
    protected void atualizarInstancia(Administrador adm, Object... params) {
        String nome = (String) params[0];
        String senha = (String) params[1];
        String telefone = (String) params[2];
        String endereco = (String) params[3];

        if (nome != null && !nome.isBlank()) {
            adm.setNome(nome);
        }
        if (senha != null && !senha.isBlank()) {
            adm.setSenha(senha);
        }
        if (telefone != null && !telefone.isBlank()) {
            adm.setTelefone(telefone);
        }
        if (endereco != null && !endereco.isBlank()) {
            adm.setEndereco(endereco);
        }
    }

    @Override
    protected boolean validarCriacao(Object... params) {
        String email = (String) params[1];
        String cpfOriginal = (String) params[3];

        boolean emailExiste = Administrador.instances.stream()
                .anyMatch(a -> a.getEmail().equalsIgnoreCase(email));

        boolean cpfExiste = Administrador.instances.stream()
                .anyMatch(a -> JasyptCrypto.compareTo(cpfOriginal, a.getCpf()));

        if (emailExiste) {
            throw new IllegalArgumentException("Já existe um administrador com este email.");
        }
        if (cpfExiste) {
            throw new IllegalArgumentException("Já existe um administrador com este CPF.");
        }

        return true;
    }

    @Override
    protected boolean validarAtualizacao(Administrador adm, Object... params) {
        if (adm == null) {
            return false;
        }

        String novoEmail = (String) params[0];

        if (novoEmail != null && !novoEmail.equalsIgnoreCase(adm.getEmail())) {
            boolean emailExiste = Administrador.instances.stream()
                    .anyMatch(a -> a.getEmail().equalsIgnoreCase(novoEmail));
            if (emailExiste) {
                throw new IllegalArgumentException("Outro administrador já usa este email.");
            }
        }

        return true;
    }

    public Map<UUID, Administrador> index() {
        Map<UUID, Administrador> index = new HashMap<>();
        for (Administrador a : getInstancias()) {
            index.put(a.getId(), a);
        }
        return index;
    }

}
