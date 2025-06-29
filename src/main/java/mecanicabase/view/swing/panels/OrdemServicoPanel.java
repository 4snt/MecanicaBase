package mecanicabase.view.swing.panels;

import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.StatusOrdemDeServico;
import mecanicabase.model.operacao.Peca;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.PecaCrud;

public class OrdemServicoPanel extends BasePanel {

    private final OrdemDeServicoCrud crud;
    private final PecaCrud pecaCrud;

    public OrdemServicoPanel(ApplicationContext context, OrdemDeServicoCrud crud, PecaCrud pecaCrud) {
        super(context, "Gestão de Ordens de Serviço");
        this.crud = crud;
        this.pecaCrud = pecaCrud;
    }

    @Override
    protected void setupForm() {
        // Nenhum formulário necessário neste painel
    }

    @Override
    protected void setupTable() {
        String[] columns = {"ID", "Status", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    protected void setupButtons() {
        buttonPanel.add(createButton("Adicionar Peça", this::adicionarPeca));
        buttonPanel.add(createButton("Finalizar OS", this::finalizarOrdem));
    }

    @Override
    protected boolean saveFormData() {
        // Não há formulário neste painel
        return false;
    }

    @Override
    public void loadData() {
        tableModel.setRowCount(0);
        List<OrdemDeServico> ordens = crud.listarTodos();
        for (OrdemDeServico ordem : ordens) {
            tableModel.addRow(new Object[]{
                ordem.getId(),
                ordem.getStatus(),
                "R$ 0,00" // substitua aqui se quiser calcular o total com base nas peças
            });
        }
    }

    private void adicionarPeca() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione uma OS.");
            return;
        }
        UUID osId = (UUID) tableModel.getValueAt(selectedRow, 0);
        OrdemDeServico os = crud.buscarPorId(osId);
        if (os == null) {
            showError("Ordem de serviço não encontrada.");
            return;
        }
        List<Peca> pecas = pecaCrud.listarTodos();
        if (pecas.isEmpty()) {
            showError("Nenhuma peça cadastrada.");
            return;
        }
        Peca selecionada = (Peca) JOptionPane.showInputDialog(
                this,
                "Selecione a peça:",
                "Adicionar Peça",
                JOptionPane.PLAIN_MESSAGE,
                null,
                pecas.toArray(),
                pecas.get(0)
        );
        if (selecionada == null) {
            return;
        }
        String qtdStr = JOptionPane.showInputDialog(this, "Quantidade:");
        if (qtdStr == null || qtdStr.isBlank()) {
            return;
        }
        try {
            int quantidade = Integer.parseInt(qtdStr.trim());
            crud.venderPeca(os.getId(), selecionada.getId(), quantidade);
            showMessage("Peça adicionada com sucesso!");
            loadData();
        } catch (Exception e) {
            showError("Erro: " + e.getMessage());
        }
    }

    private void finalizarOrdem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecione uma OS.");
            return;
        }
        Object idObj = tableModel.getValueAt(selectedRow, 0);
        UUID osId;
        if (idObj instanceof UUID uuid) {
            osId = uuid;
        } else {
            try {
                osId = UUID.fromString(idObj.toString());
            } catch (Exception e) {
                showError("ID da OS inválido.");
                return;
            }
        }
        if (!confirmAction("Tem certeza que deseja finalizar esta ordem de serviço?")) {
            return;
        }
        try {
            crud.atualizar(osId.toString(), true, StatusOrdemDeServico.CONCLUIDO);
            showMessage("Ordem de serviço finalizada!");
            loadData();
        } catch (RuntimeException e) {
            showError("Erro ao finalizar: " + e.getMessage());
        }
    }
}
