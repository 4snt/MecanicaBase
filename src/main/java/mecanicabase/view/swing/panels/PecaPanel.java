package mecanicabase.view.swing.panels;

import java.util.List;
import java.util.UUID;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Peca;

public class PecaPanel extends BasePanel {

    private JTextField nomeField;
    private JTextField valorField;
    private JTextField quantidadeField;
    private JTextField buscaField;
    private UUID pecaEditandoId = null;

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
        table.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Nova Peça", this::abrirFormularioNovaPeca));
        buttonPanel.add(createButton("Editar", this::editarPeca));
        buttonPanel.add(createButton("Excluir", this::excluirPeca));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarPeca));
    }

    @Override
    protected void setupForm() {
        formPanel.removeAll();
        nomeField = new JTextField(20);
        valorField = new JTextField(10);
        quantidadeField = new JTextField(6);

        java.awt.Color fg = java.awt.Color.WHITE;
        java.awt.Color bg = new java.awt.Color(60, 63, 65);
        for (JTextField field : new JTextField[]{nomeField, valorField, quantidadeField}) {
            field.setForeground(fg);
            field.setBackground(bg);
        }

        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "Valor (R$)", valorField, 1);
        addFormField(formPanel, "Quantidade", quantidadeField, 2);
    }

    private void abrirFormularioNovaPeca() {
        clearForm();
        setupForm();
        javax.swing.JFrame parent = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Nova Peça");
    }

    private void editarPeca() {
        if (!hasSelection()) {
            showMessage("Selecione uma peça para editar.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0); // ID agora está na coluna 0
        if (value == null) {
            showError("ID da peça é nulo.");
            return;
        }
        pecaEditandoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
        String nome = (String) tableModel.getValueAt(selectedRow, 1);
        String valorStr = ((String) tableModel.getValueAt(selectedRow, 2)).replace("R$", "").replace(",", ".").trim();
        String quantidadeStr = tableModel.getValueAt(selectedRow, 3).toString();

        nomeField.setText(nome);
        valorField.setText(valorStr);
        quantidadeField.setText(quantidadeStr);

        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Peça");

    }

    private void excluirPeca() {
        if (!hasSelection()) {
            showMessage("Selecione uma peça para excluir.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0); // ID agora está na coluna 0
        if (value == null) {
            showError("ID da peça é nulo.");
            return;
        }
        if (!confirmAction("Tem certeza que deseja excluir esta peça?")) {
            return;
        }
        try {
            UUID pecaId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
            boolean removido = context.pecaCrud.removerPorId(pecaId.toString());
            if (removido) {
                showMessage("Peça excluída com sucesso!");
            } else {
                showError("Peça não encontrada para exclusão.");
            }
        } catch (Exception e) {
            showError("Erro ao excluir peça: " + e.getMessage());
        }
        loadData();
    }

    private void buscarPeca() {
        String termo = buscaField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        try {
            List<Peca> pecas = context.pecaCrud.listarTodos();
            pecas = termo.isEmpty()
                    ? pecas
                    : pecas.stream()
                            .filter(p -> p.getNome().toLowerCase().contains(termo)
                            || String.valueOf(p.getValor()).contains(termo)
                            || String.valueOf(p.getQuantidade()).contains(termo))
                            .toList();

            for (Peca peca : pecas) {
                tableModel.addRow(new Object[]{
                    peca.getId(),
                    peca.getNome(),
                    String.format("R$ %.2f", peca.getValor()),
                    peca.getQuantidade()
                });
            }
        } catch (Exception e) {
            showError("Erro ao buscar peças: " + e.getMessage());
        }
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
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
        String nome = nomeField.getText().trim();
        String valorStr = valorField.getText().trim().replace(",", ".");
        String quantidadeStr = quantidadeField.getText().trim();
        if (nome.isEmpty() || valorStr.isEmpty() || quantidadeStr.isEmpty()) {
            showError("Preencha todos os campos.");
            return false;
        }
        float valor;
        int quantidade;
        try {
            valor = Float.parseFloat(valorStr);
        } catch (NumberFormatException e) {
            showError("Valor inválido.");
            return false;
        }
        try {
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            showError("Quantidade inválida.");
            return false;
        }

        boolean nomeExiste = context.pecaCrud.listarTodos().stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nome)
                && (pecaEditandoId == null || !p.getId().equals(pecaEditandoId)));

        if (nomeExiste) {
            showError("Já existe uma peça cadastrada com este nome.");
            return false;
        }

        try {
            if (pecaEditandoId != null) {
                context.pecaCrud.atualizar(pecaEditandoId.toString(), true, nome, valor, quantidade);
                showMessage("Peça atualizada com sucesso!");
            } else {
                context.pecaCrud.criar(true, nome, valor, quantidade);
                showMessage("Peça cadastrada com sucesso!");
            }
            pecaEditandoId = null;
            clearForm();
            loadData();
            return true;
        } catch (Exception e) {
            showError("Erro ao salvar peça: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        nomeField.setText("");
        valorField.setText("");
        quantidadeField.setText("");
    }
}
