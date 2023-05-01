package util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Calculadora {
	private String expressaoPosFixa;
	private String preFixaParaInfixa;

	public void InfixaParaPosFixa(String expressaoIn) {
		Deque<String> operadores = new ArrayDeque<>();
		List<String> saida = new ArrayList<>();
		boolean esperaOperando = true;

		for (String elemento : elementos(expressaoIn)) {
			switch (elemento) {
				case "(" -> {
					if (!esperaOperando) {
						throw new IllegalArgumentException("Falta um operador antes de '('.");
					}
					operadores.push(elemento);
				}
				case ")" -> {
					if (esperaOperando) {
						throw new IllegalArgumentException("Falta um operando antes de ')'.");
					}
					while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
						saida.add(operadores.pop());
					}
					if (operadores.isEmpty()) {
						throw new IllegalArgumentException("Parênteses desbalanceados.");
					}
					operadores.pop();
				}
				default -> {
					if (precedencia(elemento) > 0) {
						if (esperaOperando) {
							throw new IllegalArgumentException("Falta um operando antes de " + elemento + ".");
						}
						while (!operadores.isEmpty()
								&& precedencia(operadores.peek()) >= precedencia(elemento)) {
							saida.add(operadores.pop());
						}
						operadores.push(elemento);
						esperaOperando = true;
					} else {
						if (!esperaOperando) {
							throw new IllegalArgumentException("Falta um operador entre os operandos.");
						}
						saida.add(elemento);
						esperaOperando = false;
					}
				}
			}
		}
		if (esperaOperando) {
			throw new IllegalArgumentException("A expressão termina sem um operando.");
		}
		while (!operadores.isEmpty()) {
			String operador = operadores.pop();
			if (operador.equals("(")) {
				throw new IllegalArgumentException("Parênteses desbalanceados.");
			}
			saida.add(operador);
		}

		this.expressaoPosFixa = String.join(" ", saida);
		System.out.print(this.expressaoPosFixa);
	}

	public Double calcularResultadoInfixaPorPosFixa() {
		return posFixaResultado(this.expressaoPosFixa);
	}

	public String InfixaParaPosFixaParaPreFixa() {
		return posFixaParaPreFixa(this.expressaoPosFixa);
	}

	public Double posFixaResultado(String expressaoPos) {
		Deque<Double> pilha = new ArrayDeque<>();
		for (String elemento : elementos(expressaoPos)) {
			if (precedencia(elemento) == 0) {
				pilha.push(Double.parseDouble(elemento));
				continue;
			}
			exigirDoisOperandos(pilha);
			double direito = pilha.pop();
			double esquerdo = pilha.pop();
			pilha.push(switch (elemento) {
				case "+" -> esquerdo + direito;
				case "-" -> esquerdo - direito;
				case "*" -> esquerdo * direito;
				case "/" -> esquerdo / direito;
				default -> throw new IllegalArgumentException("Operador desconhecido: " + elemento);
			});
		}
		return retirarResultado(pilha);
	}

	public String posFixaParaInfixa(String expressaoPos) {
		return converter(expressaoPos, false);
	}

	public String posFixaParaPreFixa(String expressaoPos) {
		return converter(expressaoPos, true);
	}

	public String preFixaReversa(String expressaoPre) {
		List<String> invertidos = new ArrayList<>(List.of(elementos(expressaoPre)));
		Collections.reverse(invertidos);
		return String.join(" ", invertidos);
	}

	public String preFixaParaInfixa(String expressaoPre) {
		Deque<String> pilha = new ArrayDeque<>();
		for (String elemento : elementos(preFixaReversa(expressaoPre))) {
			if (precedencia(elemento) == 0) {
				pilha.push(elemento);
			} else {
				exigirDoisOperandos(pilha);
				String esquerdo = pilha.pop();
				String direito = pilha.pop();
				pilha.push("( " + esquerdo + " " + elemento + " " + direito + " )");
			}
		}
		this.preFixaParaInfixa = retirarResultado(pilha);
		return this.preFixaParaInfixa;
	}

	public void preFixaParaPosFixa() {
		InfixaParaPosFixa(this.preFixaParaInfixa);
	}

	public Double preFixaResultado() {
		return posFixaResultado(expressaoPosFixa);
	}

	private static String converter(String expressaoPos, boolean prefixa) {
		Deque<String> pilha = new ArrayDeque<>();
		for (String elemento : elementos(expressaoPos)) {
			if (precedencia(elemento) == 0) {
				pilha.push(elemento);
			} else {
				exigirDoisOperandos(pilha);
				String direito = pilha.pop();
				String esquerdo = pilha.pop();
				pilha.push(prefixa ? elemento + " " + esquerdo + " " + direito
						: "(" + esquerdo + " " + elemento + " " + direito + ")");
			}
		}
		return retirarResultado(pilha);
	}

	private static String[] elementos(String expressao) {
		if (expressao == null || expressao.isBlank()) {
			throw new IllegalArgumentException("Informe uma expressão.");
		}
		return expressao.strip().split("\\s+");
	}

	private static int precedencia(String operador) {
		return switch (operador) {
			case "+", "-" -> 1;
			case "*", "/" -> 2;
			default -> 0;
		};
	}

	private static void exigirDoisOperandos(Deque<?> pilha) {
		if (pilha.size() < 2) {
			throw new IllegalArgumentException("Faltam operandos para o operador.");
		}
	}

	private static <T> T retirarResultado(Deque<T> pilha) {
		if (pilha.size() != 1) {
			throw new IllegalArgumentException("A expressão deve produzir um único resultado.");
		}
		return pilha.pop();
	}
}
