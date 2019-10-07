
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author 
 *  @version 
 */
public class Variable {
    private String variable_id;
    private Integer variable_data;
    public Variable() {
        String variable_id = "No id";
        Integer variable_data = -1;
        
    }
    public Variable(String id, Integer data) {
        variable_id = id;
        variable_data = data;
    }
    public String getIdentifier() {
        return variable_id;
    }
    public void setIdentifier(String id) {
        variable_id = id;
    }
    public Integer getData() {
        return variable_data;
    }
    public void setData(Integer data) {
        variable_data = data;
    }
    public String toString() {
        return variable_id + String.valueOf(variable_data);
    }
    public static void main(String[] args) {

        

    }

}
