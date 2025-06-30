package mecanicabase;

import java.time.LocalDateTime;
import mecanicabase.controller.AgendamentoController;
import mecanicabase.model.financeiro.Agendamento;
import mecanicabase.model.financeiro.OrdemDeServico;
import mecanicabase.model.financeiro.StatusAgendamento;
import mecanicabase.model.operacao.EntradaPeca;
import mecanicabase.model.operacao.Peca;
import mecanicabase.model.operacao.Servico;
import mecanicabase.model.operacao.Veiculo;
import mecanicabase.model.usuarios.Cliente;
import mecanicabase.service.financeiro.AgendamentoCrud;
import mecanicabase.service.operacao.PecaCrud;
import mecanicabase.service.usuarios.ClienteCrud;

public class RequisitosImplementadosTest {

    public static void main(String[] args) {
        testeQuestao1_Cliente();
        testeQuestao2e3_FuncionarioEAdministrador();
        testeQuestao4_PecaEstoque();
        testeQuestao5_7_Agendamento();
        testeQuestao6_EntradaPeca();
        testeQuestao8_RelatorioVendasServicos();
        testeQuestao9_BalancoMensal();
    }

    // Questão 1: Incluir, Editar e Remover Cliente
    public static void testeQuestao1_Cliente() {
        System.out.println("\n--- Questão 1: Cliente ---");
        ClienteCrud clienteCrud = new ClienteCrud();
        Cliente c = clienteCrud.criar(true, "João", "joao@email.com", "123", "123.456.789-00", "99999-0000", "Rua 1");
        System.out.println("Criado: " + c);
        clienteCrud.atualizar(c.getId().toString(), true, "João Silva", null, null, null, null);
        System.out.println("Editado: " + clienteCrud.buscarPorId(c.getId().toString()));
        clienteCrud.removerPorId(c.getId().toString());
        System.out.println("Removido: " + clienteCrud.buscarPorId(c.getId().toString()));
    }

    // Questão 2 e 3: Incluir, Editar, Remover e Alterar Senha de Funcionário e Administrador
    public static void testeQuestao2e3_FuncionarioEAdministrador() {
        System.out.println("\n--- Questão 2 e 3: Funcionário e Administrador ---");
        // Funcionário
        var funcionarioCrud = new mecanicabase.service.usuarios.FuncionarioCrud();
        var funcionario = funcionarioCrud.criar(true, "Maria", mecanicabase.model.usuarios.TipoFuncionario.MECANICO_ESPECIFICO, "maria@email.com", "321", "321.654.987-00", "88888-0000", "Rua 2", 2500.0f);

        System.out.println("Funcionário criado:");
        imprimirFuncionarioDetalhado(funcionario);

        funcionarioCrud.atualizar(funcionario.getId().toString(), true, "Maria Souza", null, null, null, mecanicabase.model.usuarios.TipoFuncionario.MECANICO_ESPECIALISTA, Float.valueOf(3000.0f));

        System.out.println("Funcionário editado:");
        imprimirFuncionarioDetalhado(funcionarioCrud.buscarPorId(funcionario.getId().toString()));

        boolean senhaAlteradaFunc = funcionarioCrud.trocarSenha(funcionario, "321", "novaSenhaFunc");
        System.out.println("Senha do funcionário alterada: " + senhaAlteradaFunc);

        funcionarioCrud.removerPorId(funcionario.getId().toString());
        System.out.println("Funcionário removido: " + funcionarioCrud.buscarPorId(funcionario.getId().toString()));

        // Administrador
        var adminCrud = new mecanicabase.service.usuarios.AdministradorCrud();
        var admin = adminCrud.criar(true, "Admin", "admin@email.com", "senha123", "000.000.000-00", "99999-9999", "Rua Admin");

        System.out.println("Administrador criado:");
        imprimirAdministradorDetalhado(admin);

        adminCrud.atualizar(admin.getId().toString(), true, "Admin Editado", null, null, null);
        System.out.println("Administrador editado:");
        imprimirAdministradorDetalhado(adminCrud.buscarPorId(admin.getId().toString()));

        boolean senhaAlteradaAdmin = adminCrud.trocarSenha(admin, "senha123", "novaSenhaAdmin");
        System.out.println("Senha do administrador alterada: " + senhaAlteradaAdmin);

        adminCrud.removerPorId(admin.getId().toString());
        System.out.println("Administrador removido: " + adminCrud.buscarPorId(admin.getId().toString()));
    }

