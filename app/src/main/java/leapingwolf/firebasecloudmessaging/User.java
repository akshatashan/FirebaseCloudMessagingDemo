package leapingwolf.firebasecloudmessaging;

/**
 * Created by akshata on 30/11/2016.
 */

public class User {
    private Integer id;
    private String name;
    private String occupation;

    public User(Integer id, String name, String occupation){
        this.name = name;
        this.occupation =occupation;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }
}
