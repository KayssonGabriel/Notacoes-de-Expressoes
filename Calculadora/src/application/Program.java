package application;

import java.util.Scanner;

import util.Calculadora;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Calculadora calc = new Calculadora();

		System.out.println("Informe em qual tipo de notação a expressão está: ");
		System.out.println("1 - Infixa");
		System.out.println("2 - Pós-fixa");
		System.out.println("3 - Pré-fixa");
		int opcao = sc.nextInt();
		sc.nextLine();

		switch (opcao) {
		case 1:
			System.out.println("Digite a expressão Infixa: ");
			String expressaoIn = sc.nextLine();
			String expressaoInFinal = "( " + expressaoIn + " )";
			System.out.println("\nEm Infixa: " + expressaoIn);
			System.out.print("Em Pós-fixa: ");
			calc.InfixaParaPosFixa(expressaoInFinal);
			System.out.println("\nEm Pré-fixa: " + calc.InfixaParaPosFixaParaPreFixa());
			System.out.println("Resultado: " + calc.calcularResultadoInfixaPorPosFixa());

			break;

		case 2:
			System.out.println("Digite a expressão Pós-fixa: ");
			String expressaoPos = sc.nextLine();
			System.out.println("\nEm Infixa: " + calc.posFixaParaInfixa(expressaoPos));
			System.out.println("Em Pós-fixa " + expressaoPos);
			System.out.println("Em Pré-fixa: " + calc.posFixaParaPreFixa(expressaoPos));
			System.out.println("Resultado: " + calc.posFixaResultado(expressaoPos));

			break;

		case 3:
			System.out.println("Digite a expressão Pré-fixa: ");
			String expressaoPre = sc.nextLine();
			System.out.println("Em Infixa: "+calc.preFixaParaInfixa(expressaoPre));
			System.out.print("Em Pós-fixa: ");
			calc.preFixaParaPosFixa();
			System.out.println("\nEm Pré-fixa: " + expressaoPre);
			System.out.println("Resultado: "+ calc.preFixaResultado());

			break;
		}

		sc.close();
	}

}
