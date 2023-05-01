package util;

import java.util.ArrayList;
import java.util.Stack;

public class Calculadora {
	private String expressaoPosFixa;
	private String preFixaParaInfixa;

	public void InfixaParaPosFixa(String expressaoIn) {
		Stack<String> pilha = new Stack<>();
		String[] elementos = expressaoIn.split(" ");
		ArrayList<String> posFixa = new ArrayList<>();

		for (String elemento : elementos) {
			String operador = null;
			if (elemento.equals("(") || elemento.equals("+") || elemento.equals("-") || elemento.equals("*")
					|| elemento.equals("/")) {

				if (!pilha.empty()) {
					String topo = pilha.peek();
					// mesmas precedências, portanto, sai o operador que veio primeiro
					if (topo.equals("+") && elemento.equals("-")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("+") && elemento.equals("+")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("-") && elemento.equals("-")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("-") && elemento.equals("+")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("*") && elemento.equals("/")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("*") && elemento.equals("*")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("/") && elemento.equals("/")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("/") && elemento.equals("*")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					}

					// precedências diferentes, portanto, somente adiciono na pilha

					else if (topo.equals("+") && elemento.equals("*")) {
						pilha.push(elemento);
					} else if (topo.equals("+") && elemento.equals("/")) {
						pilha.push(elemento);
					}

					else if (topo.equals("-") && elemento.equals("*")) {
						pilha.push(elemento);
					} else if (topo.equals("-") && elemento.equals("/")) {
						pilha.push(elemento);

					} else if (topo.equals("/") && elemento.equals("-")) {
						posFixa.add(pilha.pop());
						pilha.push(elemento);
					} else if (topo.equals("/") && elemento.equals("+")) {
						posFixa.add(pilha.pop());
					}

					else if (topo.equals("*") && elemento.equals("-")) {
						posFixa.add(pilha.pop());
					} else if (topo.equals("*") && elemento.equals("+")) {
						posFixa.add(pilha.pop());
					} else {
						pilha.push(elemento);

					}
				} else if (pilha.empty()) {
					if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
						posFixa.add(elemento);
					} else {
						pilha.push(elemento);

					}
				}

			} else if (elemento.equals(")")) {
				while (!pilha.peek().equals("(")) {
					operador = pilha.pop();
					posFixa.add(operador);
				}
				pilha.pop(); // retira o "(" da pilha
			} else {
				posFixa.add(elemento);
			}
		}

		StringBuilder sb = new StringBuilder();
		String separador = " ";

		// printa a expressao em posfixa
		for (String elemento : posFixa) {
			sb.append(elemento).append(separador);
			this.expressaoPosFixa = sb.toString();
			System.out.print(elemento + " ");

		}
	}

	public Double calcularResultadoInfixaPorPosFixa() {
		return posFixaResultado(this.expressaoPosFixa);
	}

	public String InfixaParaPosFixaParaPreFixa() {
		return posFixaParaPreFixa(this.expressaoPosFixa);
	}

	public Double posFixaResultado(String expressaoPos) {
		Stack<Double> pilha = new Stack<>();

		String[] elementos = expressaoPos.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				double operando2 = pilha.pop();
				double operando1 = pilha.pop();

				if (elemento.equals("+")) {
					pilha.push(operando1 + operando2);
				}

				else if (elemento.equals("-")) {
					pilha.push(operando1 - operando2);
				}

				else if (elemento.equals("*")) {
					pilha.push(operando1 * operando2);

				}

				else if (elemento.equals("/")) {
					pilha.push(operando1 / operando2);
				}
			} else {
				double num = Double.parseDouble(String.valueOf(elemento));
				pilha.push(num);
			}
		}
		return pilha.pop();

	}

	public String posFixaParaInfixa(String expressaoPos) {
		Stack<String> pilha = new Stack<>();
		String[] elementos = expressaoPos.split(" ");

		for (String elemento : elementos) {
			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				String operando2 = pilha.pop();
				String operando1 = pilha.pop();
				String expressao = "(" + operando1 + " " + elemento + " " + operando2 + ")";
				pilha.push(expressao);
			} else {
				pilha.push(elemento);
			}
		}

		return pilha.pop();
	}

	public String posFixaParaPreFixa(String expressaoPos) {
		Stack<String> pilha = new Stack<>();
		String[] elementos = expressaoPos.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				String operando2 = pilha.pop();
				String operando1 = pilha.pop();
				String expressao = elemento + " " + operando1 + " " + operando2;
				pilha.push(expressao);
			} else {
				pilha.push(elemento);
			}
		}

		return pilha.pop();
	}

	// Inverte a expressão Pré-fixa para realixar a conversão para Infixa
	// futuramente.
	public String preFixaReversa(String expressaoPre) {
		Stack<String> pilha = new Stack<>();
		String[] elementos = expressaoPre.split(" ");

		for (String elemento : elementos) {
			pilha.push(elemento);
		}

		String preFixaReversa = "";
		while (!pilha.empty()) {
			preFixaReversa += pilha.pop() + " ";
		}

		return preFixaReversa.trim();
	}

	public String preFixaParaInfixa(String expressaoPre) {
		String preFixaReversaStr = preFixaReversa(expressaoPre);

		Stack<String> pilha = new Stack<>();
		String[] elementos = preFixaReversaStr.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				String operando2 = pilha.pop();
				String operando1 = pilha.pop();
				String expressao = "( " + operando2 + " " + elemento + " " + operando1 + " )";
				pilha.push(expressao);
			} else {
				pilha.push(elemento);
			}
		}
		this.preFixaParaInfixa = pilha.peek();
		return pilha.pop();
	}

	public void preFixaParaPosFixa() {
		InfixaParaPosFixa(this.preFixaParaInfixa);

	}

	public Double preFixaResultado() {
		return posFixaResultado(expressaoPosFixa);
	}

}
