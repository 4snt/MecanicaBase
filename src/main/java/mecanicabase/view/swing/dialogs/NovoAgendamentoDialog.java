package mecanicabase.view.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.model.usuarios.Funcionario;

public class NovoAgendamentoDialog extends JDialog {

    private Cliente clienteSelecionado;
    private Veiculo veiculoSelecionado;
    private Servico servicoSelecionado;
    private Funcionario mecanicoSelecionado;
    private LocalDateTime dataHoraSelecionada;
    private String descricaoProblema;
    private boolean confirmado = false;

    private JComboBox<Cliente> clienteCombo;
    private JComboBox<Veiculo> veiculoCombo;
    private JComboBox<Servico> servicoCombo;
    private JComboBox<Funcionario> mecanicoCombo;
    private javax.swing.JSpinner dataHoraSpinner;
    private JTextField descricaoField;

    public NovoAgendamentoDialog(JFrame parent, ApplicationContext context) {
        super(parent, "Novo Agendamento", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));

        // Cliente
        List<Cliente> clientes = context.clienteCrud.listarTodos();
        clienteCombo = new JComboBox<>(clientes.toArray(new Cliente[0]));
        clienteCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente c) {
                    setText(c.getNome());
                }
                return this;
            }
        });
        form.add(new JLabel("Cliente:"));
        form.add(clienteCombo);

        // Veículo
        veiculoCombo = new JComboBox<>();
        veiculoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Veiculo v) {
                    setText(v.getModelo() + " - " + v.getPlaca());
                }
                return this;
            }
        });
        clienteCombo.addActionListener(e -> {
            Cliente c = (Cliente) clienteCombo.getSelectedItem();
            veiculoCombo.removeAllItems();
            if (c != null) {
                List<Veiculo> veiculos = context.veiculoCrud.buscarPorFiltro(c.getId().toString());
                for (Veiculo v : veiculos) {
                    veiculoCombo.addItem(v);
                }
            }
        });
        if (!clientes.isEmpty()) {
            clienteCombo.setSelectedIndex(0);
        }
        form.add(new JLabel("Veículo:"));
        form.add(veiculoCombo);

        // Serviço
        List<Servico> servicos = context.servicoCrud.listarTodos();
        servicoCombo = new JComboBox<>(servicos.toArray(new Servico[0]));
        servicoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Servico s) {
                    setText(s.getTipo());
                }
                return this;
            }
        });
        form.add(new JLabel("Serviço:"));
        form.add(servicoCombo);

        // Mecânico
        List<Funcionario> mecanicos = context.funcionarioCrud.listarTodos();
        mecanicoCombo = new JComboBox<>();
        mecanicoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario f) {
                    setText(f.getNome() + (f.getFuncao() != null ? " - " + f.getFuncao() : ""));
                }
                return this;
            }
        });

        Runnable atualizarMecanicos = () -> {
            mecanicoCombo.removeAllItems();
            Servico servicoSel = (Servico) servicoCombo.getSelectedItem();
            if (servicoSel != null && servicoSel.getTipoFuncionario() != null) {
                for (Funcionario f : mecanicos) {
                    if (f.getFuncao() != null && f.getFuncao().equals(servicoSel.getTipoFuncionario())) {
                        mecanicoCombo.addItem(f);
                    }
                }
            } else {
                for (Funcionario f : mecanicos) {
                    mecanicoCombo.addItem(f);
                }
            }
        };

        servicoCombo.addActionListener(e -> atualizarMecanicos.run());
        if (servicoCombo.getItemCount() > 0) {
            servicoCombo.setSelectedIndex(0);
        }
        atualizarMecanicos.run();
        form.add(new JLabel("Mecânico:"));
        form.add(mecanicoCombo);

        // Data e Hora
        form.add(new JLabel("Data e hora:"));
        java.util.Date now = new java.util.Date();
        javax.swing.SpinnerDateModel dateModel = new javax.swing.SpinnerDateModel(now, null, null, java.util.Calendar.MINUTE);
        dataHoraSpinner = new javax.swing.JSpinner(dateModel);
        javax.swing.JSpinner.DateEditor dateEditor = new javax.swing.JSpinner.DateEditor(dataHoraSpinner, "dd/MM/yyyy HH:mm");
        dataHoraSpinner.setEditor(dateEditor);
        form.add(dataHoraSpinner);

        // Descrição
        descricaoField = new JTextField();
        form.add(new JLabel("Descrição do problema:"));
        form.add(descricaoField);

        add(form, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("Salvar");
        JButton cancelBtn = new JButton("Cancelar");
        botoes.add(okBtn);
        botoes.add(cancelBtn);
        add(botoes, BorderLayout.SOUTH);

        okBtn.addActionListener(e -> {
            try {
                clienteSelecionado = (Cliente) clienteCombo.getSelectedItem();
                veiculoSelecionado = (Veiculo) veiculoCombo.getSelectedItem();
                servicoSelecionado = (Servico) servicoCombo.getSelectedItem();
                mecanicoSelecionado = (Funcionario) mecanicoCombo.getSelectedItem();
                java.util.Date data = (java.util.Date) dataHoraSpinner.getValue();
                dataHoraSelecionada = data.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                descricaoProblema = descricaoField.getText();
                confirmado = true;
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!\n" + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> setVisible(false));
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Cliente getCliente() {
        return clienteSelecionado;
    }

    public Veiculo getVeiculo() {
        return veiculoSelecionado;
    }

    public Servico getServico() {
        return servicoSelecionado;
    }

    public Funcionario getMecanico() {
        return mecanicoSelecionado;
    }

    public LocalDateTime getDataHora() {
        return dataHoraSelecionada;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }
}
