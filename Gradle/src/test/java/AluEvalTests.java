
import static org.junit.Assert.*;
import static student.testingsupport.ReflectionSupport.*;
import java.util.Arrays;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.testingsupport.annotations.ScoringWeight;

/**
 * Test the eval(expr) method of Alu.
 * 
 * @author acsiochi
 * @version 20190926
 */
@ScoringWeight(value = 100)
public class AluEvalTests {

    private static final Class<?> CUT = Alu.class;


    /**
     * Initialize the logs.
     */
    @BeforeClass
    public static void setUpAll() {
        Log.init();
    }


    /**
     * Print the logs for this test class.
     */
    @AfterClass
    public static void afterAll() {
        Log.endTests();
    }


    /**
     * Evaluate the given expression.
     * 
     * @param op1
     *            operand1
     * @param op
     *            operator
     * @param op2
     *            operand2
     * @return value of expression if expression is valid; 1 else
     */
    private static int evalX(int op1, char op, int op2) {
        switch (op) {
            case '+':
                return op1 + op2;
            case '*':
                return op1 * op2;
            case '-':
                return op1 - op2;
            case '/':
                return op1 / op2;
            case '%':
                return op1 % op2;
            default:
                return 1;
        }
    }


    /**
     * Test tha eval(String[]) returns correct values.
     * 
     * @param omm1
     *            min and max range of op1
     * @param op
     *            operator
     * @param omm2
     *            min and max range of op2
     * @return true if eval() correctly calculates the expression
     */
    private boolean evalTest(int[] omm1, char op, int[] omm2) {
        String cname = CUT.getCanonicalName(); // class name
        String mname = "eval"; // method name
        String oname = "alu"; // object name
        String rname = "val"; // result name
        int o1 = Rnd.rndInt(omm1[0], omm1[1], omm1[2] != 0);
        int o2 = Rnd.rndInt(omm2[0], omm2[1], omm2[2] != 0);
        String[] expr = { "" + o1, "" + op, "" + o2 };
        String param = Arrays.toString(expr);
        int expected = evalX(o1, op, o2);
        Log.initCase();
        String gfmt = "%s %s = new %s()";
        String g = String.format(gfmt, cname, oname, cname);
        Log.given(g);
        String wfmt = "%s = %s.%s(%s)";
        String w = String.format(wfmt, rname, oname, mname, param);
        Log.when(w);
        String tfmt = "%s == %d";
        String t = String.format(tfmt, rname, expected);
        Log.then(t);
        Log.startCase();
        // Ugh! reflection and varargs don't mix well. have to wrap String[]
        // in an Object[] to get it past varargs
        Object[] marg = { expr };
        int actual = (int)invoke(CUT, int.class, mname, marg);
        Log.got("" + actual);
        boolean allEqual = (expected == actual);
        Log.resultIs(allEqual);
        Log.endCase();
        return allEqual;
    }


    /**
     * Test that eval(String[]) throws an exception.
     * 
     * @param omm1
     *            min and max range of op1
     * @param op
     *            operator
     * @param omm2
     *            min and max range of op2
     * @param exEx
     *            expected exception
     * @return true if expected was thrown
     */
    private
        boolean
        evalTestException(int[] omm1, char op, int[] omm2, Class<?> exEx) {
        String cname = CUT.getCanonicalName(); // class name
        String mname = "eval"; // method name
        String oname = "avm"; // object name
        String rname = "val"; // result name
        int o1 = Rnd.rndInt(omm1[0], omm1[1], omm1[2] != 0);
        int o2 = Rnd.rndInt(omm2[0], omm2[1], omm2[2] != 0);
        String[] expr = { "" + o1, "" + op, "" + o2 };
        String param = Arrays.toString(expr);
        Log.initCase();
        String gfmt = "%s %s = new %s()";
        String g = String.format(gfmt, cname, oname, cname);
        Log.given(g);
        String wfmt = "%s = %s.%s(%s)";
        String w = String.format(wfmt, rname, oname, mname, param);
        Log.when(w);
        String tfmt = "%s should be thrown";
        String t = String.format(tfmt, exEx);
        Log.then(t);
        Log.startCase();
        // Ugh! reflection and varargs don't mix well. have to wrap String[]
        // in an Object[] to get it past varargs
        Object[] marg = { expr };
        Throwable actual = null;
        try {
            invoke(CUT, int.class, mname, marg);
        }
        catch (Throwable x) {
            actual = x;
        }
        boolean expExWasThrown = exEx.isInstance(actual);
        Log.got("" + ((actual == null) ? "no or incorrect exception" : actual));
        Log.resultIs(expExWasThrown);
        Log.endCase();
        return expExWasThrown;
    }


