
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author amacleod
 * @version 10072019
 */
public class Alu {

    /**
     * takes list of [x, "+", y]
     * 
     * @param x
     *            list of [x, "+", y]
     * @return evaluation based on x[1]
     */
    public static Integer eval(String[] x) {
        int firstVar = Integer.parseInt(x[0]);
        int secondVar = Integer.parseInt(x[2]);
        if (x[1].equals("+")) {
            return firstVar + secondVar;

        }
        else if (x[1].contentEquals("-")) {
            return firstVar - secondVar;
        }
        else if (x[1].contentEquals("*")) {
            return firstVar * secondVar;
        }
        else if (x[1].contentEquals("/")) {
            return firstVar / secondVar;
        }
        else if (x[1].contentEquals("%")) {
            return firstVar % secondVar;
        }
        else {
            return null;
        }

    }


    /**
     * main loop
     * 
     * @param args
     *            main arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
