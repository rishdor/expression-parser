import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {

    @Test
    public void parsesSimpleAddition() {
        double result = ExpressionParser.parse("1 + 2");
        assertEquals(3, result);
    }

    @Test
    public void parsesSimpleSubtraction() {
        double result = ExpressionParser.parse("5 - 2");
        assertEquals(3, result);
    }

    @Test
    public void parsesSimpleMultiplication() {
        double result = ExpressionParser.parse("2 * 3");
        assertEquals(6, result);
    }

    @Test
    public void parsesSimpleDivision() {
        double result = ExpressionParser.parse("6 / 2");
        assertEquals(3, result);
    }

    @Test
    public void parsesExpressionWithParentheses() {
        double result = ExpressionParser.parse("( 2 + 3 ) * 4");
        assertEquals(20, result);
    }

    @Test
    public void parsesExpressionWithNestedParentheses() {
        double result = ExpressionParser.parse("( 2 + ( 3 * 4 ) ) / 2");
        assertEquals(7, result);
    }

    @Test
    public void parsesExpressionWithMultipleOperators() {
        double result = ExpressionParser.parse("2 + 3 * 4 - 6 / 2");
        assertEquals(11, result);
    }

    @Test
    public void parsesPowerWithPositiveExponent() {
        double result = ExpressionParser.parse("2 ^ 3");
        assertEquals(8, result);
    }

    @Test
    public void parsesPowerWithNegativeExponent() {
        double result = ExpressionParser.parse("2 ^ -3");
        assertEquals(0.125, result);
    }

    @Test
    public void parsesPowerWithZeroExponent() {
        double result = ExpressionParser.parse("2 ^ 0");
        assertEquals(1, result);
    }

    @Test
    public void parsesComplexExpression() {
        double result = ExpressionParser.parse("( 2 + 3 * 4 - 6 / 2 ) ^ 2 / 11");
        assertEquals(11, result);
    }

    @Test
    public void throwsExceptionForInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("2 + + 3"));
    }

    @Test
    public void throwsExceptionForUnbalancedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("( 2 + 3 ) )"));
    }

    @Test
    public void throwsExceptionForDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> ExpressionParser.parse("2 / 0"));
    }
}