package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento de agendamentos.
 */
public class AgendamentoPanel extends BasePanel {

    public AgendamentoPanel(ApplicationContext context) {
        super(context, "Gestão de Agendamentos");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Cliente", "Data", "Hora", "Descrição", "Status"};
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
        buttonPanel.add(createButton("Novo Agendamento", this::novoAgendamento));
        buttonPanel.add(createButton("Editar", this::editarAgendamento));
        buttonPanel.add(createButton("Cancelar", this::cancelarAgendamento));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        // Implementação futura com campos para agendamento
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);

        try {
            context.agendamentoCrud.listarTodos().forEach(agendamento -> {
                // Obtendo o nome do cliente via veículo, se possível
                String clienteNome = "N/A";
                if (agendamento.getVeiculo() != null && agendamento.getVeiculo().getCliente() != null) {
                    clienteNome = agendamento.getVeiculo().getCliente().getNome();
                }

                Object[] row = {
                    agendamento.getId(),
                    clienteNome,
                    agendamento.getData() != null ? agendamento.getData().toLocalDate().toString() : "N/A",
                    agendamento.getData() != null ? agendamento.getData().toLocalTime().toString() : "N/A",
                    agendamento.getDescricaoProblema(),
                    agendamento.getStatus() != null ? agendamento.getStatus().name() : "N/A"
                };
                tableModel.addRow(row);
            });
        } catch (Exception e) {
            showError("Erro ao carregar agendamentos: " + e.getMessage());
        }
    }

    private void novoAgendamento() {
        showMessage("Funcionalidade em desenvolvimento - Novo Agendamento");
    }

    private void editarAgendamento() {
        if (!hasSelection()) {
            showMessage("Selecione um agendamento para editar.");
            return;
        }
        showMessage("Funcionalidade em desenvolvimento - Editar Agendamento");
    }

    private void cancelarAgendamento() {
        if (!hasSelection()) {
            showMessage("Selecione um agendamento para cancelar.");
            return;
        }

        if (confirmAction("Tem certeza que deseja cancelar este agendamento?")) {
            showMessage("Funcionalidade em desenvolvimento - Cancelar Agendamento");
        }
    }

    @Override
    protected boolean saveFormData() {
        // Implementação futura
        return false;
    }
}
