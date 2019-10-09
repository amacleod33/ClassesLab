
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author amacleod
 * @version 100719
 */
public class Variable {

    private String  variable_id;
    private Integer variable_data;

    /**
     * Create a new Variable object.
     */
    public Variable() {

        String variable_id = "No id";
        Integer variable_data = -1;

    }


    /**
     * Create a new Variable object with params.
     * 
     * @param id
     *            variable id
     * @param data
     *            data in variable
     */
    public Variable(String id, Integer data) {
        variable_id = id;
        variable_data = data;
    }


    /**
     * gets the id of variable
     * 
     * @return id of variable
     */
    public String getIdentifier() {
        return variable_id;
    }


    /**
     * sets id to variable_id
     * 
     * @param id
     *            id of variable
     */
    public void setIdentifier(String id) {
        variable_id = id;
    }


    /**
     * gets data from variable_data
     * 
     * @return variable_data
     */
    public Integer getData() {
        return variable_data;
    }


    /**
     * sets the data to variable_data
     * 
     * @param data
     *            variable data
     */
    public void setData(Integer data) {
        variable_data = data;
    }


    /**
     * prints variable id and variable data in a string {@inheritDoc}
     */
    public String toString() {
        return variable_id + String.valueOf(variable_data);
    }


    /**
     * main method
     * 
     * @param args
     *            main arguments
     */
    public static void main(String[] args) {

    }

}
