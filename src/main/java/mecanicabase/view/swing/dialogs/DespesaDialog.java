package mecanicabase.view.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.model.financeiro.Despesa;
import mecanicabase.service.financeiro.CategoriaDespesaCrud;
import mecanicabase.service.financeiro.DespesaCrud;

public class DespesaDialog extends JDialog {

    private final DespesaCrud despesaCrud = new DespesaCrud();
    private final CategoriaDespesaCrud categoriaCrud = new CategoriaDespesaCrud();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> list = new JList<>(listModel);

    public DespesaDialog(ApplicationContext context) {
        super((Frame) null, "Gestão de Despesas", true);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JButton criarBtn = new JButton("Criar");
        criarBtn.addActionListener(e -> criarDespesa());

        JButton atualizarBtn = new JButton("Atualizar");
        atualizarBtn.addActionListener(e -> atualizarDespesa());

        JButton removerBtn = new JButton("Remover");
        removerBtn.addActionListener(e -> removerDespesa());

        JPanel topPanel = new JPanel();
        topPanel.add(criarBtn);
        topPanel.add(atualizarBtn);
        topPanel.add(removerBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);

        carregarLista();
    }

    private void carregarLista() {
        listModel.clear();
        List<Despesa> despesas = despesaCrud.listarTodos();
        for (Despesa d : despesas) {
            listModel.addElement(d.getId() + " - R$ " + d.getValor() + " - " + d.getDescricao());
        }
    }

    private void criarDespesa() {
        List<CategoriaDespesa> categorias = categoriaCrud.listarTodos();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma categoria cadastrada.");
            return;
        }

        String[] opcoes = categorias.stream().map(CategoriaDespesa::getTitulo).toArray(String[]::new);
        int sel = JOptionPane.showOptionDialog(this, "Escolha a categoria:", "Categoria",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (sel < 0) {
            return;
        }
        CategoriaDespesa cat = categorias.get(sel);

        String descricao = JOptionPane.showInputDialog(this, "Descrição:");
        String valorStr = JOptionPane.showInputDialog(this, "Valor:");
        float valor = Float.parseFloat(valorStr);

        despesaCrud.criar(true, cat.getId(), descricao, valor);
        carregarLista();
    }

    private void atualizarDespesa() {
        String selecionado = list.getSelectedValue();
        if (selecionado == null) {
            return;
        }

        String novaDescricao = JOptionPane.showInputDialog(this, "Nova descrição:");
        String novoValorStr = JOptionPane.showInputDialog(this, "Novo valor:");
        Float novoValor = novoValorStr.isBlank() ? null : Float.valueOf(novoValorStr);

        String id = selecionado.split(" ")[0];
        despesaCrud.atualizar(id, true, novaDescricao, novoValor);
        carregarLista();
    }

    private void removerDespesa() {
        String selecionado = list.getSelectedValue();
        if (selecionado == null) {
            return;
        }

        String id = selecionado.split(" ")[0];
        despesaCrud.removerPorId(id);
        carregarLista();
    }
}
