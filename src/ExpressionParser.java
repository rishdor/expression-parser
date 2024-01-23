import java.util.*;

public class ExpressionParser {
    // Set of operators supported by the parser
    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", "^");

    /**
     * Parses a mathematical expression represented as a string and returns the result.
     *
     * @param expression The mathematical expression to parse.
     * @return The result of the expression.
     * @throws IllegalArgumentException If the expression is invalid.
     */
    public static double parse(String expression) {
        if (!areParenthesesBalanced(expression)) {
            throw new IllegalArgumentException("Unbalanced parentheses in expression");
        }

        Queue<String> queue = new LinkedList<>(List.of(expression.split(" ")));
        return parseExpression(queue);
    }

    /**
     * Checks if the parentheses in the expression are balanced.
     *
     * @param expression The expression to check.
     * @return True if the parentheses are balanced, false otherwise.
     */
    private static boolean areParenthesesBalanced(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    /**
     * Parses an expression from the queue.
     *
     * @param queue The queue containing the expression.
     * @return The result of the expression.
     */
    private static double parseExpression(Queue<String> queue) {
        double number = parseTerm(queue);

        while (!queue.isEmpty() && !queue.peek().equals(")") && (queue.peek().equals("+") || queue.peek().equals("-"))) {
            String operator = queue.poll();
            double nextNumber = parseTerm(queue);

            switch (operator) {
                case "+":
                    number += nextNumber;
                    break;
                case "-":
                    number -= nextNumber;
                    break;
            }
        }

        return number;
    }

    /**
     * Parses a term from the queue.
     *
     * @param queue The queue containing the term.
     * @return The result of the term.
     */
    private static double parseTerm(Queue<String> queue) {
        double number = parseFactor(queue);

        while (!queue.isEmpty() && !queue.peek().equals(")") && (queue.peek().equals("*") || queue.peek().equals("/") || queue.peek().equals("^"))) {
            String operator = queue.poll();
            double nextNumber = parseFactor(queue);

            switch (operator) {
                case "*":
                    number *= nextNumber;
                    break;
                case "/":
                    if (nextNumber == 0) {
                        throw new ArithmeticException("Division by zero is not allowed");
                    }
                    number /= nextNumber;
                    break;
                case "^":
                    number = Math.pow(number, nextNumber);
                    break;
            }
        }

        return number;
    }

    /**
     * Parses a factor from the queue.
     *
     * @param queue The queue containing the factor.
     * @return The result of the factor.
     * @throws IllegalArgumentException If the factor is invalid.
     */
    private static double parseFactor(Queue<String> queue) {
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        String factor = queue.poll();

        if (factor.equals("(")) {
            double number = parseExpression(queue);

            if (queue.isEmpty() || !queue.poll().equals(")")) {
                throw new IllegalArgumentException("Unbalanced parentheses in expression");
            }

            return number;
        } else if (OPERATORS.contains(factor)) {
            throw new IllegalArgumentException("Unexpected operator: " + factor);
        } else {
            return Double.parseDouble(factor);
        }
    }
}