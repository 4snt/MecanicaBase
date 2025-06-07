package mecanicabase.service.usuarios.administrador;

import java.util.UUID;
import mecanicabase.model.usuarios.Administrador;

/**
 * Caso de uso responsável por remover um administrador do sistema.
 */
public class RemoverAdministradorUseCase {

    /**
     * Remove um administrador identificado pelo seu UUID.
     *
     * @param id O ID do administrador a ser removido.
     * @return true se o administrador foi removido com sucesso, false caso
     * contrário.
     */
    public boolean use(String id) {
        // Converte o ID fornecido para UUID
        UUID uuid = UUID.fromString(id);

        // Remove o administrador da lista de instâncias caso o ID corresponda
        return Administrador.instances.removeIf(administrador -> administrador.getId().equals(uuid));
    }
}
