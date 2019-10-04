
import static org.junit.Assert.*;
import static student.testingsupport.ReflectionSupport.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import student.testingsupport.annotations.ScoringWeight;

/**
 * Test the Memory.capacity() method. Dependency: Variable.class of student.
 * 
 * @author acsiochi
 * @version 20190930
 */
@ScoringWeight(value = 15)
public class MemoryCapacityTests {

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
        String param = "";
        String rname = "val";
        String state = asString(id, d);

        Log.initCase();

        String gfmt = "%s %s == %s";
        String g = String.format(gfmt, cname, oname, state);
        Log.given(g);

        String wfmt = "%s = %s.%s(%s)";
        String w = String.format(wfmt, rname, oname, mname, param);
        Log.when(w);

        String tfmt = "%s == %d";
        String t = String.format(tfmt, rname, expected);
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


    // ============== capacity() test cases

    /**
     * <p>
     * does capacity() return MEMCAPACITY when Memory is empty.
     * </p>
     * 
     * <pre>
     * given Memory m == { }
     *  when val = m.cpacity()
     *  then val == MEMCAPACITY
     * </pre>
     */
    @Test
    public void doesCapacityGetDefaultCapacityInEmptyMemory() {
        int num = 0;
        String[] ids = Rnd.rndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.rndInts(num, min, max, noZero);
        Integer expected = MEMCAPACITY;
        Boolean result =
            checkMemoryWith(ids, d, expected, Integer.class, "capacity");
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does capacity() return MEMCAPACITY when Memory is half full.
     * </p>
     * 
     * <pre>
     * given Memory m == { [v, d] ...}
     *  when val = m.cpacity()
     *  then val == MEMCAPACITY
     * </pre>
     */
    @Test
    public void doesCapacityGetDefaultInHalfFullMemory() {
        int num = MEMCAPACITY / 2;
        String[] ids = Rnd.rndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.rndInts(num, min, max, noZero);
        Integer expected = MEMCAPACITY;
        Boolean result =
            checkMemoryWith(ids, d, expected, Integer.class, "capacity");
        assertTrue(Log.explain(), result);
    }


    /**
     * <p>
     * does capacity() return MEMCAPACITY when Memory is full.
     * </p>
     * 
     * <pre>
     * given Memory m == { [v, d] ...}
     *  when val = m.cpacity()
     *  then val == MEMCAPACITY
     * </pre>
     */
    @Test
    public void doesCapacityGetDefaultInFullMemory() {
        int num = MEMCAPACITY;
        String[] ids = Rnd.rndIds(num, 1);
        int min = -15;
        int max = 15;
        Boolean noZero = false;
        Integer[] d = Rnd.rndInts(num, min, max, noZero);
        Integer expected = MEMCAPACITY;
        Boolean result =
            checkMemoryWith(ids, d, expected, Integer.class, "capacity");
        assertTrue(Log.explain(), result);
    }

}
