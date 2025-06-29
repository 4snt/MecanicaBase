package mecanicabase.view.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.usuarios.Funcionario;

/**
 * Painel visual de agenda para agendamentos por mecânico. Permite alternar
 * entre visão semanal e mensal, navegar entre períodos e selecionar mecânico.
 */
public class AgendaView extends JPanel {

    private final ApplicationContext context;
    private JComboBox<Funcionario> mecanicoCombo;
    private JButton semanaBtn, mesBtn, anteriorBtn, proximoBtn;
    private JPanel agendaPanel;
    private boolean modoSemana = true;
    private LocalDate dataReferencia = LocalDate.now();
    private JSpinner datePicker;

    public AgendaView(ApplicationContext context) {
        this.context = context;
        setLayout(new BorderLayout());
        setupTopo();
        setupAgenda();
        atualizarAgenda();
    }

    private void setupTopo() {
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Mecânico:"));
        mecanicoCombo = new JComboBox<>();
        mecanicoCombo.setPreferredSize(new Dimension(250, 28));
        mecanicoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Funcionario) {
                    Funcionario f = (Funcionario) value;
                    setText(f.getNome() + " (" + f.getFuncao().name() + ")");
                }
                return this;
            }
        });
        carregarMecanicos();
        topo.add(mecanicoCombo);

        semanaBtn = new JButton("Semana");
        mesBtn = new JButton("Mês");
        anteriorBtn = new JButton("<");
        proximoBtn = new JButton(">");

        semanaBtn.addActionListener(e -> {
            modoSemana = true;
            atualizarAgenda();
        });
        mesBtn.addActionListener(e -> {
            modoSemana = false;
            atualizarAgenda();
        });
        anteriorBtn.addActionListener(e -> navegarPeriodo(-1));
        proximoBtn.addActionListener(e -> navegarPeriodo(1));

        // DatePicker (JSpinner)
        SpinnerDateModel dateModel = new SpinnerDateModel();
        datePicker = new JSpinner(dateModel);
        datePicker.setPreferredSize(new Dimension(120, 28));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(datePicker, "dd/MM/yyyy");
        datePicker.setEditor(editor);
        datePicker.setValue(java.sql.Date.valueOf(dataReferencia));
        datePicker.addChangeListener(e -> {
            Date selected = (Date) datePicker.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime(selected);
            dataReferencia = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            atualizarAgenda();
        });
        topo.add(datePicker);

        topo.add(semanaBtn);
        topo.add(mesBtn);
        topo.add(anteriorBtn);
        topo.add(proximoBtn);
        add(topo, BorderLayout.NORTH);
    }

    private void setupAgenda() {
        agendaPanel = new JPanel();
        add(new JScrollPane(agendaPanel), BorderLayout.CENTER);
    }

    private void carregarMecanicos() {
        mecanicoCombo.removeAllItems();
        List<Funcionario> mecanicos = Funcionario.instances;
        for (Funcionario f : mecanicos) {
            if (f.getFuncao() != null && (f.getFuncao().name().startsWith("MECANICO"))) {
                mecanicoCombo.addItem(f);
            }
        }
    }

    private void atualizarAgenda() {
        agendaPanel.removeAll();
        if (modoSemana) {
            montarAgendaSemana();
        } else {
            montarAgendaMes();
        }
        agendaPanel.revalidate();
        agendaPanel.repaint();
    }

    private void montarAgendaSemana() {
        agendaPanel.setLayout(new GridLayout(1, 7));
        LocalDate inicioSemana = dataReferencia.minusDays(dataReferencia.getDayOfWeek().getValue() - 1);
        for (int i = 0; i < 7; i++) {
            LocalDate dia = inicioSemana.plusDays(i);
            JPanel diaPanel = criarDiaPanel(dia);
            agendaPanel.add(diaPanel);
        }
    }

    private void montarAgendaMes() {
        agendaPanel.setLayout(new GridLayout(0, 7));
        LocalDate primeiroDia = dataReferencia.withDayOfMonth(1);
        int diasNoMes = dataReferencia.lengthOfMonth();
        int diaSemana = primeiroDia.getDayOfWeek().getValue();
        int offset = diaSemana - 1;
        int totalCelulas = offset + diasNoMes;
        for (int i = 0; i < totalCelulas; i++) {
            if (i < offset) {
                agendaPanel.add(new JPanel());
            } else {
                LocalDate dia = primeiroDia.plusDays(i - offset);
                JPanel diaPanel = criarDiaPanel(dia);
                agendaPanel.add(diaPanel);
            }
        }
    }

    private JPanel criarDiaPanel(LocalDate dia) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(dia.getDayOfMonth() + " " + dia.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault())));
        panel.setPreferredSize(new Dimension(140, 400));
        JPanel horariosPanel = new JPanel(new GridLayout(24, 1));
        DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inicio = LocalTime.of(0, 0);
        // Obter mecânico selecionado
        Funcionario mecanicoSelecionado = (Funcionario) mecanicoCombo.getSelectedItem();
        // Filtrar agendamentos do dia e do mecânico
        List<Agendamento> agendamentosDia = Agendamento.instances.stream()
                .filter(a -> a.getFuncionario() != null && mecanicoSelecionado != null && a.getFuncionario().getId().equals(mecanicoSelecionado.getId()))
                .filter(a -> a.getData().toLocalDate().equals(dia))
                .toList();
        for (int i = 0; i < 24; i++) {
            LocalTime hora = inicio.plusHours(i);
            String texto = hora.format(horaFmt);
            // Verifica se há agendamento neste horário
            Agendamento ag = agendamentosDia.stream()
                    .filter(a -> a.getData().getHour() == hora.getHour())
                    .findFirst().orElse(null);
            JLabel horaLabel;
            if (ag != null) {
                String servico = ag.getServico() != null ? ag.getServico().getTipo() : "(Serviço)";
                String status = ag.getStatus() != null ? ag.getStatus().name() : "";
                horaLabel = new JLabel(texto + " - " + servico + " [" + status + "]", SwingConstants.LEFT);
                horaLabel.setForeground(java.awt.Color.RED);
                horaLabel.setFont(horaLabel.getFont().deriveFont(12f).deriveFont(java.awt.Font.BOLD));
            } else {
                horaLabel = new JLabel(texto, SwingConstants.LEFT);
                horaLabel.setFont(horaLabel.getFont().deriveFont(12f));
            }
            horariosPanel.add(horaLabel);
        }
        JScrollPane scroll = new JScrollPane(horariosPanel);
        scroll.setPreferredSize(new Dimension(130, 350));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void navegarPeriodo(int direcao) {
        if (modoSemana) {
            dataReferencia = dataReferencia.plusWeeks(direcao);
        } else {
            dataReferencia = dataReferencia.plusMonths(direcao);
        }
        datePicker.setValue(java.sql.Date.valueOf(dataReferencia));
        atualizarAgenda();
    }
}
