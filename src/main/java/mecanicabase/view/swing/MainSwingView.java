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
import mecanicabase.infra.db.Database;
import mecanicabase.view.swing.panels.AdministradorPanel;
import mecanicabase.view.swing.panels.AgendamentoPanel;
import mecanicabase.view.swing.panels.ClientePanel;
import mecanicabase.view.swing.panels.ColaboradorPanel;
import mecanicabase.view.swing.panels.FinanceiroPanel;
import mecanicabase.view.swing.panels.PecaPanel;
import mecanicabase.view.swing.panels.ServicoPanel;
import mecanicabase.view.swing.panels.VeiculoPanel;

/**
 * Classe principal da interface Swing que substitui o JavaFX. Gerencia o menu
 * principal e a navegação entre os diferentes módulos.
 */
public class MainSwingView extends JFrame {

    private final ApplicationContext context;
    private JPanel cardsPanel; // Painel central com CardLayout
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
    private mecanicabase.view.swing.panels.OrdemServicoPanel ordemServicoPanel = null;

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

        // Exige login antes de inicializar componentes
        if (!LoginDialog.showLogin(this, context)) {
            System.exit(0);
        }

        // Inicializa componentes após login bem-sucedido
        initializeComponents();
        setupLayout();
        configureWindow();

        // Adiciona listener para salvar dados ao fechar a janela (após inicialização)
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Database.save();
            }
        });
    }

    private void initializeComponents() {
        // Painel de cards (conteúdo principal)
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // Painel de clientes
        ClientePanel clientePanel = new ClientePanel(context);
        cardsPanel.add(clientePanel, CARD_CLIENTES);

        // Painel de serviços
        servicoPanel = new ServicoPanel(context);
        cardsPanel.add(servicoPanel, CARD_SERVICOS);

        // Painel de agendamentos (restaurado para AgendamentoPanel)
        agendamentoPanel = new AgendamentoPanel(context);
        cardsPanel.add(agendamentoPanel, CARD_AGENDAMENTOS);

        // Painel de colaboradores
        colaboradorPanel = new ColaboradorPanel(context);
        cardsPanel.add(colaboradorPanel, CARD_COLABORADORES);

        // Painel de financeiro
        financeiroPanel = new FinanceiroPanel(context);
        cardsPanel.add(financeiroPanel, CARD_FINANCEIRO);

        // Painel do menu principal (dashboard)
        JPanel mainPanel = criarDashboard();
        cardsPanel.add(mainPanel, CARD_MENU);

        // Painel de veículos
        VeiculoPanel veiculoPanel = new VeiculoPanel(context);
        cardsPanel.add(veiculoPanel, "veiculos");

        // Painel de peças
        PecaPanel pecaPanel = new PecaPanel(context);
        cardsPanel.add(pecaPanel, "pecas");

        // Painel de administradores
        administradorPanel = new AdministradorPanel(context);
        cardsPanel.add(administradorPanel, "administradores");
        // NÃO adicionar o painel de ordens de serviço aqui!

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
        // Botão Oficina com submenu
        JButton oficinaBtn = createSidebarButton("Oficina", this::toggleOficinaSubmenu);
        sidebar.add(oficinaBtn, gbc);
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
        gbc.gridy = ++row;
        // Botão Colaboradores com submenu
        JButton colaboradoresBtn = createSidebarButton("Colaboradores", this::toggleColaboradoresSubmenu);
        sidebar.add(colaboradoresBtn, gbc);
        gbc.gridy = ++row;
        colaboradoresSubmenuPanel = criarColaboradoresSubmenuPanel();
        colaboradoresSubmenuPanel.setVisible(false);
        sidebar.add(colaboradoresSubmenuPanel, gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Financeiro", this::abrirFinanceiro), gbc);
        gbc.gridy = ++row;
        sidebar.add(createSidebarButton("Sair", this::sair), gbc);

        return sidebar;
    }

    private JPanel criarOficinaSubmenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new java.awt.Color(50, 50, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 30, 2, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        int row = 0;
        JButton veiculosBtn = createSidebarButton("Veículos", this::abrirVeiculos);
        veiculosBtn.setFont(veiculosBtn.getFont().deriveFont(14f));
        panel.add(veiculosBtn, gbc);
        gbc.gridy = ++row;
        JButton pecasBtn = createSidebarButton("Peças", this::abrirPecas);
        pecasBtn.setFont(pecasBtn.getFont().deriveFont(14f));
        panel.add(pecasBtn, gbc);
        return panel;
    }

    private JPanel criarColaboradoresSubmenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new java.awt.Color(50, 50, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 30, 2, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        int row = 0;
        JButton funcionariosBtn = createSidebarButton("Funcionários", this::abrirColaboradoresFuncionarios);
        funcionariosBtn.setFont(funcionariosBtn.getFont().deriveFont(14f));
        panel.add(funcionariosBtn, gbc);
        gbc.gridy = ++row;
        JButton administradoresBtn = createSidebarButton("Administradores", this::abrirColaboradoresAdministradores);
        administradoresBtn.setFont(administradoresBtn.getFont().deriveFont(14f));
        panel.add(administradoresBtn, gbc);
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
            if (comp instanceof VeiculoPanel) {
                ((VeiculoPanel) comp).loadData();
                break;
            }
        }
        cardLayout.show(cardsPanel, "veiculos");
    }

    private void abrirPecas() {
        for (java.awt.Component comp : cardsPanel.getComponents()) {
            if (comp instanceof PecaPanel) {
                ((PecaPanel) comp).loadData();
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

    // Métodos de navegação
    private void abrirClientes() {
        if (cardsPanel != null) {
            for (java.awt.Component comp : cardsPanel.getComponents()) {
                if (comp instanceof ClientePanel) {
                    ((ClientePanel) comp).loadData();
                    break;
                }
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
        // Não há mais abas, painel já mostra funcionários
    }

    private void abrirColaboradoresAdministradores() {
        if (administradorPanel != null) {
            administradorPanel.loadData();
        }
        cardLayout.show(cardsPanel, "administradores");
    }

    private void abrirOrdens() {
        if (ordemServicoPanel == null) {
            ordemServicoPanel = new mecanicabase.view.swing.panels.OrdemServicoPanel(
                    context,
                    context.ordemCrud,
                    context.pecaCrud
            );
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

    // Adicionar método utilitário createSidebarButton
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
