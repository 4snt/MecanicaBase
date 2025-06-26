package mecanicabase.view.swing.panels;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;

import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;

public class VeiculoPanel extends BasePanel {

    public VeiculoPanel(ApplicationContext context) {
        super(context, "Gestão de Veículos");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Placa", "Modelo", "Cliente"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new javax.swing.JTable(tableModel);
    }

    private javax.swing.JButton novoVeiculoButton;
    private javax.swing.JButton editarButton;
    private javax.swing.JButton excluirButton;

    private JTextField modeloField;
    private JTextField placaField;
    private JTextField anoField;
    private JTextField corField;
    private JComboBox<Cliente> clienteCombo;

    @Override
    protected void setupForm() {
        formPanel.removeAll();
        modeloField = new JTextField(20);
        placaField = new JTextField(10);
        anoField = new JTextField(4);
        corField = new JTextField(10);
        // Cores claras nos campos
        Color fg = Color.WHITE;
        Color bg = new Color(60, 63, 65);
        modeloField.setForeground(fg);
        modeloField.setBackground(bg);
        placaField.setForeground(fg);
        placaField.setBackground(bg);
        anoField.setForeground(fg);
        anoField.setBackground(bg);
        corField.setForeground(fg);
        corField.setBackground(bg);
        // Combo de clientes com filtro e exibição customizada
        clienteCombo = new JComboBox<>();
        clienteCombo.setEditable(true);
        clienteCombo.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setForeground(fg);
                setBackground(isSelected ? new Color(80, 80, 100) : bg);
                if (value instanceof Cliente c) {
                    setText(c.getNome() + " - " + c.getEndereco());
                } else {
                    setText("");
                }
                return this;
            }
        });
        for (Cliente c : Cliente.instances) {
            clienteCombo.addItem(c);
        }
        addFormField(formPanel, "Modelo", modeloField, 0);
        addFormField(formPanel, "Placa", placaField, 1);
        addFormField(formPanel, "Ano", anoField, 2);
        addFormField(formPanel, "Cor", corField, 3);
        addFormField(formPanel, "Cliente", clienteCombo, 4);
        // Filtro/autocomplete simples
        clienteCombo.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = ((JTextField) clienteCombo.getEditor().getEditorComponent()).getText().toLowerCase();
                clienteCombo.removeAllItems();
                for (Cliente c : Cliente.instances) {
                    if (c.getNome().toLowerCase().contains(text) || c.getEndereco().toLowerCase().contains(text)) {
                        clienteCombo.addItem(c);
                    }
                }
                clienteCombo.setPopupVisible(true);
            }
        });
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Novo Veículo", this::abrirFormularioNovoVeiculo));
        buttonPanel.add(createButton("Editar", () -> showMessage("Funcionalidade em desenvolvimento")));
        buttonPanel.add(createButton("Excluir", () -> showMessage("Funcionalidade em desenvolvimento")));
    }

    private void abrirFormularioNovoVeiculo() {
        clearForm();
        setupForm();
        JFrame parent = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Novo Veículo");
    }

    @Override
    protected void loadData() {
        try {
            tableModel.setRowCount(0);
            List<Veiculo> veiculos = context.veiculoCrud.listarTodos();
            for (Veiculo veiculo : veiculos) {
                tableModel.addRow(new Object[]{
                    veiculo.getId(),
                    veiculo.getPlaca(),
                    veiculo.getModelo() != null ? veiculo.getModelo() : "N/A",
                    veiculo.getCliente() != null ? veiculo.getCliente().getNome() : "N/A"
                });
            }
        } catch (Exception e) {
            showError("Erro ao carregar veículos: " + e.getMessage());
        }
    }

    @Override
    protected boolean saveFormData() {
        String modelo = modeloField.getText().trim();
        String placa = placaField.getText().trim();
        String anoStr = anoField.getText().trim();
        String cor = corField.getText().trim();
        Cliente cliente = (Cliente) clienteCombo.getSelectedItem();
        if (modelo.isEmpty() || placa.isEmpty() || anoStr.isEmpty() || cor.isEmpty() || cliente == null) {
            showError("Preencha todos os campos e selecione um cliente.");
            return false;
        }
        int ano;
        try {
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            showError("Ano inválido.");
            return false;
        }
        // Prevenir placas duplicadas
        List<Veiculo> veiculos = context.veiculoCrud.listarTodos();
        boolean placaExiste = veiculos.stream().anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
        if (placaExiste) {
            showError("Já existe um veículo cadastrado com esta placa.");
            return false;
        }
        try {
            Veiculo v = context.veiculoCrud.criar(true, cor, ano, placa, modelo, cliente.getId());
            cliente.addVeiculo(v.getId());
            showMessage("Veículo cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            showError("Erro ao cadastrar veículo: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        if (modeloField != null) {
            modeloField.setText("");
        }
        if (placaField != null) {
            placaField.setText("");
        }
        if (anoField != null) {
            anoField.setText("");
        }
        if (corField != null) {
            corField.setText("");
        }
        if (clienteCombo != null && clienteCombo.getItemCount() > 0) {
            clienteCombo.setSelectedIndex(0);
        }
    }
}
