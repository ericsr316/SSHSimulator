import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<User> users;

    public Group(String name, ArrayList<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getMembers() {
        return users;
    }

    public void setMembers(ArrayList<User> users) {
        this.users = users;
    }

    @Override

    public String toString() {
        return "Group: [" + this.getName() + "," + this.getMembers().toString() + "]";
    }
}
