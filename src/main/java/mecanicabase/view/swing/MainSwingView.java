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
import mecanicabase.infra.auth.Session;
import mecanicabase.infra.db.Database;
import mecanicabase.model.usuarios.Administrador;
import mecanicabase.view.swing.panels.AdministradorPanel;
import mecanicabase.view.swing.panels.AgendamentoPanel;
import mecanicabase.view.swing.panels.ClientePanel;
import mecanicabase.view.swing.panels.ColaboradorPanel;
import mecanicabase.view.swing.panels.FinanceiroPanel;
import mecanicabase.view.swing.panels.OrdemServicoPanel;
import mecanicabase.view.swing.panels.PecaPanel;
import mecanicabase.view.swing.panels.ServicoPanel;
import mecanicabase.view.swing.panels.VeiculoPanel;

/**
 * Classe principal da interface Swing que substitui o JavaFX. Gerencia o menu
 * principal e a navegação entre os diferentes módulos.
 */
public class MainSwingView extends JFrame {

    private final ApplicationContext context;
    private JPanel cardsPanel;
    private CardLayout cardLayout;
    private JPanel sidebarPanel;

    public static final String CARD_MENU = "menu";
    public static final String CARD_CLIENTES = "clientes";
    public static final String CARD_SERVICOS = "servicos";
    public static final String CARD_AGENDAMENTOS = "agendamentos";
    public static final String CARD_ORDENS = "ordens";
    public static final String CARD_COLABORADORES = "colaboradores";
    public static final String CARD_FINANCEIRO = "financeiro";

    private ServicoPanel servicoPanel;
    private AgendamentoPanel agendamentoPanel;
    private ColaboradorPanel colaboradorPanel;
    private FinanceiroPanel financeiroPanel;
    private AdministradorPanel administradorPanel;
    private OrdemServicoPanel ordemServicoPanel = null;

    private boolean oficinaSubmenuVisivel = false;
    private JPanel oficinaSubmenuPanel;
    private boolean colaboradoresSubmenuVisivel = false;
    private JPanel colaboradoresSubmenuPanel;

