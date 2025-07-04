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
 * Classe base para todos os painéis da interface Swing. Fornece funcionalidades
 * comuns como layout padrão, tabelas e botões.
 */
public abstract class BasePanel extends JPanel {

    /**
     * Contexto da aplicação
     */
    protected final ApplicationContext context;
    /**
     * Tabela exibida no painel
     */
    protected JTable table;
    /**
     * Modelo da tabela exibida no painel.
     */
    protected DefaultTableModel tableModel;
    /**
     * Painel de botões
     */
    protected JPanel buttonPanel;
    /**
     * Painel de formulário (inicialmente oculto)
     */
    protected JPanel formPanel;

    /**
     * Construtor do painel base.
     *
     * @param context Contexto da aplicação
     * @param title Título do painel
     */
    public BasePanel(ApplicationContext context, String title) {
        this.context = context;
        setLayout(new BorderLayout());
        setBackground(new java.awt.Color(40, 40, 50)); // Fundo escuro
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initializeComponents(title);
        setupLayout();
        // NÃO chamar loadData() aqui!
    }

    protected void initializeComponents(String title) {
        // Título
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        titleLabel.setForeground(java.awt.Color.WHITE); // Título branco
        add(titleLabel, BorderLayout.NORTH);

        // Tabela
        setupTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new java.awt.Color(40, 40, 50)); // Fundo da tabela
        table.setBackground(new java.awt.Color(60, 63, 65));
        table.setForeground(java.awt.Color.WHITE);
        table.setSelectionBackground(new java.awt.Color(80, 80, 100));
        table.setSelectionForeground(java.awt.Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new java.awt.Color(40, 40, 50));
        setupButtons();
        add(buttonPanel, BorderLayout.SOUTH);

        // Painel de formulário (inicialmente oculto)
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new java.awt.Color(40, 40, 50));
        setupForm();
    }

    /**
     * Configura o layout do painel.
     */
    protected void setupLayout() {
        // Pode ser customizado pelas subclasses
    }

    protected abstract void setupTable();

    protected abstract void setupButtons();

    protected abstract void setupForm();

    public abstract void loadData();

    /**
     * Cria um botão com ação associada.
     *
     * @param text Texto do botão
     * @param action Ação a ser executada
     * @return JButton criado
     */
    protected JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    /**
     * Exibe uma mensagem informativa para o usuário.
     *
     * @param message Mensagem a ser exibida
     */
    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Exibe uma mensagem de erro para o usuário.
     *
     * @param message Mensagem de erro
     */
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exibe uma caixa de confirmação para o usuário.
     *
     * @param message Mensagem a ser exibida
     * @return true se confirmado, false caso contrário
     */
    protected boolean confirmAction(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Exibe um diálogo de formulário.
     *
     * @param parent Janela pai
     * @param title Título do diálogo
     */
    protected void showFormDialog(JFrame parent, String title) {
        javax.swing.JDialog formFrame = new javax.swing.JDialog(parent, title, true);
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
        formFrame.setLocationRelativeTo(parent);
        formFrame.setVisible(true);
    }

    protected abstract boolean saveFormData();

    protected void clearForm() {
        // Implementação padrão vazia - subclasses podem sobrescrever
    }

    /**
     * Retorna o índice da linha selecionada na tabela.
     *
     * @return Índice da linha selecionada
     */
    protected int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    /**
     * Verifica se há uma linha selecionada na tabela.
     *
     * @return true se houver seleção, false caso contrário
     */
    protected boolean hasSelection() {
        return getSelectedRowIndex() >= 0;
    }

    /**
     * Adiciona um campo de formulário ao painel.
     *
     * @param panel Painel alvo
     * @param labelText Texto do rótulo
     * @param field Componente do campo
     */
    protected void addFormField(JPanel panel, String labelText,
            java.awt.Component field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(labelText + ":");
        label.setForeground(java.awt.Color.WHITE); // Corrige cor da label
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }
}
