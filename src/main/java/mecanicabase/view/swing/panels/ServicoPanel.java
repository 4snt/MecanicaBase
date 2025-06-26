package mecanicabase.view.swing.panels;

import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Servico;

/**
 * Painel Swing para gerenciamento de serviços.
 */
public class ServicoPanel extends BasePanel {

    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField precoField;

    public ServicoPanel(ApplicationContext context) {
        super(context, "Gestão de Serviços");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "Descrição", "Preço"};
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
        buttonPanel.add(createButton("Novo Serviço", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Editar", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Excluir", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        nomeField = new JTextField(25);
        descricaoField = new JTextField(40);
        precoField = new JTextField(15);

        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "Descrição", descricaoField, 1);
        addFormField(formPanel, "Preço", precoField, 2);
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);

        try {
            List<Servico> servicos = context.servicoCrud.listarTodos();
            for (Servico servico : servicos) {
                Object[] row = {
                    servico.getId(),
                    servico.getTipo(),
                    servico.getDescricao(),
                    servico.getPreco()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Erro ao carregar serviços: " + e.getMessage());
        }
    }

    @Override
    protected boolean saveFormData() {
        showMessage("Funcionalidade em desenvolvimento.");
        return false;
    }

    @Override
    protected void clearForm() {
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
    }

}
