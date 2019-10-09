import java.util.ArrayList;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author amacleod
 * @version 100819
 */
public class Mu {

    private ArrayList<Variable> variableList = new ArrayList<Variable>();

    /**
     * Create a new Mu object.
     */
    public Mu() {
        variableList = new ArrayList<Variable>();
        for (int i = 0; i < 11; i++) {
            variableList.add(null);
        }
    }


    /**
     * capacity of memory
     * 
     * @return capacity of memory
     */
    public int capacity() {
        return variableList.size() - 1;
    }


    /**
     * getter; it returns the variable object stored at the given address; or
     * null if no variable is at that location or if the address is not a valid
     * address. A valid address ranges from [0..9]
     * 
     * @param adr
     *            address in variable
     * @return null if no variable at location; variable is adress is at
     *         location
     */
    public Variable fetch(Integer adr) {
        if (adr.compareTo(0) < 0) {
            return null;
        }
        if (!(variableList.isEmpty())) {
            return variableList.get(adr);
        }
        else {
            return null;
        }

    }


    /**
     * Place a description of your method here.
     * 
     * @param v
     *            variable of Variable
     * @param adr
     *            index of list
     * @return false if value cannot be stored; true if it can
     */
    public Boolean store(Variable v, Integer adr) {
        String id = v.getIdentifier();
        if (id.length() > 3) {
            return false;
        }
        System.out.println(variableList.get(adr));
        if ((variableList.isEmpty())) {
            return false;
        }
        else {
            variableList.set(adr, v);
            return true;
        }

    }


    /**
     * getter
     * 
     * @return number of used spaces in memory
     */
    public int used() {
        int used = 0;
        for (int i = 0; i < variableList.size(); i++) {
            if (!(variableList.get(i) == (null))) {
                used = used + 1;
            }
        }
        return used;
    }


    /**
     * main loop
     * 
     * @param args
     *            main args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
