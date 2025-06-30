package mecanicabase.view.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mecanicabase.service.financeiro.relatorios.GerarBalancoUseCase;

public class BalancoDialog extends JDialog {

    public BalancoDialog() {
        super((Frame) null, "Balanço Financeiro", true);
        setSize(500, 300);
        setLayout(new BorderLayout());

        JTextField inicioField = new JTextField("2025-01-01");
        JTextField fimField = new JTextField("2025-12-31");
        JButton gerar = new JButton("Gerar Balanço");

        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        gerar.addActionListener(e -> {
            try {
                LocalDateTime inicio = LocalDateTime.parse(inicioField.getText() + "T00:00:00");
                LocalDateTime fim = LocalDateTime.parse(fimField.getText() + "T23:59:59");
                String relatorio = new GerarBalancoUseCase().use(inicio, fim);
                resultado.setText(relatorio);
            } catch (Exception ex) {
                resultado.setText("Erro: " + ex.getMessage());
            }
        });

        JPanel input = new JPanel(new GridLayout(3, 2));
        input.add(new JLabel("Início (yyyy-MM-dd):"));
        input.add(inicioField);
        input.add(new JLabel("Fim (yyyy-MM-dd):"));
        input.add(fimField);
        input.add(gerar);

        add(input, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);
    }
}
