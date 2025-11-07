import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DenunciaDAO dao = new DenunciaDAO();
        dao.criarTabela();

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 6) {
            exibirMenu();

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    registrar(dao, scanner);
                    break;
                case 2:
                    listar(dao);
                    break;
                case 3:
                    buscar(dao, scanner);
                    break;
                case 4:
                    atualizarStatus(dao, scanner);
                    break;
                case 5:
                    excluir(dao, scanner);
                    break;
                case 6:
                    System.out.println("Encerrando sistema ODS11 em Ação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- ODS11 EM AÇÃO: PRESERVAR MARINGÁ ---");
        System.out.println("1. Registrar Nova Denúncia");
        System.out.println("2. Listar Todas as Denúncias");
        System.out.println("3. Buscar Denúncia por ID");
        System.out.println("4. Atualizar Status (Monitoramento)");
        System.out.println("5. Excluir Denúncia");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void registrar(DenunciaDAO dao, Scanner scanner) {
        System.out.print("Descreva o problema: ");
        String descricao = scanner.nextLine();

        System.out.print("Qual a localização (ex: Parque do Ingá): ");
        String local = scanner.nextLine();

        System.out.print("Qual o tipo (1-CULTURAL / 2-NATURAL): ");
        String tipoInput = scanner.nextLine();
        String tipo = tipoInput.equals("1") ? "CULTURAL" : "NATURAL";

        Denuncia d = new Denuncia(descricao, local, tipo);
        dao.registrarDenuncia(d);
    }

    private static void listar(DenunciaDAO dao) {
        List<Denuncia> denuncias = dao.listarTodas();
        if (denuncias.isEmpty()) {
            System.out.println("Nenhuma denúncia registrada.");
        } else {
            System.out.println("\n--- MONITORAMENTO DE DENÚNCIAS ---");
            for (Denuncia d : denuncias) {
                System.out.println(d);
            }
        }
    }

    private static void buscar(DenunciaDAO dao, Scanner scanner) {
        try {
            System.out.print("Digite o ID da denúncia para buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Denuncia d = dao.buscarPorId(id);

            if (d != null) {
                System.out.println("--- DENÚNCIA ENCONTRADA ---");
                System.out.println(d);
            } else {
                System.out.println("Denúncia com ID " + id + " não encontrada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use um número.");
        }
    }

    private static void atualizarStatus(DenunciaDAO dao, Scanner scanner) {
        try {
            System.out.print("Digite o ID da denúncia para atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o NOVO status (ex: EM_ANALISE, RESOLVIDA): ");
            String novoStatus = scanner.nextLine();

            dao.atualizarStatus(id, novoStatus.toUpperCase());

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use um número. ");
        }
    }

    private static void excluir(DenunciaDAO dao, Scanner scanner) {
        try {
            System.out.print("Digite o ID da denúncia para excluir: ");
            int id = Integer.parseInt(scanner.nextLine());

            dao.excluirDenuncia(id);

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID inválido. Use um número.");
        }
    }
}