    public MainSwingView(ApplicationContext context) {
        super();
        this.context = context;
        setTitle("Sistema Oficina Mecânica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1100, 700);
        setLocationRelativeTo(null);

        if (!LoginDialog.showLogin(this, context)) {
            System.exit(0);
        }

        initializeComponents();
        setupLayout();
        configureWindow();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Database.save();
            }
        });
    }

    private void initializeComponents() {
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        cardsPanel.add(new ClientePanel(context), CARD_CLIENTES);
        servicoPanel = new ServicoPanel(context);
        cardsPanel.add(servicoPanel, CARD_SERVICOS);
        agendamentoPanel = new AgendamentoPanel(context);
        cardsPanel.add(agendamentoPanel, CARD_AGENDAMENTOS);
        colaboradorPanel = new ColaboradorPanel(context);
        cardsPanel.add(colaboradorPanel, CARD_COLABORADORES);
        financeiroPanel = new FinanceiroPanel(context);
        cardsPanel.add(financeiroPanel, CARD_FINANCEIRO);
        cardsPanel.add(new VeiculoPanel(context), "veiculos");
        cardsPanel.add(new PecaPanel(context), "pecas");
        administradorPanel = new AdministradorPanel(context);
        cardsPanel.add(administradorPanel, "administradores");
        cardsPanel.add(criarDashboard(), CARD_MENU);

        boolean isAdmin = Session.getPessoaLogado() instanceof Administrador;
        sidebarPanel = criarSidebar(isAdmin);

        add(sidebarPanel, BorderLayout.WEST);
        add(cardsPanel, BorderLayout.CENTER);
    }

    /**
     * Cria a barra lateral com os botões de navegação visíveis conforme o tipo
     * de usuário.
     */
    private JPanel criarSidebar(boolean isAdmin) {
        JPanel sidebar = new JPanel(new GridBagLayout());
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
        sidebar.add(createSidebarButton("Oficina", this::toggleOficinaSubmenu), gbc);
        gbc.gridy = ++row;
        oficinaSubmenuPanel = criarOficinaSubmenuPanel();
        oficinaSubmenuPanel.setVisible(false);
        sidebar.add(oficinaSubmenuPanel, gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Serviços", this::abrirServicos), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Agendamentos", this::abrirAgendamentos), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Ordens de Serviço", this::abrirOrdens), gbc);

        if (isAdmin) {
            gbc.gridy = ++row;
            sidebar.add(createSidebarButton("Colaboradores", this::toggleColaboradoresSubmenu), gbc);
            gbc.gridy = ++row;
            colaboradoresSubmenuPanel = criarColaboradoresSubmenuPanel();
            colaboradoresSubmenuPanel.setVisible(false);
            sidebar.add(colaboradoresSubmenuPanel, gbc);
            gbc.gridy = ++row;
            sidebar.add(createSidebarButton("Financeiro", this::abrirFinanceiro), gbc);
        }

        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Sair", this::sair), gbc);
        return sidebar;
    }

    private JPanel criarOficinaSubmenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new java.awt.Color(50, 50, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 30, 2, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        int row = 0;
        panel.add(createSidebarButton("Veículos", this::abrirVeiculos), gbc);
        gbc.gridy = ++row;
        panel.add(createSidebarButton("Peças", this::abrirPecas), gbc);
        return panel;
    }

    private JPanel criarColaboradoresSubmenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new java.awt.Color(50, 50, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 30, 2, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        int row = 0;
        panel.add(createSidebarButton("Funcionários", this::abrirColaboradoresFuncionarios), gbc);
        gbc.gridy = ++row;
        panel.add(createSidebarButton("Administradores", this::abrirColaboradoresAdministradores), gbc);
        return panel;
    }

    private void toggleOficinaSubmenu() {
        oficinaSubmenuVisivel = !oficinaSubmenuVisivel;
        oficinaSubmenuPanel.setVisible(oficinaSubmenuVisivel);
        sidebarPanel.revalidate();
        sidebarPanel.repaint();
    }

    private void toggleColaboradoresSubmenu() {
        colaboradoresSubmenuVisivel = !colaboradoresSubmenuVisivel;
        colaboradoresSubmenuPanel.setVisible(colaboradoresSubmenuVisivel);
        sidebarPanel.revalidate();
        sidebarPanel.repaint();
    }

    private void abrirVeiculos() {
        for (java.awt.Component comp : cardsPanel.getComponents()) {
            if (comp instanceof VeiculoPanel vp) {
                vp.loadData();
                break;
            }
        }
        cardLayout.show(cardsPanel, "veiculos");
    }

    private void abrirPecas() {
        for (java.awt.Component comp : cardsPanel.getComponents()) {
            if (comp instanceof PecaPanel pp) {
                pp.loadData();
                break;
            }
        }
        cardLayout.show(cardsPanel, "pecas");
    }

    private JPanel criarDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bem-vindo ao Sistema de Gestão da Oficina!", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(24f));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void abrirClientes() {
        for (java.awt.Component comp : cardsPanel.getComponents()) {
            if (comp instanceof ClientePanel cp) {
                cp.loadData();
                break;
            }
        }
        cardLayout.show(cardsPanel, CARD_CLIENTES);
    }

    private void abrirServicos() {
        if (servicoPanel != null) {
            servicoPanel.loadData();
        }
        cardLayout.show(cardsPanel, CARD_SERVICOS);
    }

    private void abrirAgendamentos() {
        if (agendamentoPanel != null) {
            agendamentoPanel.loadData();
        }
        cardLayout.show(cardsPanel, CARD_AGENDAMENTOS);
    }

    private void abrirFinanceiro() {
        if (financeiroPanel != null) {
            financeiroPanel.loadData();
        }
        cardLayout.show(cardsPanel, CARD_FINANCEIRO);
    }

    private void abrirColaboradoresFuncionarios() {
        if (colaboradorPanel != null) {
            colaboradorPanel.loadData();
        }
        cardLayout.show(cardsPanel, CARD_COLABORADORES);
    }

    private void abrirColaboradoresAdministradores() {
        if (administradorPanel != null) {
            administradorPanel.loadData();
        }
        cardLayout.show(cardsPanel, "administradores");
    }

    private void abrirOrdens() {
        if (ordemServicoPanel == null) {
            ordemServicoPanel = new OrdemServicoPanel(context, context.ordemCrud, context.pecaCrud);
            cardsPanel.add(ordemServicoPanel, CARD_ORDENS);
        } else {
            ordemServicoPanel.loadData();
        }
        cardLayout.show(cardsPanel, CARD_ORDENS);
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
}
