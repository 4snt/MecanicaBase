package mecanicabase.view.swing.panels;

import java.util.List;
import java.util.UUID;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.usuarios.Cliente;

/**
 * Painel Swing para gerenciamento de clientes.
 */
public class ClientePanel extends BasePanel {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField emailField;
    private JTextField enderecoField;

    public ClientePanel(ApplicationContext context) {
        super(context, "Gestão de Clientes");
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Nome", "CPF", "Telefone", "Email", "Endereço"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela somente leitura
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
    protected void loadData() {
        tableModel.setRowCount(0);
        
        try {
            List<Cliente> clientes = context.clienteCrud.listarTodos();
            for (Cliente cliente : clientes) {
                Object[] row = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail(),
                    cliente.getEndereco()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            showError("Erro ao carregar clientes: " + e.getMessage());
        }
    }

    private void novoCliente() {
        clearForm();
        showFormDialog("Novo Cliente");
    }

    private void editarCliente() {
        if (!hasSelection()) {
            showMessage("Selecione um cliente para editar.");
            return;
        }

        int selectedRow = getSelectedRowIndex();
        UUID clienteId = (UUID) tableModel.getValueAt(selectedRow, 0);
        
        try {
            Cliente cliente = context.clienteCrud.buscarPorId(clienteId);
            if (cliente != null) {
                preencherFormulario(cliente);
                showFormDialog("Editar Cliente");
            } else {
                showError("Cliente não encontrado.");
            }
        } catch (Exception e) {
            showError("Erro ao carregar cliente: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        if (!hasSelection()) {
            showMessage("Selecione um cliente para excluir.");
            return;
        }

        if (!confirmAction("Tem certeza que deseja excluir este cliente?")) {
            return;
        }

        int selectedRow = getSelectedRowIndex();
        UUID clienteId = (UUID) tableModel.getValueAt(selectedRow, 0);
        
        try {
            context.clienteCrud.removerPorId(clienteId.toString());
            showMessage("Cliente excluído com sucesso!");
            loadData();
        } catch (Exception e) {
            showError("Erro ao excluir cliente: " + e.getMessage());
        }
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

            // Use the CRUD API correctly - parameters: nome, endereco, telefone, email, cpf
            if (hasSelection()) {
                // For now, just show message - editing not implemented
                showMessage("Funcionalidade de edição em desenvolvimento.");
            } else {
                context.clienteCrud.criar(true, nome, endereco, telefone, email, cpf);
                showMessage("Cliente criado com sucesso!");
            }

            clearForm();
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

    private void preencherFormulario(Cliente cliente) {
        nomeField.setText(cliente.getNome());
        cpfField.setText(cliente.getCpf());
        telefoneField.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
        emailField.setText(cliente.getEmail() != null ? cliente.getEmail() : "");
        enderecoField.setText(cliente.getEndereco() != null ? cliente.getEndereco() : "");
    }
}
