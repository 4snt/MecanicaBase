package mecanicabase.view.swing.panels;

import java.util.List;
import java.util.UUID;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.usuarios.Cliente;

public class ClientePanel extends BasePanel {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JTextField enderecoField;
    private JTextField buscaField;
    private UUID clienteEditandoId = null;

    public ClientePanel(ApplicationContext context) {
        super(context, "Gestão de Clientes");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "CPF", "Telefone", "Endereço", "Email"};
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
        buttonPanel.add(createButton("Novo Cliente", this::novoCliente));
        buttonPanel.add(createButton("Editar", this::editarCliente));
        buttonPanel.add(createButton("Excluir", this::excluirCliente));
        buttonPanel.add(createButton("Atualizar", this::loadData));
        buscaField = new JTextField(15);
        buttonPanel.add(buscaField);
        buttonPanel.add(createButton("Buscar", this::buscarCliente));
    }

    @Override
    protected void setupForm() {
        nomeField = new JTextField(20);
        cpfField = new JTextField(15);
        telefoneField = new JTextField(15);
        emailField = new JTextField(20);
        enderecoField = new JTextField(30);

        addFormField(formPanel, "Nome", nomeField, 0);
        addFormField(formPanel, "CPF", cpfField, 1);
        addFormField(formPanel, "Telefone", telefoneField, 2);
        addFormField(formPanel, "Email", emailField, 3);
        addFormField(formPanel, "Endereço", enderecoField, 4);
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Cliente> clientes = context.clienteCrud.listarTodos();
            for (Cliente cliente : clientes) {
                Object[] row = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEndereco(),
                    cliente.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    private void novoCliente() {
        clearForm();
        clienteEditandoId = null;
        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Novo Cliente");
    }

    private void editarCliente() {
        if (!hasSelection()) {
            showMessage("Selecione um cliente para editar.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0); // ID agora está na coluna 0
        clienteEditandoId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());

        String nome = (String) tableModel.getValueAt(selectedRow, 1);
        String cpf = (String) tableModel.getValueAt(selectedRow, 2);
        String telefone = (String) tableModel.getValueAt(selectedRow, 3);
        String endereco = (String) tableModel.getValueAt(selectedRow, 4);
        String email = (String) tableModel.getValueAt(selectedRow, 5);

        nomeField.setText(nome);
        cpfField.setText(cpf);
        telefoneField.setText(telefone);
        enderecoField.setText(endereco);
        emailField.setText(email);

        showFormDialog((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), "Editar Cliente");
    }

    private void excluirCliente() {
        if (!hasSelection()) {
            showMessage("Selecione um cliente para excluir.");
            return;
        }
        int selectedRow = getSelectedRowIndex();
        Object value = tableModel.getValueAt(selectedRow, 0); // ID agora está na coluna 0
        try {
            if (value != null) {
                UUID clienteId = value instanceof UUID ? (UUID) value : UUID.fromString(value.toString());
                boolean removido = context.clienteCrud.removerPorId(clienteId.toString());
                if (removido) {
                    showMessage("Cliente excluído com sucesso!");
                } else {
                    showError("Cliente não encontrado para exclusão.");
                }
            } else {
                showError("ID do cliente é nulo.");
            }
        } catch (Exception e) {
            showError("Erro ao excluir cliente: " + e.getMessage());
            e.printStackTrace();
        }
        loadData();
    }

    @Override
    protected boolean saveFormData() {
        try {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String email = emailField.getText().trim();
            String endereco = enderecoField.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty()) {
                showError("Nome e CPF são obrigatórios.");
                return false;
            }
            if (!cpf.matches("\\d{11}")) {
                showError("CPF inválido. Digite apenas números (11 dígitos).");
                return false;
            }
            if (!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                showError("E-mail inválido.");
                return false;
            }
            if (!telefone.isEmpty() && !telefone.matches("^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$")) {
                showError("Telefone inválido.");
                return false;
            }

            if (clienteEditandoId != null) {
                context.clienteCrud.atualizar(clienteEditandoId.toString(), true, nome, telefone, endereco, email, cpf);
                showMessage("Cliente atualizado com sucesso!");
            } else {
                context.clienteCrud.criar(true, nome, telefone, endereco, email, cpf);
                showMessage("Cliente criado com sucesso!");
            }

            clienteEditandoId = null;
            clearForm();
            loadData();
            return true;

        } catch (Exception e) {
            showError("Erro ao salvar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void clearForm() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        emailField.setText("");
        enderecoField.setText("");
    }

    private void buscarCliente() {
        String termo = buscaField.getText().trim();
        tableModel.setRowCount(0);
        try {
            List<Cliente> clientes = termo.isEmpty()
                    ? context.clienteCrud.listarTodos()
                    : context.clienteCrud.buscarPorFiltro(termo);
            for (Cliente cliente : clientes) {
                Object[] row = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEndereco(),
                    cliente.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Erro ao buscar clientes: " + e.getMessage());
        }
    }
}
