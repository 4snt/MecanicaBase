package mecanicabase.view.swing.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import mecanicabase.infra.ApplicationContext;
import mecanicabase.model.financeiro.CategoriaDespesa;
import mecanicabase.service.financeiro.CategoriaDespesaCrud;

public class CategoriaDespesaDialog extends JDialog {

    private final CategoriaDespesaCrud crud;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> list = new JList<>(listModel);

    public CategoriaDespesaDialog(ApplicationContext context) {
        super((Frame) null, "Categorias de Despesa", true);
        this.crud = new CategoriaDespesaCrud();
        setSize(400, 300);
        setLayout(new BorderLayout());

        JButton criarBtn = new JButton("Criar");
        criarBtn.addActionListener(e -> criarCategoria());

        JButton atualizarBtn = new JButton("Atualizar");
        atualizarBtn.addActionListener(e -> atualizarCategoria());

        JButton removerBtn = new JButton("Remover");
        removerBtn.addActionListener(e -> removerCategoria());

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
        List<CategoriaDespesa> categorias = crud.listarTodos();
        for (CategoriaDespesa c : categorias) {
            listModel.addElement(c.getId() + " - " + c.getTitulo());
        }
    }

    private void criarCategoria() {
        String titulo = JOptionPane.showInputDialog(this, "Título da categoria:");
        if (titulo != null && !titulo.isBlank()) {
            crud.criar(true, titulo);
            carregarLista();
        }
    }

    private void atualizarCategoria() {
        String selecionado = list.getSelectedValue();
        if (selecionado == null) {
            return;
        }

        String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:");
        if (novoTitulo != null && !novoTitulo.isBlank()) {
            UUID id = UUID.fromString(selecionado.split(" ")[0]);
            crud.atualizar(id.toString(), true, novoTitulo);
            carregarLista();
        }
    }

    private void removerCategoria() {
        String selecionado = list.getSelectedValue();
        if (selecionado == null) {
            return;
        }
        UUID id = UUID.fromString(selecionado.split(" ")[0]);
        crud.removerPorId(id.toString());
        carregarLista();
    }
}
