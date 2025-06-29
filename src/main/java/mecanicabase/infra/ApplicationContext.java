package mecanicabase.infra;

import java.util.Scanner;
import mecanicabase.controller.AgendamentoController;
import mecanicabase.controller.ClienteController;
import mecanicabase.service.financeiro.AgendamentoCrud;
import mecanicabase.service.financeiro.CategoriaDespesaCrud;
import mecanicabase.service.financeiro.DespesaCrud;
import mecanicabase.service.financeiro.OrdemDeServicoCrud;
import mecanicabase.service.operacao.PecaCrud;
import mecanicabase.service.operacao.ServicoCrud;
import mecanicabase.service.operacao.VeiculoCrud;
import mecanicabase.service.usuarios.AdministradorCrud;
import mecanicabase.service.usuarios.ClienteCrud;
import mecanicabase.service.usuarios.FuncionarioCrud;
import mecanicabase.view.Terminal.AgendamentoTerminalHandler;
import mecanicabase.view.Terminal.CategoriaDespesaTerminalHandler;
import mecanicabase.view.Terminal.ClienteTerminalHandler;
import mecanicabase.view.Terminal.ColaboradorTerminalHandler;
import mecanicabase.view.Terminal.DespesaTerminalHandler;
import mecanicabase.view.Terminal.GerarBalancoTerminalHandler;
import mecanicabase.view.Terminal.GerarRelatorioTerminalHandler;
import mecanicabase.view.Terminal.LoginTerminalHandler;
import mecanicabase.view.Terminal.OficinaTerminalHandler;
import mecanicabase.view.Terminal.OrdemDeServicoTerminalHandler;
import mecanicabase.view.Terminal.PecaTerminalHandler;
import mecanicabase.view.Terminal.ServicoTerminalHandler;
import mecanicabase.view.Terminal.VeiculoTerminalHandler;
import mecanicabase.view.Terminal.router.TerminalRouter;

public class ApplicationContext {

    // 🔗 Scanner global
    public final Scanner scanner;

    // 🔗 Cruds
    public final ClienteCrud clienteCrud;
    public final VeiculoCrud veiculoCrud;
    public final ServicoCrud servicoCrud;
    public final OrdemDeServicoCrud ordemCrud;
    public final AgendamentoCrud agendamentoCrud;
    public final DespesaCrud despesaCrud;
    public final CategoriaDespesaCrud categoriaCrud;
    public final PecaCrud pecaCrud;
    public final FuncionarioCrud funcionarioCrud;
    public final AdministradorCrud administradorCrud;

    // 🔗 Controllers
    public final AgendamentoController agendamentoController;
    public final ClienteController clienteController;

    // 🔗 Terminal Handlers
    public final ClienteTerminalHandler clienteHandler;
    public final VeiculoTerminalHandler veiculoHandler;
    public final ServicoTerminalHandler servicoHandler;
    public final OrdemDeServicoTerminalHandler ordemHandler;
    public final AgendamentoTerminalHandler agendamentoHandler;
    public final DespesaTerminalHandler despesaHandler;
    public final CategoriaDespesaTerminalHandler categoriaHandler;
    public final OficinaTerminalHandler oficinaHandler;
    public final PecaTerminalHandler pecaHandler;
    public final ColaboradorTerminalHandler colaboradorHandler;
    public final LoginTerminalHandler loginHandler;
    public final GerarRelatorioTerminalHandler relatorioHandler;
    public final GerarBalancoTerminalHandler balancoHandler;

    // 🔗 Terminal Router
    public final TerminalRouter router;

    public ApplicationContext() {
        // ✅ Scanner
        scanner = new Scanner(System.in);

        // ✅ Instanciar Cruds
        clienteCrud = new ClienteCrud();
        veiculoCrud = new VeiculoCrud();
        servicoCrud = new ServicoCrud();
        ordemCrud = new OrdemDeServicoCrud();
        agendamentoCrud = new AgendamentoCrud();
        despesaCrud = new DespesaCrud();
        categoriaCrud = new CategoriaDespesaCrud();
        pecaCrud = new PecaCrud();
        funcionarioCrud = new FuncionarioCrud();
        administradorCrud = new AdministradorCrud();

        // ✅ Instanciar Controllers
        agendamentoController = new AgendamentoController(agendamentoCrud);
        clienteController = new ClienteController(clienteCrud);

        // ✅ Instanciar Terminal Handlers (na ordem correta!)
        clienteHandler = new ClienteTerminalHandler(scanner, clienteController);
        veiculoHandler = new VeiculoTerminalHandler(scanner, veiculoCrud);
        servicoHandler = new ServicoTerminalHandler(scanner, servicoCrud);
        pecaHandler = new PecaTerminalHandler(scanner, pecaCrud); // 🔥 precisa antes da oficina
        ordemHandler = new OrdemDeServicoTerminalHandler(scanner, ordemCrud, pecaCrud);

        agendamentoHandler = new AgendamentoTerminalHandler(
                scanner,
                agendamentoCrud,
                ordemCrud,
                clienteCrud,
                veiculoCrud,
                servicoCrud,
                agendamentoController
        );

        despesaHandler = new DespesaTerminalHandler(scanner, despesaCrud, categoriaCrud);
        categoriaHandler = new CategoriaDespesaTerminalHandler(scanner, categoriaCrud);

        oficinaHandler = new OficinaTerminalHandler( // 🔥 agora pecaHandler já existe
                scanner,
                veiculoHandler,
                pecaHandler
        );

        colaboradorHandler = new ColaboradorTerminalHandler(scanner, funcionarioCrud, administradorCrud);
        loginHandler = new LoginTerminalHandler(scanner, funcionarioCrud, administradorCrud);
        relatorioHandler = new GerarRelatorioTerminalHandler(scanner);
        balancoHandler = new GerarBalancoTerminalHandler(scanner);

        router = new TerminalRouter(this);
    }
}
