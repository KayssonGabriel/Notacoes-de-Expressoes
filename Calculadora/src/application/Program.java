package application;

import java.util.Scanner;
import util.Calculadora;

public class Program {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Informe em qual tipo de notação a expressão está: ");
			System.out.println("1 - Infixa");
			System.out.println("2 - Pós-fixa");
			System.out.println("3 - Pré-fixa");
			if (!sc.hasNextInt()) {
				System.out.println("Escolha uma opção de 1 a 3.");
				return;
			}
			int opcao = sc.nextInt();
			if (opcao < 1 || opcao > 3) {
				System.out.println("Escolha uma opção de 1 a 3.");
				return;
			}
			if (sc.hasNextLine()) {
				sc.nextLine();
			}
			String notacao = switch (opcao) {
				case 1 -> "Infixa";
				case 2 -> "Pós-fixa";
				default -> "Pré-fixa";
			};
			System.out.println("Digite a expressão " + notacao + ": ");
			if (!sc.hasNextLine()) {
				return;
			}
			String expressao = sc.nextLine();

			Calculadora calc = new Calculadora();
			try {
				switch (opcao) {
					case 1 -> {
						System.out.println("Em Infixa: " + expressao);
						System.out.print("Em Pós-fixa: ");
						calc.InfixaParaPosFixa(expressao);
						System.out.println("\nEm Pré-fixa: " + calc.InfixaParaPosFixaParaPreFixa());
						System.out.println("Resultado: " + calc.calcularResultadoInfixaPorPosFixa());
					}
					case 2 -> {
						System.out.println("Em Infixa: " + calc.posFixaParaInfixa(expressao));
						System.out.println("Em Pós-fixa: " + expressao);
						System.out.println("Em Pré-fixa: " + calc.posFixaParaPreFixa(expressao));
						System.out.println("Resultado: " + calc.posFixaResultado(expressao));
					}
					case 3 -> {
						System.out.println("Em Infixa: " + calc.preFixaParaInfixa(expressao));
						System.out.print("Em Pós-fixa: ");
						calc.preFixaParaPosFixa();
						System.out.println("\nEm Pré-fixa: " + expressao);
						System.out.println("Resultado: " + calc.preFixaResultado());
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println("\nExpressão inválida. " + e.getMessage());
			}
		}
	}
}
