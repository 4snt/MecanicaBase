package mecanicabase.view.swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mecanicabase.infra.ApplicationContext;
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
    private JPanel cardsPanel; // Painel central com CardLayout
    private CardLayout cardLayout;
    private JPanel sidebarPanel;

    public static final String CARD_MENU = "menu";
    public static final String CARD_CLIENTES = "clientes";
    public static final String CARD_OFICINA = "oficina";
    public static final String CARD_SERVICOS = "servicos";
    public static final String CARD_AGENDAMENTOS = "agendamentos";
    public static final String CARD_ORDENS = "ordens";
    public static final String CARD_COLABORADORES = "colaboradores";
    public static final String CARD_FINANCEIRO = "financeiro";

    private OficinaPanel oficinaPanel;
    private ServicoPanel servicoPanel;
    private AgendamentoPanel agendamentoPanel;
    private OrdemServicoPanel ordemServicoPanel;
    private ColaboradorPanel colaboradorPanel;
    private FinanceiroPanel financeiroPanel;

    public MainSwingView(ApplicationContext context) {
        super();
        this.context = context;
        setTitle("Sistema Oficina Mecânica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // Exige login antes de inicializar componentes
        if (!LoginDialog.showLogin(this, context)) {
            System.exit(0);
        }

        // Inicializa componentes após login bem-sucedido
        initializeComponents();
        setupLayout();
        configureWindow();
    }

    private void initializeComponents() {
        // Painel de cards (conteúdo principal)
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Painel de clientes
        ClientePanel clientePanel = new ClientePanel(context);
        cardsPanel.add(clientePanel, CARD_CLIENTES);

        // Painel de oficina
        oficinaPanel = new OficinaPanel(context);
        cardsPanel.add(oficinaPanel, CARD_OFICINA);

        // Painel de serviços
        servicoPanel = new ServicoPanel(context);
        cardsPanel.add(servicoPanel, CARD_SERVICOS);

        // Painel de agendamentos
        agendamentoPanel = new AgendamentoPanel(context);
        cardsPanel.add(agendamentoPanel, CARD_AGENDAMENTOS);

        // Painel de ordens de serviço
        ordemServicoPanel = new OrdemServicoPanel(context);
        cardsPanel.add(ordemServicoPanel, CARD_ORDENS);

        // Painel de colaboradores
        colaboradorPanel = new ColaboradorPanel(context);
        cardsPanel.add(colaboradorPanel, CARD_COLABORADORES);

        // Painel de financeiro
        financeiroPanel = new FinanceiroPanel(context);
        cardsPanel.add(financeiroPanel, CARD_FINANCEIRO);

        // Painel do menu principal (dashboard)
        JPanel mainPanel = criarDashboard();
        cardsPanel.add(mainPanel, CARD_MENU);

        // Barra lateral
        sidebarPanel = criarSidebar();

        add(sidebarPanel, BorderLayout.WEST);
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridBagLayout());
        sidebar.setBackground(new java.awt.Color(40, 40, 50));
        sidebar.setPreferredSize(new java.awt.Dimension(220, 700));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;
        sidebar.add(createSidebarButton("Dashboard", this::voltarMenu), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Clientes", this::abrirClientes), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Oficina", this::abrirOficina), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Serviços", this::abrirServicos), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Agendamentos", this::abrirAgendamentos), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Ordens de Serviço", this::abrirOrdens), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Colaboradores", this::abrirColaboradores), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Financeiro", this::abrirFinanceiro), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Sair", this::sair), gbc);

        return sidebar;
    }

    private JButton createSidebarButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new java.awt.Color(60, 63, 65));
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(button.getFont().deriveFont(16f));
        button.setPreferredSize(new java.awt.Dimension(180, 40));
        button.addActionListener(e -> action.run());
        return button;
    }

    private JPanel criarDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bem-vindo ao Sistema de Gestão da Oficina!", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(24f));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // Métodos de navegação
    private void abrirClientes() {
        cardLayout.show(cardsPanel, CARD_CLIENTES);
    }

    private void abrirOficina() {
        cardLayout.show(cardsPanel, CARD_OFICINA);
    }

    private void abrirServicos() {
        cardLayout.show(cardsPanel, CARD_SERVICOS);
    }

    private void abrirAgendamentos() {
        cardLayout.show(cardsPanel, CARD_AGENDAMENTOS);
    }

    private void abrirOrdens() {
        cardLayout.show(cardsPanel, CARD_ORDENS);
    }

    private void abrirColaboradores() {
        cardLayout.show(cardsPanel, CARD_COLABORADORES);
    }

    private void abrirFinanceiro() {
        cardLayout.show(cardsPanel, CARD_FINANCEIRO);
    }

    private void voltarMenu() {
        cardLayout.show(cardsPanel, CARD_MENU);
    }

    private void sair() {
        System.exit(0);
    }

    private void setupLayout() {
    }

    private void configureWindow() {
    }
}
