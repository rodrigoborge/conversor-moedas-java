import java.util.Scanner;

public class ConversorMoedas {
    public static void main(String[] args) {
        ConsultaValor novaConsulta = new ConsultaValor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n***** CONVERSOR DE MOEDAS  *****");

            System.out.println("1 - Real --> Dolar");
            System.out.println("2 - Real --> Euro");
            System.out.println("3 - Real --> Libra");
            System.out.println("4 - Real --> Iene");
            System.out.println("5 - Real --> Franco Suíço");
            System.out.println("6 - Sair");
            System.out.println("De acordo com o menu acima, escolha uma opção:");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (escolha) {
                case 1:
                    realizarConversao(novaConsulta, "USD");
                    break;
                case 2:
                    realizarConversao(novaConsulta, "EUR");
                    break;
                case 3:
                    realizarConversao(novaConsulta, "GBP");
                    break;
                case 4:
                    realizarConversao(novaConsulta, "JPY");
                    break;
                case 5:
                    realizarConversao(novaConsulta, "CHF");
                    break;
                case 6:
                    System.out.println("Saindo do programa...");
                    return; // Sai do método main e encerra o programa
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void realizarConversao(ConsultaValor consulta, String moedaDestino) {
        ExchangeRateApi consultaMoeda = consulta.consultaValor("BRL");

        if (consultaMoeda != null && consultaMoeda.conversionRates() != null) {
            Double taxaDeCambio = (Double) consultaMoeda.conversionRates().get(moedaDestino);

            if (taxaDeCambio != null) {
                double valorEmReais;
                Scanner scanner = new Scanner(System.in);

                System.out.println("Digite o valor que você deseja converter: ");
                valorEmReais = scanner.nextDouble();

                double valorConvertido = valorEmReais * taxaDeCambio;
                System.out.println("O valor de " + valorEmReais + " BRL" + " convertido para " + moedaDestino +
                        " é de: " + valorConvertido);
                System.out.println("\n********************************");
            } else {
                System.out.println("Taxa de câmbio para " + moedaDestino + " não disponível.");
                System.out.println("\n********************************");
            }
        } else {
            System.out.println("Erro ao obter as taxas de câmbio.");
            System.out.println("\n********************************");
        }
    }
}