    private static void imprimirFuncionarioDetalhado(Object funcionario) {
        if (funcionario == null) {
            System.out.println("null");
            return;
        }
        var clazz = funcionario.getClass();
        try {
            System.out.println("  ID: " + clazz.getMethod("getId").invoke(funcionario));
            System.out.println("  Nome: " + clazz.getMethod("getNome").invoke(funcionario));
            System.out.println("  Função: " + clazz.getMethod("getFuncao").invoke(funcionario)); // Corrigido de getTipo para getFuncao
            System.out.println("  Salário: " + clazz.getMethod("getSalario").invoke(funcionario));
            System.out.println("  Email: " + clazz.getMethod("getEmail").invoke(funcionario));
            System.out.println("  CPF: " + clazz.getMethod("getCpf").invoke(funcionario));
            System.out.println("  Telefone: " + clazz.getMethod("getTelefone").invoke(funcionario));
            System.out.println("  Endereço: " + clazz.getMethod("getEndereco").invoke(funcionario));
        } catch (Exception e) {
            System.out.println("  Erro ao imprimir funcionário: " + e.getMessage());
        }
    }

    private static void imprimirAdministradorDetalhado(Object admin) {
        if (admin == null) {
            System.out.println("null");
            return;
        }
        var clazz = admin.getClass();
        try {
            System.out.println("  ID: " + clazz.getMethod("getId").invoke(admin));
            System.out.println("  Nome: " + clazz.getMethod("getNome").invoke(admin));
            System.out.println("  Email: " + clazz.getMethod("getEmail").invoke(admin));
            System.out.println("  CPF: " + clazz.getMethod("getCpf").invoke(admin));
            System.out.println("  Telefone: " + clazz.getMethod("getTelefone").invoke(admin));
            System.out.println("  Endereço: " + clazz.getMethod("getEndereco").invoke(admin));
        } catch (Exception e) {
            System.out.println("  Erro ao imprimir administrador: " + e.getMessage());
        }
    }

    // Questão 4: Verificar produto no estoque da loja
    public static void testeQuestao4_PecaEstoque() {
        System.out.println("\n--- Questão 4: Peça/Estoque ---");
        PecaCrud pecaCrud = new PecaCrud();
        Peca p = pecaCrud.criar(true, "Filtro de Óleo", 50.0f, 10);
        System.out.println("Peça criada: " + p);
        System.out.println("Listar todas as peças:");
        for (Peca item : pecaCrud.listarTodos()) {
            System.out.println(item);
        }
        System.out.println("Buscar peça por ID:");
        Peca encontrada = pecaCrud.buscarPorId(p.getId().toString());
        System.out.println(encontrada);
    }

