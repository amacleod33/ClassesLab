
import static org.junit.Assert.*;
import static student.testingsupport.ReflectionSupport.*;
import java.util.Arrays;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.testingsupport.annotations.ScoringWeight;

/**
 * Test the Variable fetch(Integer) method. Dependency: Variable.class of
 * student. Does fetch(adr) return null when adr is an invalid address?
 * 
 * @author acsiochi
 * @version 20190930
 */
@ScoringWeight(value = 15)
public class MemoryUnSuccessfulFetchTests {

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
        String plist = Arrays.toString(p);
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
    private Boolean checkMemoryWith(
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
        String expectedString =
            (expected == null) ? "null" : expected.toString();
        String t = String.format(tfmt, rname) + expectedString;
        Log.then(t);
        Log.startCase();

        // call the method under test
        Object actual = invoke(m, rClass, mname, params);
        Log.got("" + actual);
        boolean result = equals(expected, actual); // watch out! == of Integers
        Log.resultIs(result);
        Log.endCase();

        return result;
    }


    /**
     * Determine if the two Variables are equal.
     * 
     * @param exp
     *            expected Variable
     * @param act
     *            actual Variable
     * @return true if they are equal address, or contain same id and data
     */
    private Boolean equals(Object exp, Object act) {
        if (exp == act) {
            return true; // same object
        }
        if (exp == null) { // act can't be null, so not equal
            return false;
        }
        // neither are null, check their attributes
        Integer ed = ((Variable)exp).getData();
        Integer ad = ((Variable)act).getData();
        String eid = ((Variable)exp).getIdentifier();
        String aid = ((Variable)act).getIdentifier();
        return (ed.equals(ad) && eid.equals(aid));
    }

    // ============== unsuccessful-fetch() test cases


    /**
     * <p>
     * does fetch(a) return null when a is an invalid address of fully populated
     * Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {V0, V1, Vcapacity-1}
     *  when val = m.fetch(capacity)...
     *  then val == null
     * </pre>
     */
    @Test
    public void doesFetchReturnNullForAddressPastLastAddressInFullMemory() {
        int num = MEMCAPACITY;
        int idLen = 1;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, idLen);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Variable.class;
        Variable expected = null;
        // check both id and data
        Boolean result;
        result = checkMemoryWith(ids, d, expected, rType, "fetch", badAdr);
        assertTrue(Log.explain(), result);

    }


    /**
     * <p>
     * does fetch(a) return null when a is an invalid address of half populated
     * Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == {V0, V1, ... Vcapacity/2}
     *  when val = m.fetch(capacity)...
     *  then val == null
     * </pre>
     */
    @Test
    public void doesFetchReturnNullForAddressPastLastAddressInHalfFullMemory() {
        int num = MEMCAPACITY / 2;
        int idLen = 1;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, idLen);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Variable.class;
        Variable expected = null;
        // check both id and data
        Boolean result;
        result = checkMemoryWith(ids, d, expected, rType, "fetch", badAdr);
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does fetch(a) return null when a is an invalid address of empty Memory.
     * </p>
     * 
     * <pre>
     * given Memory m == { }
     *  when val = m.fetch(capacity)...
     *  then val == null
     * </pre>
     */
    @Test
    public void doesFetchReturnNullForAddressPastLastAddressInEmptyMemory() {
        int num = 0;
        int idLen = 1;
        int badAdr = MEMCAPACITY;
        String[] ids = Rnd.uniqueRndIds(num, idLen);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Variable.class;
        Variable expected = null;
        // check both id and data
        Boolean result;
        result = checkMemoryWith(ids, d, expected, rType, "fetch", badAdr);
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does fetch(a) return null when a is negative.
     * </p>
     * 
     * <pre>
     * given Memory m == {V0, V1, ... Vcapacity-1}
     *  when val = m.fetch(capacity)...
     *  then val == null
     * </pre>
     */
    @Test
    public void doesFetchReturnNullForNegativeAddressInFullMemory() {
        int num = MEMCAPACITY;
        int idLen = 1;
        int badAdr = -1;
        String[] ids = Rnd.uniqueRndIds(num, idLen);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.uniqueRndInts(num, min, max, noZero);
        Class<?> rType = Variable.class;
        Variable expected = null;
        // check both id and data
        Boolean result;
        result = checkMemoryWith(ids, d, expected, rType, "fetch", badAdr);
        assertTrue(Log.explain(), result);

    }

}
