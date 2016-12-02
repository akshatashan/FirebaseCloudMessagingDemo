package leapingwolf.firebasecloudmessaging;

/**
 * Created by akshata on 30/11/2016.
 */

public class User {
    private String name;
    private String occupation;

    public User(String name, String occupation){
        this.name = name;
        this.occupation =occupation;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }
}
