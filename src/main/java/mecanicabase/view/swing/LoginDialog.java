package mecanicabase.view.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.infra.auth.Session;
import mecanicabase.model.usuarios.Colaborador;

/**
 * Dialog de login para a interface Swing.
 */
public class LoginDialog extends JDialog {

    private JComboBox<String> tipoUsuarioComboBox;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private boolean loginSucesso = false;
    private final ApplicationContext context;

    public LoginDialog(JFrame parent, ApplicationContext context) {
        super(parent, "Login - Sistema Oficina Mecânica", true);
        this.context = context;
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        tipoUsuarioComboBox = new JComboBox<>(new String[]{"Funcionário", "Administrador"});

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Título
        JLabel titleLabel = new JLabel("Login do Sistema", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(16f));
        add(titleLabel, BorderLayout.NORTH);

        // Painel do formulário
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int linha = 0;

        // Tipo de Usuário
        gbc.gridx = 0;
        gbc.gridy = linha;
        formPanel.add(new JLabel("Tipo de Usuário:"), gbc);

        gbc.gridx = 1;
        formPanel.add(tipoUsuarioComboBox, gbc);

        linha++;

        // Usuário
        usuarioField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = linha;
        formPanel.add(new JLabel("E-mail:"), gbc); // Alterado para E-mail

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(usuarioField, gbc);

        linha++;

        // Senha
        senhaField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(senhaField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Entrar");
        JButton cancelButton = new JButton("Cancelar");

        loginButton.addActionListener(e -> realizarLogin());
        cancelButton.addActionListener(e -> {
            loginSucesso = false;
            dispose();
        });

        getRootPane().setDefaultButton(loginButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupLayout() {
        pack();
        setLocationRelativeTo(getParent());
        setResizable(false);
    }

    private void realizarLogin() {
        String tipoUsuario = (String) tipoUsuarioComboBox.getSelectedItem();
        String usuario = usuarioField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha usuário e senha.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Colaborador colaborador = null;

            if ("Funcionário".equals(tipoUsuario)) {
                colaborador = context.funcionarioCrud.login(usuario, senha);
            } else if ("Administrador".equals(tipoUsuario)) {
                colaborador = context.administradorCrud.login(usuario, senha);
            }

            System.out.println("[DEBUG] colaborador retornado: " + (colaborador != null ? colaborador.getNome() : "null"));

            if (colaborador != null) {
                Session.setPessoaLogado(colaborador);
                loginSucesso = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuário ou senha incorretos.",
                        "Erro de Login", JOptionPane.ERROR_MESSAGE);
                senhaField.setText("");
                usuarioField.selectAll();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao realizar login: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Adicionado para depuração
        }

    }

    public boolean isLoginSucesso() {
        return loginSucesso;
    }

    public static boolean showLogin(JFrame parent, ApplicationContext context) {
        LoginDialog dialog = new LoginDialog(parent, context);
        dialog.setVisible(true);
        return dialog.isLoginSucesso();
    }
}
