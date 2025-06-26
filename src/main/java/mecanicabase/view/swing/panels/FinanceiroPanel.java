package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento financeiro (apenas para administradores).
 */
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
        // Formulário não necessário para este painel de navegação
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);
        
        // Adicionar módulos financeiros disponíveis
        tableModel.addRow(new Object[]{"Categorias de Despesa", "Gerenciamento de categorias de despesas"});
        tableModel.addRow(new Object[]{"Despesas", "Controle de despesas da oficina"});
        tableModel.addRow(new Object[]{"Relatórios", "Relatórios de vendas e serviços"});
        tableModel.addRow(new Object[]{"Balanço", "Balanço financeiro mensal"});
    }

    private void gerenciarCategorias() {
        showMessage("Funcionalidade em desenvolvimento - Categorias de Despesa");
    }

    private void gerenciarDespesas() {
        showMessage("Funcionalidade em desenvolvimento - Gestão de Despesas");
    }

    private void gerarRelatorios() {
        showMessage("Funcionalidade em desenvolvimento - Relatórios de Vendas");
    }

    private void gerarBalanco() {
        showMessage("Funcionalidade em desenvolvimento - Balanço Financeiro");
    }

    @Override
    protected boolean saveFormData() {
        // Não aplicável para este painel
        return false;
    }
}
