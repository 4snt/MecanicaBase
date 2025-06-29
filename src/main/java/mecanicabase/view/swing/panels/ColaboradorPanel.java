package mecanicabase.view.swing.panels;

import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento de colaboradores (apenas para
 * administradores).
 */
// ... (importações continuam iguais)
public class ColaboradorPanel extends BasePanel {

    // --- Campos para funcionário ---
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField salarioField;
    private JComboBox<mecanicabase.model.usuarios.TipoFuncionario> funcaoCombo;
    private JTextField buscaField;
    private UUID colaboradorEditandoId = null;

    public ColaboradorPanel(ApplicationContext context) {
        super(context, "Gestão de Colaboradores");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "CPF", "Cargo", "Email", "Telefone", "Objeto"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(new java.awt.Color(60, 63, 65));
        table.setForeground(java.awt.Color.WHITE);
        table.setSelectionBackground(new java.awt.Color(80, 80, 100));
        table.setSelectionForeground(java.awt.Color.WHITE);

        // Oculta a última coluna (Objeto Funcionario)
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Novo Colaborador", this::novoColaborador));
        buttonPanel.add(createButton("Editar", this::editarColaborador));
        buttonPanel.add(createButton("Desativar", this::desativarColaborador));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarColaborador));
    }

    @Override
    protected void setupForm() {
        formPanel.removeAll();
        nomeField = new JTextField(20);
        emailField = new JTextField(20);
        senhaField = new JPasswordField(20);
        cpfField = new JTextField(14);
        telefoneField = new JTextField(15);
        enderecoField = new JTextField(30);
        salarioField = new JTextField(10);
        funcaoCombo = new JComboBox<>(mecanicabase.model.usuarios.TipoFuncionario.values());
        java.awt.Color fg = java.awt.Color.WHITE;
        java.awt.Color bg = new java.awt.Color(60, 63, 65);
        for (var field : new JTextField[]{nomeField, emailField, cpfField, telefoneField, enderecoField, salarioField}) {
            field.setForeground(fg);
            field.setBackground(bg);
        }
        senhaField.setForeground(fg);
        senhaField.setBackground(bg);
        funcaoCombo.setForeground(fg);
        funcaoCombo.setBackground(bg);

        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "Email", emailField, 1);
        addFormField(formPanel, "Senha", senhaField, 2);
        addFormField(formPanel, "CPF", cpfField, 3);
        addFormField(formPanel, "Telefone", telefoneField, 4);
        addFormField(formPanel, "Endereço", enderecoField, 5);
        addFormField(formPanel, "Salário", salarioField, 6);
        addFormField(formPanel, "Função", funcaoCombo, 7);
    }

    private void novoColaborador() {
        clearForm();
        setupForm();
        javax.swing.JFrame parent = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Novo Colaborador");
    }

    private int getCheckedRowIndex() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object checked = tableModel.getValueAt(i, 0);
            if (checked instanceof Boolean b && b) {
                return i;
            }
        }
        return -1;
    }

    private mecanicabase.model.usuarios.Funcionario getFuncionarioFromRow(int row) {
        Object value = tableModel.getValueAt(row, 7); // Coluna oculta
        if (value instanceof mecanicabase.model.usuarios.Funcionario f) {
            return f;
        }
        return null;
    }

    private void editarColaborador() {
        int row = getCheckedRowIndex();
        if (row == -1) {
            row = getSelectedRowIndex();
        }
        if (row == -1) {
            showMessage("Selecione um colaborador para editar.");
            return;
        }
        var f = getFuncionarioFromRow(row);
        if (f == null) {
            showError("Colaborador não encontrado.");
            return;
        }
        colaboradorEditandoId = f.getId();
        nomeField.setText(f.getNome());
        emailField.setText(f.getEmail());
        senhaField.setText("");
        cpfField.setText(""); // CPF não editável
        telefoneField.setText(f.getTelefone());
        enderecoField.setText(f.getEndereco());
        salarioField.setText(String.valueOf(f.getSalario()));
        funcaoCombo.setSelectedItem(f.getFuncao());
        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Colaborador");
    }

    private void desativarColaborador() {
        if (!hasSelection()) {
            showMessage("Selecione um colaborador para desativar.");
            return;
        }
        int row = getSelectedRowIndex();
        var f = getFuncionarioFromRow(row);
        if (f == null) {
            showError("Colaborador não encontrado.");
            return;
        }
        if (!confirmAction("Tem certeza que deseja desativar este colaborador?")) {
            return;
        }
        try {
            boolean removido = context.funcionarioCrud.removerPorId(f.getId().toString());
            if (removido) {
                showMessage("Colaborador desativado com sucesso!");
            } else {
                showError("Colaborador não encontrado para desativação.");
            }
        } catch (Exception e) {
            showError("Erro ao desativar colaborador: " + e.getMessage());
        }
        loadData();
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
            context.funcionarioCrud.listarTodos().forEach(funcionario -> {
                Object[] row = {
                    funcionario.getId(),
                    funcionario.getNome() != null ? funcionario.getNome() : "N/A",
                    funcionario.getCpf() != null ? "***" : "N/A",
                    funcionario.getFuncao() != null ? funcionario.getFuncao().name() : "N/A",
                    funcionario.getEmail() != null ? funcionario.getEmail() : "N/A",
                    funcionario.getTelefone() != null ? funcionario.getTelefone() : "N/A",
                    funcionario // coluna oculta
                };
                tableModel.addRow(row);
            });
        } catch (Exception e) {
            showError("Erro ao carregar colaboradores: " + e.getMessage());
        }
    }

    private void buscarColaborador() {
        String termo = buscaField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        try {
            var funcionarios = context.funcionarioCrud.listarTodos().stream()
                    .filter(f -> f.getNome().toLowerCase().contains(termo)
                    || f.getEmail().toLowerCase().contains(termo)
                    || (f.getFuncao() != null && f.getFuncao().name().toLowerCase().contains(termo))
                    || f.getTelefone().toLowerCase().contains(termo))
                    .toList();
            for (var funcionario : funcionarios) {
                tableModel.addRow(new Object[]{
                    funcionario.getId(),
                    funcionario.getNome() != null ? funcionario.getNome() : "N/A",
                    funcionario.getCpf() != null ? "***" : "N/A",
                    funcionario.getFuncao() != null ? funcionario.getFuncao().name() : "N/A",
                    funcionario.getEmail() != null ? funcionario.getEmail() : "N/A",
                    funcionario.getTelefone() != null ? funcionario.getTelefone() : "N/A",
                    funcionario
                });
            }
        } catch (Exception e) {
            showError("Erro ao buscar colaboradores: " + e.getMessage());
        }
    }

    @Override
    protected boolean saveFormData() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();
        String cpf = cpfField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String endereco = enderecoField.getText().trim();
        String salarioStr = salarioField.getText().trim().replace(",", ".");
        var funcao = (mecanicabase.model.usuarios.TipoFuncionario) funcaoCombo.getSelectedItem();

        if (nome.isEmpty() || email.isEmpty() || (colaboradorEditandoId == null && senha.isEmpty()) || telefone.isEmpty() || endereco.isEmpty() || salarioStr.isEmpty() || funcao == null) {
            showError("Preencha todos os campos obrigatórios.");
            return false;
        }

        float salario;
        try {
            salario = Float.parseFloat(salarioStr);
        } catch (NumberFormatException e) {
            showError("Salário inválido.");
            return false;
        }

        try {
            if (colaboradorEditandoId != null) {
                context.funcionarioCrud.atualizar(colaboradorEditandoId.toString(), true, nome, senha.isEmpty() ? null : senha, telefone, endereco, funcao, salario);
                showMessage("Colaborador atualizado com sucesso!");
            } else {
                if (cpf.isEmpty()) {
                    showError("CPF é obrigatório para novo colaborador.");
                    return false;
                }
                context.funcionarioCrud.criar(true, nome, funcao, email, senha, cpf, telefone, endereco, salario);
                showMessage("Colaborador cadastrado com sucesso!");
            }
            colaboradorEditandoId = null;
            clearForm();
            loadData();
            return true;
        } catch (Exception e) {
            showError("Erro ao salvar colaborador: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        for (var field : new JTextField[]{nomeField, emailField, cpfField, telefoneField, enderecoField, salarioField}) {
            if (field != null) {
                field.setText("");
            }
        }
        if (senhaField != null) {
            senhaField.setText("");
        }
        if (funcaoCombo != null && funcaoCombo.getItemCount() > 0) {
            funcaoCombo.setSelectedIndex(0);
        }
    }
}
