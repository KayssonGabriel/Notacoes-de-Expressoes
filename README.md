# Notações de expressões

Exercício de Estrutura de Dados em Java sobre notações infixa, pós-fixa e pré-fixa. Um menu lê uma expressão, tenta convertê-la para as outras notações e calcula o resultado usando pilhas.

## Como executar

Com JDK 17 ou superior, abra o PowerShell na raiz.

```powershell
$fontes = Get-ChildItem -Path Calculadora/src -Recurse -Filter '*.java' | ForEach-Object FullName
javac -encoding UTF-8 -d build $fontes
java -cp build application.Program
```

Separe cada operando, operador e parêntese por um espaço. Os operadores implementados são `+`, `-`, `*` e `/`. Para obter um resultado de exemplo, selecione a opção 2 (pós-fixa) e digite `2 3 +`. O resultado é `5.0`.

## Arquivos e limitações

- [Program.java](Calculadora/src/application/Program.java) lê a notação e a expressão digitadas no console.
- [Calculadora.java](Calculadora/src/util/Calculadora.java) converte e avalia as expressões.

A conversão infixa aplica a precedência de multiplicação e divisão e respeita parênteses. A expressão `2 + 3 * 4 + 5`, por exemplo, retorna `19.0`. Expressões com parênteses desbalanceados ou operandos ausentes são rejeitadas.

[Vídeo de demonstração](https://youtu.be/Kt9TS0MK80U)
