package mecanicabase.view.swing.panels;

import java.util.List;
import java.util.UUID;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.TipoElevador;
import mecanicabase.model.usuarios.TipoFuncionario;

public class ServicoPanel extends BasePanel {

    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField precoField;
    private JTextField buscaField;
    private JTextField duracaoField;
    private UUID servicoEditandoId = null;
    private javax.swing.JComboBox<TipoFuncionario> tipoFuncionarioCombo;
    private javax.swing.JCheckBox usaElevadorCheck;
    private javax.swing.JComboBox<TipoElevador> tipoElevadorCombo;

    public ServicoPanel(ApplicationContext context) {
        super(context, "Gestão de Serviços");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "Descrição", "Preço", "Duração (min)", "Tipo de Mecânico", "Usa Elevador", "Tipo de Elevador"};
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
        buttonPanel.add(createButton("Novo Serviço", this::abrirFormularioNovoServico));
        buttonPanel.add(createButton("Editar", this::editarServico));
        buttonPanel.add(createButton("Excluir", this::excluirServico));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarServico));
    }

    private void abrirFormularioNovoServico() {
        clearForm();
        servicoEditandoId = null;
        javax.swing.JFrame parent = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Novo Serviço");
    }

    private void editarServico() {
        if (!hasSelection()) {
            showMessage("Selecione um serviço para editar.");
            return;
        }

        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0);
        if (value == null) {
            showError("ID do serviço é nulo.");
            return;
        }

        servicoEditandoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());

        Servico servico = context.servicoCrud.listarTodos().stream()
                .filter(s -> s.getId().equals(servicoEditandoId)).findFirst().orElse(null);
        if (servico == null) {
            showError("Serviço não encontrado.");
            return;
        }

        nomeField.setText(servico.getTipo());
        descricaoField.setText(servico.getDescricao());
        precoField.setText(String.valueOf(servico.getPreco()));
        duracaoField.setText(String.valueOf(servico.getDuracao()));
        tipoFuncionarioCombo.setSelectedItem(servico.getTipoFuncionario());
        usaElevadorCheck.setSelected(servico.usaElevador());
        tipoElevadorCombo.setSelectedItem(servico.getTipoElevador());
        tipoElevadorCombo.setEnabled(servico.usaElevador());

        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Serviço");
    }

    private void excluirServico() {
        if (!hasSelection()) {
            showMessage("Selecione um serviço para excluir.");
            return;
        }

        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0);
        if (value == null) {
            showError("ID do serviço é nulo.");
            return;
        }

        if (!confirmAction("Tem certeza que deseja excluir este serviço?")) {
            return;
        }

        try {
            UUID servicoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
            boolean removido = context.servicoCrud.removerPorId(servicoId.toString());
            if (removido) {
                showMessage("Serviço excluído com sucesso!");
            } else {
                showError("Erro ao excluir serviço.");
            }
        } catch (Exception e) {
            showError("Erro ao excluir serviço: " + e.getMessage());
        }
        loadData();
    }

    @Override
    protected void setupForm() {
        nomeField = new JTextField(25);
        descricaoField = new JTextField(40);
        precoField = new JTextField(15);
        duracaoField = new JTextField(10);
        tipoFuncionarioCombo = new javax.swing.JComboBox<>(TipoFuncionario.values());
        usaElevadorCheck = new javax.swing.JCheckBox("Usa elevador?");
        tipoElevadorCombo = new javax.swing.JComboBox<>(TipoElevador.values());
        tipoElevadorCombo.setEnabled(false);
        usaElevadorCheck.addActionListener(e -> tipoElevadorCombo.setEnabled(usaElevadorCheck.isSelected()));

        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "Descrição", descricaoField, 1);
        addFormField(formPanel, "Preço", precoField, 2);
        addFormField(formPanel, "Duração (min)", duracaoField, 3);
        addFormField(formPanel, "Tipo de Mecânico", tipoFuncionarioCombo, 4);
        addFormField(formPanel, "Usa Elevador", usaElevadorCheck, 5);
        addFormField(formPanel, "Tipo de Elevador", tipoElevadorCombo, 6);
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Servico> servicos = context.servicoCrud.listarTodos();
            for (Servico servico : servicos) {
                Object[] row = {
                    servico.getId(),
                    servico.getTipo(),
                    servico.getDescricao(),
                    String.format("R$ %.2f", servico.getPreco()),
                    servico.getDuracao(),
                    servico.getTipoFuncionario() != null ? servico.getTipoFuncionario().name() : "",
                    servico.usaElevador() ? "Sim" : "Não",
                    servico.getTipoElevador() != null ? servico.getTipoElevador().name() : ""
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Erro ao carregar serviços: " + e.getMessage());
        }
    }

    private void buscarServico() {
        String termo = buscaField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        try {
            List<Servico> servicos = context.servicoCrud.listarTodos();
            for (Servico servico : servicos) {
                boolean nomeMatch = servico.getTipo().toLowerCase().contains(termo);
                boolean descricaoMatch = servico.getDescricao().toLowerCase().contains(termo);
                boolean precoMatch = String.valueOf(servico.getPreco()).contains(termo);
                boolean duracaoMatch = String.valueOf(servico.getDuracao()).contains(termo);
                boolean tipoFuncionarioMatch = servico.getTipoFuncionario() != null && servico.getTipoFuncionario().name().toLowerCase().contains(termo);
                boolean usaElevadorMatch = (servico.usaElevador() ? "sim" : "não").contains(termo);
                boolean tipoElevadorMatch = servico.getTipoElevador() != null && servico.getTipoElevador().name().toLowerCase().contains(termo);
                if (termo.isEmpty() || nomeMatch || descricaoMatch || precoMatch || duracaoMatch || tipoFuncionarioMatch || usaElevadorMatch || tipoElevadorMatch) {
                    Object[] row = {
                        servico.getId(),
                        servico.getTipo(),
                        servico.getDescricao(),
                        String.format("R$ %.2f", servico.getPreco()),
                        servico.getDuracao(),
                        servico.getTipoFuncionario() != null ? servico.getTipoFuncionario().name() : "",
                        servico.usaElevador() ? "Sim" : "Não",
                        servico.getTipoElevador() != null ? servico.getTipoElevador().name() : ""
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            showError("Erro ao buscar serviços: " + e.getMessage());
        }
    }

    @Override
    protected boolean saveFormData() {
        String nome = nomeField.getText().trim();
        String descricao = descricaoField.getText().trim();
        String precoStr = precoField.getText().trim().replace(",", ".");
        String duracaoStr = duracaoField.getText().trim();
        TipoFuncionario tipoFuncionario = (TipoFuncionario) tipoFuncionarioCombo.getSelectedItem();
        Boolean usaElevador = usaElevadorCheck.isSelected();
        TipoElevador tipoElevador = usaElevador ? (TipoElevador) tipoElevadorCombo.getSelectedItem() : null;

        if (nome.isEmpty() || precoStr.isEmpty() || descricao.isEmpty() || duracaoStr.isEmpty() || tipoFuncionario == null) {
            showError("Preencha todos os campos obrigatórios.");
            return false;
        }

        Float preco;
        Integer duracao;
        try {
            preco = Float.valueOf(precoStr);
            duracao = Integer.valueOf(duracaoStr);
        } catch (NumberFormatException e) {
            showError("Preço ou duração inválidos.");
            return false;
        }

        try {
            if (servicoEditandoId != null) {
                context.servicoCrud.atualizar(servicoEditandoId.toString(), true, nome, preco, descricao, duracao, tipoFuncionario, tipoElevador, usaElevador);
                showMessage("Serviço atualizado com sucesso!");
            } else {
                context.servicoCrud.criar(true, nome, preco, descricao, duracao, tipoFuncionario, tipoElevador, usaElevador);
                showMessage("Serviço cadastrado com sucesso!");
            }

            servicoEditandoId = null;
            clearForm();
            loadData();
            return true;
        } catch (Exception e) {
            showError("Erro ao salvar serviço: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
        duracaoField.setText("");
        tipoFuncionarioCombo.setSelectedIndex(0);
        usaElevadorCheck.setSelected(false);
        tipoElevadorCombo.setSelectedIndex(0);
        tipoElevadorCombo.setEnabled(false);
    }
}
