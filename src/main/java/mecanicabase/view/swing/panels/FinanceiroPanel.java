package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.view.swing.dialogs.BalancoDialog;
import mecanicabase.view.swing.dialogs.CategoriaDespesaDialog;
import mecanicabase.view.swing.dialogs.DespesaDialog;
import mecanicabase.view.swing.dialogs.RelatorioDialog;

public class FinanceiroPanel extends BasePanel {

    public FinanceiroPanel(ApplicationContext context) {
        super(context, "Gestão Financeira");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"Módulo", "Descrição"};
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
        buttonPanel.add(createButton("Categorias de Despesa", this::gerenciarCategorias));
        buttonPanel.add(createButton("Despesas", this::gerenciarDespesas));
        buttonPanel.add(createButton("Relatórios", this::gerarRelatorios));
        buttonPanel.add(createButton("Balanço", this::gerarBalanco));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        // Não há formulário neste painel
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"Categorias de Despesa", "Gerenciamento de categorias de despesas"});
        tableModel.addRow(new Object[]{"Despesas", "Controle de despesas da oficina"});
        tableModel.addRow(new Object[]{"Relatórios", "Relatórios de vendas e serviços"});
        tableModel.addRow(new Object[]{"Balanço", "Balanço financeiro mensal"});
    }

    private void gerenciarCategorias() {
        new CategoriaDespesaDialog(context).setVisible(true);
    }

    private void gerenciarDespesas() {
        new DespesaDialog(context).setVisible(true);
    }

    private void gerarRelatorios() {
        new RelatorioDialog().setVisible(true); // corrigido
    }

    private void gerarBalanco() {
        new BalancoDialog().setVisible(true); // corrigido
    }

    @Override
    protected boolean saveFormData() {
        return false;
    }
}
