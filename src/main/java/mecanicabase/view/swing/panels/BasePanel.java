package mecanicabase.view.swing.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;

/**
 * Classe base para todos os painéis da interface Swing.
 * Fornece funcionalidades comuns como layout padrão, tabelas e botões.
 */
public abstract class BasePanel extends JFrame {

    protected final ApplicationContext context;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected JPanel buttonPanel;
    protected JPanel formPanel;

    public BasePanel(ApplicationContext context, String title) {
        this.context = context;
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        initializeComponents();
        setupLayout();
        loadData();
        
        pack();
        setLocationRelativeTo(null);
    }

    protected void initializeComponents() {
        // Título
        JLabel titleLabel = new JLabel(getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel, BorderLayout.NORTH);

        // Tabela
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        buttonPanel = new JPanel(new FlowLayout());
        setupButtons();
        add(buttonPanel, BorderLayout.SOUTH);

        // Painel de formulário (inicialmente oculto)
        formPanel = new JPanel(new GridBagLayout());
        setupForm();
    }

    protected void setupLayout() {
        setSize(800, 600);
    }

    protected abstract void setupTable();
    protected abstract void setupButtons();
    protected abstract void setupForm();
    protected abstract void loadData();

    protected JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected boolean confirmAction(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmar", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    protected void showFormDialog(String title) {
        javax.swing.JDialog formFrame = new javax.swing.JDialog(this, title, true);
        formFrame.setLayout(new BorderLayout());
        formFrame.add(formPanel, BorderLayout.CENTER);
        
        JPanel formButtonPanel = new JPanel(new FlowLayout());
        formButtonPanel.add(createButton("Salvar", () -> {
            if (saveFormData()) {
                formFrame.dispose();
                loadData();
            }
        }));
        formButtonPanel.add(createButton("Cancelar", formFrame::dispose));
        
        formFrame.add(formButtonPanel, BorderLayout.SOUTH);
        formFrame.pack();
        formFrame.setLocationRelativeTo(this);
        formFrame.setVisible(true);
    }

    protected abstract boolean saveFormData();

    protected void clearForm() {
        // Implementação padrão vazia - subclasses podem sobrescrever
    }

    protected int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    protected boolean hasSelection() {
        return getSelectedRowIndex() >= 0;
    }

    protected void addFormField(JPanel panel, String labelText, 
                               java.awt.Component field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText + ":"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }
}
