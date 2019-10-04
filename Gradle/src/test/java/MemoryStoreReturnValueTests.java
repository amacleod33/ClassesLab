
import static org.junit.Assert.*;
import static student.testingsupport.ReflectionSupport.*;
import java.util.Arrays;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.testingsupport.annotations.ScoringWeight;

/**
 * Test the Variable store(Variable, Integer) return value. Dependency:
 * Variable.class of student. Does store() return true for valid addresses and
 * false for invalid.
 * 
 * @author acsiochi
 * @version 20190930
 */
@ScoringWeight(value = 15)
public class MemoryStoreReturnValueTests {

    private static final Class<?> CUT         = Mu.class;

    private static final Integer  MEMCAPACITY = 10;


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
     * Make a string that represents the arrays of identifiers and data from the
     * given arguments.
     * 
     * @param id
     *            the array of identifiers
     * @param d
     *            the array of data
     * @return string of the form "{ [id, d], ... }"
     */
    private String asString(String[] id, Integer[] d) {
        assert id.length == d.length : "id and d lengths not the same";
        String result = "{ ";
        int len = id.length;

        if (len > 0) {
            for (int i = 0; i < len - 1; i++) {
                result += String.format("[%s, %d], ", id[i], d[i]);
            }
            result += String.format("[%s, %d]", id[len - 1], d[len - 1]);

            if (len < MEMCAPACITY) {
                result += ", ";
            }
        }

        // fill rest of string with null to indicate unused memory
        for (int i = len; i < MEMCAPACITY - 1; i++) {
            result += "null, ";
        }
        if (len < MEMCAPACITY) {
            result += "null";
        }

        result += " }";
        return result;
    }


    /**
     * Get a comma separated list of the strings in the string[] p. The '[' and
     * ']' are removed.
     * 
     * @param p
     *            array of strings
     * @return comma separated list of elements in p
     */
    private String asPlist(Object... p) {
        Object[] params = new Object[p.length];
        for (int i = 0; i < p.length; i++) {
            if (p[i].getClass() == Variable.class) {
                params[i] = p[i].toString();
            }
            else {
                params[i] = p[i];
            }
        }
        String plist = Arrays.toString(params);
        return plist.substring(1, plist.length() - 1);
    }


    /**
     * Test Memory.mname(params) returns expected.
     * 
     * @param id
     *            array of ids, for the identifier part of a Variable
     * @param d
     *            array of ints, for the data part of a Variable
     * @param expected
     *            expected return value
     * @param rClass
     *            type of return value
     * @param mname
     *            method name
     * @param params
     *            parameters of method
     * @return true if expected.equals(Memory.mname(params))
     */
    private Boolean checkMemoryReturn(
        String[] id,
        Integer[] d,
        Object expected,
        Class<?> rClass,
        String mname,
        Object... params) {
        Integer numElts = id.length; // assumes d.length is the same

        // perform setup of Memory under test so it has desired initial state
        Mu m = new Mu();
        Variable v = null;
        for (int i = 0; i < numElts; i++) {
            v = new Variable(id[i], d[i]);
            m.store(v, i);
        }

        String cname = CUT.getCanonicalName();
        String oname = "m";
        String rname = "val";
        String state = asString(id, d);

        Log.initCase();

        String gfmt = "%s %s == %s";
        String g = String.format(gfmt, cname, oname, state);
        Log.given(g);

        String wfmt = "%s = %s.%s(%s)";
        String w = String.format(wfmt, rname, oname, mname, asPlist(params));
        Log.when(w);

        String tfmt = "%s == ";
        String t = String.format(tfmt, rname) + rClass.cast(expected);
        Log.then(t);
        Log.startCase();

        // call the method under test
        Object actual = invoke(m, rClass, mname, params);
        Log.got("" + actual);
        boolean result = expected.equals(actual); // watch out! == of Integers
        Log.resultIs(result);
        Log.endCase();

        return result;
    }


    // ============== store() test cases

    /**
     * <p>
     * does store succeed in putting Variables where adr is at front of fully
     * populated Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {V1, V2, ... }
     *  when val = m.fetch(adr)
     *  then val == true
     * </pre>
     */
    @Test
    public void doesStoreReturnTrueForVariablesStoredAtFrontOfFullMemory() {
        int num = 10;
        int start = 0;
        int end = 2;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = true;
        for (int i = start; i < end; i++) {
            Variable newVar = new Variable(ids[i], d[i]);
            Boolean result;
            result =
                checkMemoryReturn(ids, d, expected, rType, "store", newVar, i);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * <p>
     * does store succeed in putting Variables where adr is at middle of fully
     * populated Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {... V1, V2, ... }
     *  when val = m.fetch(adr)
     *  then val == true
     * </pre>
     */
    @Test
    public void doesStoreReturnTrueForVariablesStoredAtMiddleOfFullMemory() {
        int num = 10;
        int start = 5;
        int end = 7;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = true;
        for (int i = start; i < end; i++) {
            Variable newVar = new Variable(ids[i], d[i]);
            Boolean result;
            result =
                checkMemoryReturn(ids, d, expected, rType, "store", newVar, i);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * <p>
     * does store succeed in putting Variables where adr is at end of fully
     * populated Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {... V1, V2 }
     *  when val = m.fetch(adr)
     *  then val == true
     * </pre>
     */
    @Test
    public void doesStoreReturnTrueForVariablesStoredAtEndOfFullMemory() {
        int num = 10;
        int start = 5;
        int end = 7;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = true;
        for (int i = start; i < end; i++) {
            Variable newVar = new Variable(ids[i], d[i]);
            Boolean result;
            result =
                checkMemoryReturn(ids, d, expected, rType, "store", newVar, i);
            assertTrue(Log.explain(), result);
        }
    }


    /**
     * <p>
     * does store fail in putting Variables when adr is after last valid
     * address, full Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {V0, V1,... Vcapacity-1 }
     *  when val = m.fetch(capacity)
     *  then val == false
     * </pre>
     */
    @Test
    public void doesStoreReturnFalseForInvalidAddressInFullMemory() {
        int num = MEMCAPACITY;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = false;
        Variable newVar = new Variable("DOZEN", 12);
        Boolean result;
        result =
            checkMemoryReturn(ids, d, expected, rType, "store", newVar, badAdr);
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does store fail in putting Variables when adr is after last valid
     * address, half full Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {V0, V1,... Vcapacity/2, null,... }
     *  when val = m.fetch(capacity)
     *  then val == false
     * </pre>
     */
    @Test
    public void doesStoreReturnFalseForInvalidAddressInHalfFullMemory() {
        int num = MEMCAPACITY / 2;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = false;
        Variable newVar = new Variable("DOZEN", 12);
        Boolean result;
        result =
            checkMemoryReturn(ids, d, expected, rType, "store", newVar, badAdr);
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does store fail in putting Variables when adr is after last valid
     * address, empty Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == { null, ... null }
     *  when val = m.fetch(capacity)
     *  then val == false
     * </pre>
     */
    @Test
    public void doesStoreReturnFalseForInvalidAddressInEmptyMemory() {
        int num = 0;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Boolean.class;
        Boolean expected = false;
        Variable newVar = new Variable("DOZEN", 12);
        Boolean result;
        result =
            checkMemoryReturn(ids, d, expected, rType, "store", newVar, badAdr);
        assertTrue(Log.explain(), result);
    }

}
