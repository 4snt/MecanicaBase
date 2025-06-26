package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento de colaboradores (apenas para
 * administradores).
 */
public class ColaboradorPanel extends BasePanel {

    public ColaboradorPanel(ApplicationContext context) {
        super(context, "Gestão de Colaboradores");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "CPF", "Cargo", "Email", "Telefone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new javax.swing.JTable(tableModel);
        table.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Novo Colaborador", this::novoColaborador));
        buttonPanel.add(createButton("Editar", this::editarColaborador));
        buttonPanel.add(createButton("Desativar", this::desativarColaborador));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        // Implementação futura com campos para colaborador
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);

        try {
            context.funcionarioCrud.listarTodos().forEach(funcionario -> {
                Object[] row = {
                    funcionario.getId(),
                    funcionario.getNome() != null ? funcionario.getNome() : "N/A",
                    funcionario.getCpf() != null ? funcionario.getCpf() : "N/A",
                    funcionario.getFuncao() != null ? funcionario.getFuncao().name() : "N/A", // <-- ajuste aqui
                    funcionario.getEmail() != null ? funcionario.getEmail() : "N/A",
                    funcionario.getTelefone() != null ? funcionario.getTelefone() : "N/A"
                };
                tableModel.addRow(row);
            });
        } catch (Exception e) {
            showError("Erro ao carregar colaboradores: " + e.getMessage());
        }
    }

    private void novoColaborador() {
        showMessage("Funcionalidade em desenvolvimento - Novo Colaborador");
    }

    private void editarColaborador() {
        if (!hasSelection()) {
            showMessage("Selecione um colaborador para editar.");
            return;
        }
        showMessage("Funcionalidade em desenvolvimento - Editar Colaborador");
    }

    private void desativarColaborador() {
        if (!hasSelection()) {
            showMessage("Selecione um colaborador para desativar.");
            return;
        }

        if (confirmAction("Tem certeza que deseja desativar este colaborador?")) {
            showMessage("Funcionalidade em desenvolvimento - Desativar Colaborador");
        }
    }

    @Override
    protected boolean saveFormData() {
        // Implementação futura
        return false;
    }
}
