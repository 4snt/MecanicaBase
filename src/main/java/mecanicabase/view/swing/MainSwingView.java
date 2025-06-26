package mecanicabase.view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.view.swing.panels.AgendamentoPanel;
import mecanicabase.view.swing.panels.ClientePanel;
import mecanicabase.view.swing.panels.ColaboradorPanel;
import mecanicabase.view.swing.panels.FinanceiroPanel;
import mecanicabase.view.swing.panels.OficinaPanel;
import mecanicabase.view.swing.panels.OrdemServicoPanel;
import mecanicabase.view.swing.panels.ServicoPanel;

/**
 * Classe principal da interface Swing que substitui o JavaFX. Gerencia o menu
 * principal e a navegação entre os diferentes módulos.
 */
public class MainSwingView extends JFrame {

    private final ApplicationContext context;
    private JPanel adminButtonsPanel;

    public MainSwingView(ApplicationContext context) {
        this.context = context;

        // Mostrar login primeiro
        if (!LoginDialog.showLogin(null, context)) {
            System.exit(0);
            return;
        }

        initializeComponents();
        setupLayout();
        configureWindow();
    }

    private void initializeComponents() {
        setTitle("Sistema Oficina Mecânica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título principal
        JLabel titleLabel = new JLabel("Sistema de Gestão - Oficina Mecânica", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        add(titleLabel, BorderLayout.NORTH);

        // Painel principal com botões
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botões principais (sempre visíveis)
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createButton("Clientes", this::abrirClientes), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(createButton("Oficina", this::abrirOficina), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createButton("Serviços", this::abrirServicos), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(createButton("Agendamentos", this::abrirAgendamentos), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(createButton("Ordens de Serviço", this::abrirOrdens), gbc);

        // Painel para botões administrativos
        adminButtonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints adminGbc = new GridBagConstraints();
        adminGbc.insets = new Insets(5, 10, 5, 10);
        adminGbc.fill = GridBagConstraints.HORIZONTAL;

        adminGbc.gridx = 0;
        adminGbc.gridy = 0;
        adminButtonsPanel.add(createButton("Colaboradores", this::abrirColaboradores), adminGbc);

        adminGbc.gridx = 1;
        adminGbc.gridy = 0;
        adminButtonsPanel.add(createButton("Financeiro", this::abrirFinanceiro), adminGbc);

        // Verificar se usuário é admin
        boolean isAdmin = Session.getPessoaLogado() instanceof Administrador;
        adminButtonsPanel.setVisible(isAdmin);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(adminButtonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Botão sair
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton("Sair", this::sair));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setPreferredSize(new java.awt.Dimension(200, 40));
        button.addActionListener(e -> action.run());
        return button;
    }

    private void setupLayout() {
        pack();
        setLocationRelativeTo(null);
    }

    private void configureWindow() {
        // Uses default look and feel - no need to change
    }

    private void abrirClientes() {
        new ClientePanel(context).setVisible(true);
    }

    private void abrirOficina() {
        new OficinaPanel(context).setVisible(true);
    }

    private void abrirServicos() {
        new ServicoPanel(context).setVisible(true);
    }

    private void abrirAgendamentos() {
        new AgendamentoPanel(context).setVisible(true);
    }

    private void abrirOrdens() {
        new OrdemServicoPanel(context).setVisible(true);
    }

    private void abrirColaboradores() {
        new ColaboradorPanel(context).setVisible(true);
    }

    private void abrirFinanceiro() {
        new FinanceiroPanel(context).setVisible(true);
    }

    private void sair() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Aqui você pode inicializar o ApplicationContext conforme necessário
            ApplicationContext context = new ApplicationContext();
            new MainSwingView(context).setVisible(true);
        });
    }
}
