package mecanicabase.view.swing.panels;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.UUID;
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

    private JTextField buscaField;
    private UUID veiculoEditandoId = null;

    private JTextField modeloField;
    private JTextField placaField;
    private JTextField anoField;
    private JTextField corField;
    private JComboBox<Cliente> clienteCombo;

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
        table.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    protected void setupForm() {
        formPanel.removeAll();
        modeloField = new JTextField(20);
        placaField = new JTextField(10);
        anoField = new JTextField(4);
        corField = new JTextField(10);

        Color fg = Color.WHITE;
        Color bg = new Color(60, 63, 65);

        for (JTextField field : new JTextField[]{modeloField, placaField, anoField, corField}) {
            field.setForeground(fg);
            field.setBackground(bg);
        }

        clienteCombo = new JComboBox<>();
        clienteCombo.setEditable(true);
        clienteCombo.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setForeground(fg);
                setBackground(isSelected ? new Color(80, 80, 100) : bg);
                if (value instanceof Cliente c) {
                    setText(c.getNome() + " - " + c.getTelefone());
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

        clienteCombo.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = ((JTextField) clienteCombo.getEditor().getEditorComponent()).getText().toLowerCase();
                clienteCombo.removeAllItems();
                for (Cliente c : Cliente.instances) {
                    if (c.getNome().toLowerCase().contains(text) || c.getTelefone().toLowerCase().contains(text)) {
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
        buttonPanel.add(createButton("Editar", this::editarVeiculo));
        buttonPanel.add(createButton("Excluir", this::excluirVeiculo));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarVeiculo));
    }

    private void abrirFormularioNovoVeiculo() {
        clearForm();
        setupForm();
        JFrame parent = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Novo Veículo");
    }

    private void editarVeiculo() {
        if (!hasSelection()) {
            showMessage("Selecione um veículo para editar.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0);
        if (value == null) {
            showError("ID do veículo é nulo.");
            return;
        }
        veiculoEditandoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
        Veiculo v = context.veiculoCrud.buscarPorId(veiculoEditandoId.toString());
        if (v != null) {
            modeloField.setText(v.getModelo());
            placaField.setText(v.getPlaca());
            anoField.setText(String.valueOf(v.getAnoFabricacao()));
            corField.setText(v.getCor());
            clienteCombo.setSelectedItem(v.getCliente());
            showFormDialog((JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Veículo");
        } else {
            showError("Veículo não encontrado.");
        }
    }

    private void excluirVeiculo() {
        if (!hasSelection()) {
            showMessage("Selecione um veículo para excluir.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0);
        if (value == null) {
            showError("ID do veículo é nulo.");
            return;
        }
        try {
            UUID veiculoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
            boolean removido = context.veiculoCrud.removerPorId(veiculoId.toString());
            if (removido) {
                showMessage("Veículo excluído com sucesso!");
            } else {
                showError("Veículo não encontrado para exclusão.");
            }
        } catch (Exception e) {
            showError("Erro ao excluir veículo: " + e.getMessage());
        }
        loadData();
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Veiculo> veiculos = context.veiculoCrud.listarTodos();
            for (Veiculo veiculo : veiculos) {
                String clienteInfo = veiculo.getCliente() != null
                        ? veiculo.getCliente().getNome() + " - " + veiculo.getCliente().getTelefone()
                        : "N/A";
                tableModel.addRow(new Object[]{
                    veiculo.getId(),
                    veiculo.getPlaca(),
                    veiculo.getModelo() != null ? veiculo.getModelo() : "N/A",
                    clienteInfo
                });
            }
        } catch (Exception e) {
            showError("Erro ao carregar veículos: " + e.getMessage());
        }
    }

    private void buscarVeiculo() {
        String termo = buscaField.getText().trim();
        tableModel.setRowCount(0);
        try {
            List<Veiculo> veiculos = termo.isEmpty()
                    ? context.veiculoCrud.listarTodos()
                    : context.veiculoCrud.buscarPorFiltro(termo);
            for (Veiculo veiculo : veiculos) {
                String clienteInfo = veiculo.getCliente() != null
                        ? veiculo.getCliente().getNome() + " - " + veiculo.getCliente().getTelefone()
                        : "N/A";
                tableModel.addRow(new Object[]{
                    veiculo.getId(),
                    veiculo.getPlaca(),
                    veiculo.getModelo() != null ? veiculo.getModelo() : "N/A",
                    clienteInfo
                });
            }
        } catch (Exception e) {
            showError("Erro ao buscar veículos: " + e.getMessage());
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

        boolean placaExiste = context.veiculoCrud.listarTodos().stream()
                .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa) && (veiculoEditandoId == null || !v.getId().equals(veiculoEditandoId)));

        if (placaExiste) {
            showError("Já existe um veículo cadastrado com esta placa.");
            return false;
        }

        try {
            if (veiculoEditandoId != null) {
                context.veiculoCrud.atualizar(veiculoEditandoId.toString(), true, cor, ano, placa, modelo, cliente.getId(), null);
                showMessage("Veículo atualizado com sucesso!");
            } else {
                Veiculo v = context.veiculoCrud.criar(true, cor, ano, placa, modelo, cliente.getId());
                cliente.addVeiculo(v.getId());
                showMessage("Veículo cadastrado com sucesso!");
            }
            veiculoEditandoId = null;
            clearForm();
            loadData();
            return true;
        } catch (Exception e) {
            showError("Erro ao salvar veículo: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        modeloField.setText("");
        placaField.setText("");
        anoField.setText("");
        corField.setText("");
        if (clienteCombo.getItemCount() > 0) {
            clienteCombo.setSelectedIndex(0);
        }
    }
}