    // ============== ADDITION

    /**
     * doesPosAddPosGivePosSum
     */
    @Test
    public void doesPosAddPosGivePosSum() {
        int[] op1 = { 1, 50, 1 };
        int[] op2 = { 1, 50, 1 };
        char op = '+';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosNAddNegNplusPosMGivePosSum
     */
    @Test
    public void doesPosNAddNegNplusPosMGivePosSum() {
        int[] op1 = { 1, 50, 1 };
        int[] op2 = { -50, -1, 1 };
        char op = '+';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosNAddNegNminusPosMGiveNegSum
     */
    @Test
    public void doesPosNAddNegNminusPosMGiveNegSum() {
        int[] op1 = { 1, 10, 1 };
        int[] op2 = { -50, -10, 1 };
        char op = '+';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    // ============== SUBTRACTION

    /**
     * doesPosSubSmallerPosGivePosSum
     */
    @Test
    public void doesPosSubSmallerPosGivePosSum() {
        int[] op1 = { 30, 50, 1 };
        int[] op2 = { 1, 30, 1 };
        char op = '-';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosSubBiggerPosMGiveNegSum
     */
    @Test
    public void doesPosSubBiggerPosMGiveNegSum() {
        int[] op1 = { 1, 30, 1 };
        int[] op2 = { 30, 50, 1 };
        char op = '-';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosSubNegGivePosSum
     */
    @Test
    public void doesPosSubNegGivePosSum() {
        int[] op1 = { 1, 10, 1 };
        int[] op2 = { -50, -10, 1 };
        char op = '-';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesNegSubSmallerNegGivePosSum
     */
    @Test
    public void doesNegSubSmallerNegGivePosSum() {
        int[] op1 = { -10, -1, 1 };
        int[] op2 = { -50, -10, 1 };
        char op = '-';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesNegSubBiggerNegGiveNegSum
     */
    @Test
    public void doesNegSubBiggerNegGiveNegSum() {
        int[] op1 = { -50, -10, 1 };
        int[] op2 = { -10, -1, 1 };
        char op = '-';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }

    // ============== MULTIPLICATION


    /**
     * doesNegMultNegGivePosProduct
     */
    @Test
    public void doesNegMultNegGivePosProduct() {
        int[] op1 = { -50, -10, 1 };
        int[] op2 = { -10, -1, 1 };
        char op = '*';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesNegMultPosGiveNegProduct
     */
    @Test
    public void doesNegMultPosGiveNegProduct() {
        int[] op1 = { -50, -10, 1 };
        int[] op2 = { 10, 20, 1 };
        char op = '*';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosMultNegGiveNegProduct
     */
    @Test
    public void doesPosMultNegGiveNegProduct() {
        int[] op1 = { 5, 10, 1 };
        int[] op2 = { -10, -1, 1 };
        char op = '*';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosMultPosGivePosProduct
     */
    @Test
    public void doesPosMultPosGivePosProduct() {
        int[] op1 = { 5, 10, 1 };
        int[] op2 = { 10, 12, 1 };
        char op = '*';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }

    // set number of multiplication trials
    private int trials = 8;


    /**
     * does0MultNonZeroGive0
     */
    @Test
    public void does0MultNonZeroGive0() {
        int[] op1 = { 0, 1, 0 };
        int[] op2 = { -100, 101, 1 };
        char op = '*';
        for (int i = 0; i < trials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * doesNonZeroMult0Give0
     */
    @Test
    public void doesNonZeroMult0Give0() {
        int[] op1 = { -100, 101, 1 };
        int[] op2 = { 0, 1, 0 };
        char op = '*';
        for (int i = 0; i < trials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * does0Mult0Give0
     */
    @Test
    public void does0Mult0Give0() {
        int[] op1 = { 0, 1, 0 };
        char op = '*';
        boolean result = evalTest(op1, op, op1);
        assertTrue(Log.explain(), result);
    }

    // ============== DIVISION


    /**
     * doesNegDivNegGivePosProduct
     */
    @Test
    public void doesNegDivNegGivePosProduct() {
        int[] op1 = { -50, -10, 1 };
        int[] op2 = { -10, -1, 1 };
        char op = '/';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesNegDivPosGiveNegProduct
     */
    @Test
    public void doesNegDivPosGiveNegProduct() {
        int[] op1 = { -50, -10, 1 };
        int[] op2 = { 10, 20, 1 };
        char op = '/';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosDivNegGiveNegProduct
     */
    @Test
    public void doesPosDivNegGiveNegProduct() {
        int[] op1 = { 5, 10, 1 };
        int[] op2 = { -10, -1, 1 };
        char op = '/';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }


    /**
     * doesPosDivPosGivePosProduct
     */
    @Test
    public void doesPosDivPosGivePosProduct() {
        int[] op1 = { 5, 10, 1 };
        int[] op2 = { 10, 12, 1 };
        char op = '/';
        boolean result = evalTest(op1, op, op2);
        assertTrue(Log.explain(), result);
    }

    // set number of division and mod trials
    private int dtrials = 8;


    /**
     * does0DivNonZeroGive0
     */
    @Test
    public void does0DivNonZeroGive0() {
        int[] op1 = { 0, 1, 0 };
        int[] op2 = { -100, 101, 1 };
        char op = '/';
        for (int i = 0; i < dtrials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * doesNonZeroDiv0ThrowAE
     */
    @Test
    public void doesNonZeroDiv0ThrowAE() {
        int[] op1 = { -100, 101, 1 };
        int[] op2 = { 0, 1, 0 };
        char op = '/';
        for (int i = 0; i < dtrials; i++) {
            boolean result =
                evalTestException(op1, op, op2, ArithmeticException.class);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * does0Div0ThrowArithmeticException
     */
    @Test
    public void does0Div0ThrowArithmeticException() {
        int[] op1 = { 0, 1, 0 };
        char op = '/';
        boolean result =
            evalTestException(op1, op, op1, ArithmeticException.class);
        assertTrue(Log.explain(), result);
    }


    // ============== MODULO

    /**
     * doesPosModPosGivePosRemainder
     */
    @Test
    public void doesPosModPosGivePosRemainder() {
        int[] op1 = { 5, 25, 1 };
        int[] op2 = { 7, 12, 1 };
        char op = '%';
        for (int i = 0; i < dtrials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * doesNegModPosGiveNegRemainder
     */
    @Test
    public void doesNegModPosGiveNegRemainder() {
        int[] op1 = { -25, -5, 1 };
        int[] op2 = { 7, 12, 1 };
        char op = '%';
        for (int i = 0; i < dtrials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * does0ModNonZeroGive0
     */
    @Test
    public void does0ModNonZeroGive0() {
        int[] op1 = { 0, 1, 0 };
        int[] op2 = { -100, 101, 1 };
        char op = '%';
        for (int i = 0; i < dtrials; i++) {
            boolean result = evalTest(op1, op, op2);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * does0Mod0ThrowArithmeticException
     */
    @Test
    public void does0Mod0ThrowArithmeticException() {
        int[] op1 = { 0, 1, 0 };
        char op = '%';
        boolean result =
            evalTestException(op1, op, op1, ArithmeticException.class);
        assertTrue(Log.explain(), result);
    }

}
