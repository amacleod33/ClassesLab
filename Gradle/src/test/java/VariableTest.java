import static org.junit.Assert.*;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.testingsupport.annotations.ScoringWeight;

/**
 * Test the Variable class.
 * 
 * @author acsiochi
 * @version 20190925
 */
@ScoringWeight(value = 100)
public class VariableTest {

    private static Random rand = new Random();


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
     * Create a random string of length len.
     * 
     * @param len
     *            length of string to create
     * @return random string
     */
    private static String rndId(int len) {
        String caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lows = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String letters = caps + lows;
        String all = letters + digits + digits + digits;
        String rnd = "" + letters.charAt(rand.nextInt(letters.length()));
        if (len < 0) {
            len *= -1;
        }
        for (int i = 0; i < len - 1; i++) {
            rnd += all.charAt(rand.nextInt(all.length()));
        }
        return rnd;
    }


    /**
     * doesGetDataMatchThatSetByCtor
     */
    @Test
    public void doesGetDataMatchThatSetByCtor() {
        String oname = "v";
        String rname = "val";

        String msig = "getData()";
        String id = rndId(3);
        int d = rand.nextInt(50);
        String expected = "" + d;
        Log.initCase();
        Log.given(
            String.format(
                "Variable %s = new Variable(\"%s\", %d)",
                oname,
                id,
                d));
        Log.when(String.format("%s = %s.%s", rname, oname, msig));
        Log.then(String.format("%s == %s", rname, expected));

        Log.startCase();
        Variable v = new Variable(id, d);
        String actual = "" + v.getData();
        Log.got(actual);

        boolean result = expected.equals(actual);
        Log.resultIs(result);
        Log.endCase();
        assertTrue(Log.explain(), result);
    }


    /**
     * doesGetIdMatchThatSetByCtor
     */
    @Test
    public void doesGetIdMatchThatSetByCtor() {
        String oname = "v";
        String rname = "val";
        String msig = "getIdentifier()";
        String id = rndId(3);
        int d = rand.nextInt(50);
        String expected = id;
        Log.initCase();
        Log.given(
            String.format(
                "Variable %s = new Variable(\"%s\", %d)",
                oname,
                id,
                d));
        Log.when(String.format("%s = %s.%s", rname, oname, msig));
        Log.then(String.format("%s == %s", rname, expected));

        Log.startCase();
        Variable v = new Variable(id, d);
        String actual = v.getIdentifier();
        Log.got(actual);

        boolean result = expected.equals(actual);
        Log.resultIs(result);
        Log.endCase();
        assertTrue(Log.explain(), result);
    }


    /**
     * doesGetDataMatchThatSetBySetData
     */
    @Test
    public void doesGetDataMatchThatSetBySetData() {
        String oname = "v";
        String rname = "val";
        String id = rndId(3);
        int d = rand.nextInt(50);
        String mname = "getData";
        String expected = "" + d;
        Log.initCase();
        Log.given(
            String.format("Variable %s; %s.setData(%s)", oname, oname, "" + d));
        Log.when(String.format("%s = %s.%s()", rname, oname, mname));
        Log.then(String.format("%s == %s", rname, expected));

        Log.startCase();
        Variable v = new Variable(id, d + 20);
        v.setData(d);
        String actual = "" + v.getData();
        Log.got(actual);

        boolean result = expected.equals(actual);
        Log.resultIs(result);
        Log.endCase();
        assertTrue(Log.explain(), result);
    }


    /**
     * doesGetIdMatchThatSetBySetId
     */
    @Test
    public void doesGetIdMatchThatSetBySetId() {
        String oname = "v";
        String rname = "val";
        String id = rndId(3);
        int d = rand.nextInt(50);
        String mname = "getIdentifier";
        String expected = id;
        Log.initCase();
        Log.given(
            String.format(
                "Variable %s; %s.setIdentifier(\"%s\")",
                oname,
                oname,
                id));
        Log.when(String.format("%s = %s.%s()", rname, oname, mname));
        Log.then(String.format("%s == \"%s\"", rname, expected));

        Log.startCase();
        Variable v = new Variable("z", d);
        v.setIdentifier(id);
        String actual = v.getIdentifier();
        Log.got(actual);

        boolean result = expected.equals(actual);
        Log.resultIs(result);
        Log.endCase();
        assertTrue(Log.explain(), result);
    }
//
//
// /**
// * doesToStringContainIdAndData
// */
// @Test
// public void doesToStringContainIdAndData() {
// String oname = "v";
// String rname = "val";
// String id = rndId(3);
// int d = rand.nextInt(50);
// String mname = "toString";
// String expected = String.format("[%s, %d]", id, d);
// Log.initCase();
// Log.given(
// String.format(
// "Variable %s = new Variable(\"%s\", %d)",
// oname,
// id,
// d));
// Log.when(String.format("%s = %s.%s()", rname, oname, mname));
// Log.then(String.format("%s == %s", rname, expected));
//
// Log.startCase();
// Variable v = new Variable("z", d);
// v.setIdentifier(id);
// String actual = v.toString();
// Log.got(actual);
//
// boolean result = expected.equals(actual);
// Log.resultIs(result);
// Log.endCase();
// assertTrue(Log.explain(), result);
// }

}
