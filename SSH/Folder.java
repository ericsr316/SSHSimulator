import java.io.Serializable;
import java.util.Arrays;

public class Folder implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private char[] permissions;
    private User own;
    private Group group;

    public Folder(String name, char[] permission, User own, Group group) {
        this.name = name;
        this.permissions = permissions;
        this.permissions = permission;
        this.own = own;
        this.group = group;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPermissions() {
        return permissions;
    }

    public void setPermissions(char[] permissions) {
        this.permissions = permissions;
    }

    public User getOwn() {
        return own;
    }

    public void setOwn(User own) {
        this.own = own;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Folder [name=" + name + ", permissions=" + Arrays.toString(permissions) + ", own=" + own.toString()
                + ", group="
                + group.toString() + "]";
    }

}
