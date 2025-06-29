package mecanicabase.view.swing.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mecanicabase.controller.AgendamentoController;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.view.swing.AgendaView;
import mecanicabase.view.swing.dialogs.NovoAgendamentoDialog;

/**
 * Painel Swing para gerenciamento de agendamentos com agenda visual.
 */
public class AgendamentoPanel extends BasePanel {

    private final AgendaView agendaView;
    private final JPanel buttonPanel;

    public AgendamentoPanel(ApplicationContext context) {
        super(context, "Agendamentos");
        setLayout(new BorderLayout());
        agendaView = new AgendaView(context);
        add(agendaView, BorderLayout.CENTER);
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        setupButtons();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void initializeComponents(String title) {
        // Título
        JLabel titleLabel = new JLabel(title, javax.swing.SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        titleLabel.setForeground(java.awt.Color.WHITE);
        setLayout(new BorderLayout());
        setBackground(new java.awt.Color(40, 40, 50));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);
        // Agenda visual já é adicionada no construtor
        // Painel de botões já é adicionado no construtor
    }

    @Override
    protected void setupTable() {
        // Não há tabela neste painel
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Novo Agendamento", this::novoAgendamento));
        buttonPanel.add(createButton("Editar", this::editarAgendamento));
        buttonPanel.add(createButton("Cancelar", this::cancelarAgendamento));
        buttonPanel.add(createButton("Atualizar", this::atualizarAgenda));
    }

    @Override
    protected void setupForm() {
        // Não há formulário padrão neste painel
    }

    @Override
    public void loadData() {
        atualizarAgenda();
    }

    private void novoAgendamento() {
        NovoAgendamentoDialog dialog = new NovoAgendamentoDialog(null, context);
        dialog.setVisible(true);
        if (dialog.isConfirmado()) {
            try {
                // Cria OS vinculada
                OrdemDeServico ordem = context.ordemCrud.criar(true, dialog.getCliente().getId());
                // Cria agendamento com alocação
                AgendamentoController agendamentoController = new AgendamentoController(context.agendamentoCrud);
                Agendamento agendamento = agendamentoController.criarAgendamentoComAlocacao(
                        dialog.getDataHora(),
                        dialog.getServico(),
                        dialog.getDescricaoProblema(),
                        dialog.getVeiculo(),
                        ordem
                );
                JOptionPane.showMessageDialog(this, "Agendamento criado com sucesso!\nID: " + agendamento.getId());
                atualizarAgenda();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar agendamento: " + ex.getMessage());
            }
        }
    }

    private void editarAgendamento() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento - Editar Agendamento");
    }

    private void cancelarAgendamento() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento - Cancelar Agendamento");
    }

    private void atualizarAgenda() {
        agendaView.repaint();
    }

    @Override
    protected boolean saveFormData() {
        // Não há formulário padrão neste painel
        return false;
    }

    @Override
    protected void clearForm() {
        // Não há formulário padrão neste painel
    }
}
