
/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 * 
 * @author amacleod
 * @version 100719
 */
public class Variable {

    private String  variableId;
    private Integer variableData;

    /**
     * Create a new Variable object with params.
     * 
     * @param id
     *            variable id
     * @param data
     *            data in variable
     */
    public Variable(String id, Integer data) {
        variableId = id;
        variableData = data;
    }


    /**
     * gets the id of variable
     * 
     * @return id of variable
     */
    public String getIdentifier() {
        return variableId;
    }


    /**
     * sets id to variable_id
     * 
     * @param id
     *            id of variable
     */
    public void setIdentifier(String id) {
        variableId = id;
    }


    /**
     * gets data from variable_data
     * 
     * @return variable_data
     */
    public Integer getData() {
        return variableData;
    }


    /**
     * sets the data to variable_data
     * 
     * @param data
     *            variable data
     */
    public void setData(Integer data) {
        variableData = data;
    }


    /**
     * prints variable id and variable data in a string {@inheritDoc}
     */
    public String toString() {
        return variableId + String.valueOf(variableData);
    }


    /**
     * main method
     * 
     * @param args
     *            main arguments
     */
    public static void main(String[] args) {
        //empty for tests
    }

}
