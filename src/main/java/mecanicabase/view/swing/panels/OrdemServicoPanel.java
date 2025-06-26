package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento de ordens de serviço.
 */
public class OrdemServicoPanel extends BasePanel {

    public OrdemServicoPanel(ApplicationContext context) {
        super(context, "Gestão de Ordens de Serviço");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Cliente", "Veículo", "Data", "Status", "Total"};
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
        buttonPanel.add(createButton("Nova OS", this::novaOrdemServico));
        buttonPanel.add(createButton("Editar", this::editarOrdemServico));
        buttonPanel.add(createButton("Finalizar", this::finalizarOrdemServico));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        // Implementação futura com campos para ordem de serviço
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);

        try {
            context.ordemCrud.listarTodos().forEach(ordem -> {
                // Buscar nome do cliente pelo ID
                String clienteNome = "N/A";
                if (ordem.getClienteId() != null) {
                    var cliente = context.clienteCrud.buscarPorId(ordem.getClienteId());
                    if (cliente != null && cliente.getNome() != null) {
                        clienteNome = cliente.getNome();
                    }
                }

                // Buscar placa do veículo pelo primeiro agendamento (se houver)
                String placaVeiculo = "N/A";
                if (!ordem.getAgendamentos().isEmpty()) {
                    var agendamento = ordem.getAgendamentos().get(0);
                    if (agendamento.getVeiculo() != null) {
                        placaVeiculo = agendamento.getVeiculo().getPlaca();
                    }
                }

                Object[] row = {
                    ordem.getId(),
                    clienteNome,
                    placaVeiculo,
                    ordem.getFinalizadoEm() != null ? ordem.getFinalizadoEm().toString() : "N/A",
                    ordem.getStatus() != null ? ordem.getStatus().toString() : "N/A",
                    "R$ 0,00" // Valor total: ajuste se houver método/calculo
                };
                tableModel.addRow(row);
            });
        } catch (Exception e) {
            showError("Erro ao carregar ordens de serviço: " + e.getMessage());
        }
    }

    private void novaOrdemServico() {
        showMessage("Funcionalidade em desenvolvimento - Nova Ordem de Serviço");
    }

    private void editarOrdemServico() {
        if (!hasSelection()) {
            showMessage("Selecione uma ordem de serviço para editar.");
            return;
        }
        showMessage("Funcionalidade em desenvolvimento - Editar Ordem de Serviço");
    }

    private void finalizarOrdemServico() {
        if (!hasSelection()) {
            showMessage("Selecione uma ordem de serviço para finalizar.");
            return;
        }

        if (confirmAction("Tem certeza que deseja finalizar esta ordem de serviço?")) {
            showMessage("Funcionalidade em desenvolvimento - Finalizar Ordem de Serviço");
        }
    }

    @Override
    protected boolean saveFormData() {
        // Implementação futura
        return false;
    }
}
