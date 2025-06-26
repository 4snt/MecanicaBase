package mecanicabase.view.swing.panels;

import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento da oficina (peças e veículos).
 */
public class OficinaPanel extends BasePanel {

    public OficinaPanel(ApplicationContext context) {
        super(context, "Gestão de Oficina");
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
        buttonPanel.add(createButton("Gerenciar Peças", this::gerenciarPecas));
        buttonPanel.add(createButton("Gerenciar Veículos", this::gerenciarVeiculos));
        buttonPanel.add(createButton("Atualizar", this::loadData));
    }

    @Override
    protected void setupForm() {
        // Formulário não necessário para este painel de navegação
    }

    @Override
    protected void loadData() {
        tableModel.setRowCount(0);

        // Adicionar módulos disponíveis
        tableModel.addRow(new Object[]{"Peças", "Gerenciamento de peças e estoque"});
        tableModel.addRow(new Object[]{"Veículos", "Cadastro e acompanhamento de veículos"});
        tableModel.addRow(new Object[]{"Sistemas", "Sistemas mecânicos dos veículos"});
    }

    private void gerenciarPecas() {
        new PecaPanel(context).setVisible(true);
    }

    private void gerenciarVeiculos() {
        new VeiculoPanel(context).setVisible(true);
    }

    @Override
    protected boolean saveFormData() {
        // Não aplicável para este painel
        return false;
    }

    // Classe interna simples para gerenciamento de peças
    private static class PecaPanel extends BasePanel {

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
        }

        @Override
        protected void loadData() {
            try {
                tableModel.setRowCount(0);
                context.pecaCrud.listarTodos().forEach(peca -> {
                    tableModel.addRow(new Object[]{
                        peca.getId(),
                        peca.getNome(),
                        String.format("R$ %.2f", peca.getValor()), // Valor nunca é null
                        peca.getQuantidade() // Quantidade nunca é null
                    });
                });
            } catch (Exception e) {
                showError("Erro ao carregar peças: " + e.getMessage());
            }
        }

        @Override
        protected boolean saveFormData() {
            return false;
        }
    }

    // Classe interna simples para gerenciamento de veículos
    private static class VeiculoPanel extends BasePanel {

        public VeiculoPanel(ApplicationContext context) {
            super(context, "Gestão de Veículos");
        }

        @Override
        protected void setupTable() {
            String[] columns = {"ID", "Placa", "Marca", "Modelo", "Cliente"};
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
            buttonPanel.add(createButton("Novo Veículo", () -> showMessage("Funcionalidade em desenvolvimento")));
            buttonPanel.add(createButton("Editar", () -> showMessage("Funcionalidade em desenvolvimento")));
            buttonPanel.add(createButton("Excluir", () -> showMessage("Funcionalidade em desenvolvimento")));
        }

        @Override
        protected void setupForm() {
        }

        @Override
        protected void loadData() {
            try {
                tableModel.setRowCount(0);
                context.veiculoCrud.listarTodos().forEach(veiculo -> {
                    tableModel.addRow(new Object[]{
                        veiculo.getId(),
                        veiculo.getPlaca(),
                        veiculo.getModelo() != null ? veiculo.getModelo() : "N/A",
                        veiculo.getCliente() != null ? veiculo.getCliente().getNome() : "N/A"
                    });
                });
            } catch (Exception e) {
                showError("Erro ao carregar veículos: " + e.getMessage());
            }
        }

        @Override
        protected boolean saveFormData() {
            return false;
        }
    }
}