    // Questão 5: Realizar agendamentos (exemplo simplificado)
    public static void testeQuestao5_7_Agendamento() {
        System.out.println("\n--- Questão 5: Agendamento ---");
        try {
            // Inicializar CRUDes e Controller
            var veiculoCrud = new mecanicabase.service.operacao.VeiculoCrud();
            var servicoCrud = new mecanicabase.service.operacao.ServicoCrud();
            var ordemCrud = new mecanicabase.service.financeiro.OrdemDeServicoCrud();
            var agendamentoCrud = new AgendamentoCrud();
            var agendamentoController = new AgendamentoController(agendamentoCrud);

            // Buscar instâncias já existentes
            Veiculo veiculo = veiculoCrud.buscarPorId("75cee087-a1f8-4dc8-9374-bcfa5ca665aa");
            Servico servico = servicoCrud.buscarPorId("35414987-e298-4505-8378-450af89c3651");

            // Criar ordem de serviço
            OrdemDeServico ordem = ordemCrud.criar(true, veiculo.getCliente().getId());

            // Criar agendamento com data futura
            LocalDateTime dataAgendamento = LocalDateTime.now().plusDays(1);
            String problema = "Trocar bateria do carro";

            Agendamento agendamento = agendamentoController.criarAgendamentoComAlocacao(
                    dataAgendamento,
                    servico,
                    problema,
                    veiculo,
                    ordem
            );

            System.out.println("🛠️ Agendamento criado:");
            System.out.println("   - ID: " + agendamento.getId());
            System.out.println("   - Data: " + agendamento.getData());
            System.out.println("   - Problema: " + agendamento.getDescricaoProblema());
            System.out.println("   - Funcionário: " + agendamento.getFuncionario().getNome());
            System.out.println("   - Veículo: " + agendamento.getVeiculo().getModelo());
            System.out.println("   - Serviço: " + agendamento.getServico().getTipo());
            System.out.println("   - Preço original: R$ " + agendamento.getServico().getPreco());

            // Cancelar agendamento
            agendamentoCrud.atualizar(
                    agendamento.getId().toString(),
                    true,
                    agendamento.getData(),
                    agendamento.getDescricaoProblema(),
                    agendamento.getVeiculo().getId(),
                    agendamento.getElevador() != null ? agendamento.getElevador().getId() : null,
                    agendamento.getFuncionario().getId(),
                    agendamento.getServico().getId(),
                    agendamento.getOrdemDeServico().getId(),
                    StatusAgendamento.CANCELADO
            );

            float precoCancelamento = agendamento.getServico().getPreco() * 0.2f;

            System.out.println("❌ Agendamento cancelado: " + agendamento.getId());
            System.out.printf("💰 Valor cobrado pelo cancelamento (20%%): R$ %.2f%n", precoCancelamento);

        } catch (Exception e) {
            System.out.println("Erro ao criar/cancelar agendamento: " + e.getMessage());
        }
    }

    // Questão 6: Receber de fornecedores e atualizar estoque
    public static void testeQuestao6_EntradaPeca() {
        System.out.println("\n--- Questão 6: Entrada de Peça ---");

        try {
            PecaCrud pecaCrud = new PecaCrud();

            // Criar peça no sistema
            Peca peca = pecaCrud.criar(true, "Pastilha de Freio", 120.0f, 5);
            System.out.println("Peça criada: " + peca.getNome() + " | Estoque inicial: " + peca.getEstoque());

            // Registrar entrada de fornecedor
            String fornecedor = "Fornecedor X";
            float custoUnitario = 100.0f;
            int quantidadeEntrada = 10;

            EntradaPeca entrada = pecaCrud.registrarEntrada(peca.getId(), fornecedor, custoUnitario, quantidadeEntrada);

            System.out.println("✅ Entrada registrada:");
            System.out.println("   - Peça: " + peca.getNome());
            System.out.println("   - Quantidade: " + entrada.getQuantidade());
            System.out.println("   - Custo unitário: R$ " + entrada.getCusto());
            System.out.println("   - Fornecedor: " + entrada.getNomeFornecedor());
            System.out.println("   - Estoque atual: " + peca.getEstoque());

        } catch (Exception e) {
            System.out.println("Erro ao registrar entrada: " + e.getMessage());
        }
    }

    // Questão 8: Emitir relatório de vendas e serviços
    public static void testeQuestao8_RelatorioVendasServicos() {
        System.out.println("\n--- Questão 8: Relatório de Vendas e Serviços ---");
        // Supondo que exista GerarRelatorioUseCase
        // GerarRelatorioUseCase relatorio = new GerarRelatorioUseCase();
        // relatorio.gerarRelatorioDia(...);
        // relatorio.gerarRelatorioMes(...);
        System.out.println("(Exemplo) Implemente conforme sua lógica de relatório.");
    }

    // Questão 9: Gerar balanço mensal
    public static void testeQuestao9_BalancoMensal() {
        System.out.println("\n--- Questão 9: Balanço Mensal ---");
        // Supondo que exista GerarBalancoUseCase
        // GerarBalancoUseCase balanco = new GerarBalancoUseCase();
        // balanco.gerarBalancoMes(...);
        System.out.println("(Exemplo) Implemente conforme sua lógica de balanço.");
    }

    // Implemente outros métodos de teste conforme a necessidade do seu projeto.
}
