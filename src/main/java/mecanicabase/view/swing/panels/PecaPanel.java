package mecanicabase.view.swing.panels;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Peca;

public class PecaPanel extends BasePanel {

    public PecaPanel(ApplicationContext context) {
        super(context, "Gestão de Peças");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "Preço", "Quantidade"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new javax.swing.JTable(tableModel);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Nova Peça", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Editar", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Excluir", () -> showMessage("Funcionalidade em desenvolvimento")));
    }

    @Override
    protected void setupForm() {
        // Não implementado
    }

    @Override
    protected void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Peca> pecas = context.pecaCrud.listarTodos();
            for (Peca peca : pecas) {
                tableModel.addRow(new Object[]{
                    peca.getId(),
                    peca.getNome(),
                    String.format("R$ %.2f", peca.getValor()),
                    peca.getQuantidade()
                });
            }
        } catch (Exception e) {
            showError("Erro ao carregar peças: " + e.getMessage());
        }
    }

    @Override
    protected boolean saveFormData() {
        return false;
    }
}
