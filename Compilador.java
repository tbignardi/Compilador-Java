import java.util.*;

public class Compilador {
    public static void main(String[] args) {
        Map<String, Double> vars = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String continueCalculation;
        do {
            System.out.println("Digite uma expressão matemática para a variável 'a':");
            assignExpression(scanner.nextLine().trim(), vars);
            System.out.println("a = " + vars.get("a"));
            System.out.println("Digite uma expressão matemática para a variável 'b':");
            assignExpression(scanner.nextLine().trim(), vars);
            System.out.println("b = " + vars.get("b"));
            System.out.println("Agora, digite uma expressão matemática para a variável 'c' usando 'a' e 'b':");
            assignExpression(scanner.nextLine().trim(), vars);
            System.out.println("c = " + vars.get("c"));
            System.out.println("Deseja realizar uma nova operação? (s/n)");
            continueCalculation = scanner.nextLine();
        } while (continueCalculation.equalsIgnoreCase("s"));
    }

    public static void assignExpression(String input, Map<String, Double> vars) {
        String[] parts = input.split("=");
        if (parts.length == 2) {
            String varName = parts[0].trim();
            String expression = parts[1].trim();
            double result = evaluate(expression, vars);
            vars.put(varName, result);
        } else {
            throw new IllegalArgumentException("Erro de sintaxe!");
        }
    }

    public static double evaluate(String expression, Map<String, Double> vars) {
        String[] tokens = expression.split("\\s");
        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();
        for (String token : tokens) {
            if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (!ops.peek().equals("(")) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                while (!ops.empty() && hasPrecedence(token, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(token);
            } else {
                if (vars.containsKey(token)) {
                    values.push(vars.get(token));
                } else {
                    try {
                        values.push(Double.parseDouble(token));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Token inválido: " + token);
                    }
                }
            }
        }
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        return values.pop();
    }

    public static boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")")) {
            return false;
        }
        if ((op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"))) {
            return false;
        }
        return true;
    }

    public static double applyOp(String op, double b, double a) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new UnsupportedOperationException("Não é possível dividir por zero");
                }
                return a / b;
        }
        return 0;
    }
}
