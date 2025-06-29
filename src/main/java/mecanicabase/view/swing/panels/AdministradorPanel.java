package mecanicabase.view.swing.panels;

import java.util.UUID;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Painel Swing para gerenciamento de administradores.
 */
// ... imports continuam iguais
public class AdministradorPanel extends BasePanel {

    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField buscaField;
    private UUID adminEditandoId = null;

    public AdministradorPanel(ApplicationContext context) {
        super(context, "Gestão de Administradores");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "CPF", "Email", "Telefone", "Objeto"};
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

        // Oculta a coluna do objeto
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Novo Administrador", this::novoAdministrador));
        buttonPanel.add(createButton("Editar", this::editarAdministrador));
        buttonPanel.add(createButton("Desativar", this::desativarAdministrador));
        buttonPanel.add(createButton("Trocar Senha", this::trocarSenhaAdministrador));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarAdministrador));
    }

    private mecanicabase.model.usuarios.Administrador getAdministradorFromRow(int row) {
        Object value = tableModel.getValueAt(row, 5);
        if (value instanceof mecanicabase.model.usuarios.Administrador admin) {
            return admin;
        }
        return null;
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
        java.awt.Color fg = java.awt.Color.WHITE;
        java.awt.Color bg = new java.awt.Color(60, 63, 65);
        for (var field : new JTextField[]{nomeField, emailField, cpfField, telefoneField, enderecoField}) {
            field.setForeground(fg);
            field.setBackground(bg);
        }
        senhaField.setForeground(fg);
        senhaField.setBackground(bg);
        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "Email", emailField, 1);
        addFormField(formPanel, "Senha", senhaField, 2);
        addFormField(formPanel, "CPF", cpfField, 3);
        addFormField(formPanel, "Telefone", telefoneField, 4);
        addFormField(formPanel, "Endereço", enderecoField, 5);
    }

    private void novoAdministrador() {
        clearForm();
        setupForm();
        javax.swing.JFrame parent = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        showFormDialog(parent, "Novo Administrador");
    }

    private void editarAdministrador() {
        if (!hasSelection()) {
            showMessage("Selecione um administrador para editar.");
            return;
        }
        var admin = getAdministradorFromRow(getSelectedRowIndex());
        if (admin == null) {
            showError("Administrador não encontrado.");
            return;
        }
        adminEditandoId = admin.getId();
        nomeField.setText(admin.getNome());
        emailField.setText(admin.getEmail());
        senhaField.setText(""); // senha não visível
        cpfField.setText(""); // cpf criptografado
        telefoneField.setText(admin.getTelefone());
        enderecoField.setText(admin.getEndereco());
        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Administrador");
    }

    private void desativarAdministrador() {
        if (!hasSelection()) {
            showMessage("Selecione um administrador para desativar.");
            return;
        }
        var admin = getAdministradorFromRow(getSelectedRowIndex());
        if (admin == null) {
            showError("Administrador não encontrado.");
            return;
        }
        if (!confirmAction("Tem certeza que deseja desativar este administrador?")) {
            return;
        }
        try {
            boolean removido = context.administradorCrud.removerPorId(admin.getId().toString());
            if (removido) {
                showMessage("Administrador desativado com sucesso!");
            } else {
                showError("Administrador não encontrado para desativação.");
            }
        } catch (Exception e) {
            showError("Erro ao desativar administrador: " + e.getMessage());
        }
        loadData();
    }

    private void buscarAdministrador() {
        String termo = buscaField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        try {
            var admins = context.administradorCrud.listarTodos().stream()
                    .filter(a -> a.getNome().toLowerCase().contains(termo)
                    || a.getEmail().toLowerCase().contains(termo)
                    || a.getTelefone().toLowerCase().contains(termo))
                    .toList();
            for (var admin : admins) {
                tableModel.addRow(new Object[]{
                    admin.getId(),
                    admin.getNome() != null ? admin.getNome() : "N/A",
                    admin.getCpf() != null ? "***" : "N/A",
                    admin.getEmail() != null ? admin.getEmail() : "N/A",
                    admin.getTelefone() != null ? admin.getTelefone() : "N/A",
                    admin // coluna oculta
                });
            }
        } catch (Exception e) {
            showError("Erro ao buscar administradores: " + e.getMessage());
        }
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
            context.administradorCrud.listarTodos().forEach(admin -> {
                tableModel.addRow(new Object[]{
                    admin.getId(),
                    admin.getNome() != null ? admin.getNome() : "N/A",
                    admin.getCpf() != null ? "***" : "N/A",
                    admin.getEmail() != null ? admin.getEmail() : "N/A",
                    admin.getTelefone() != null ? admin.getTelefone() : "N/A",
                    admin
                });
            });
        } catch (Exception e) {
            showError("Erro ao carregar administradores: " + e.getMessage());
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
        if (nome.isEmpty() || email.isEmpty() || (adminEditandoId == null && senha.isEmpty()) || telefone.isEmpty() || endereco.isEmpty()) {
            showError("Preencha todos os campos obrigatórios.");
            return false;
        }
        try {
            if (adminEditandoId != null) {
                context.administradorCrud.atualizar(adminEditandoId.toString(), true, nome, senha.isEmpty() ? null : senha, telefone, endereco);
                showMessage("Administrador atualizado com sucesso!");
            } else {
                if (cpf.isEmpty()) {
                    showError("CPF é obrigatório para novo administrador.");
                    return false;
                }
                context.administradorCrud.criar(true, nome, email, senha, cpf, telefone, endereco);
                showMessage("Administrador cadastrado com sucesso!");
            }
            adminEditandoId = null;
            clearForm();
            loadData();
            return true;
        } catch (Exception e) {
            showError("Erro ao salvar administrador: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        for (var field : new JTextField[]{nomeField, emailField, cpfField, telefoneField, enderecoField}) {
            if (field != null) {
                field.setText("");
            }
        }
        if (senhaField != null) {
            senhaField.setText("");
        }
    }

    private void trocarSenhaAdministrador() {
        if (!hasSelection()) {
            showMessage("Selecione um administrador para trocar a senha.");
            return;
        }
        var admin = getAdministradorFromRow(getSelectedRowIndex());
        if (admin == null) {
            showError("Administrador não encontrado.");
            return;
        }
        JPasswordField senhaAntigaField = new JPasswordField(20);
        JPasswordField novaSenhaField = new JPasswordField(20);
        Object[] message = {
            "Senha atual:", senhaAntigaField,
            "Nova senha:", novaSenhaField
        };
        int option = javax.swing.JOptionPane.showConfirmDialog(this, message, "Trocar Senha", javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (option == javax.swing.JOptionPane.OK_OPTION) {
            String senhaAntiga = new String(senhaAntigaField.getPassword());
            String novaSenha = new String(novaSenhaField.getPassword());
            if (senhaAntiga.isEmpty() || novaSenha.isEmpty()) {
                showError("Preencha ambos os campos de senha.");
                return;
            }
            boolean sucesso = context.administradorCrud.trocarSenha(admin, senhaAntiga, novaSenha);
            if (sucesso) {
                showMessage("Senha alterada com sucesso!");
            } else {
                showError("Senha atual incorreta.");
            }
        }
    }
}
