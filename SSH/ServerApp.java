import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ericm
 */
public class ServerApp implements Serializable {

    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<User> users = new ArrayList<User>();

    public static ArrayList<Group> groups = new ArrayList<Group>();
    public static ArrayList<Folder> folders = new ArrayList<Folder>();
    private static Server server;

    public static void main(String args[]) {
        fillUsers();
        fillGroups();
        fillFolders();

        server = new Server(users, groups, folders);
    }

    public static void fillUsers() {
        users.add(new User("root", String.valueOf("12345".hashCode())));
        users.get(users.size() - 1).setRol("root");
        users.add(new User("emmc", String.valueOf("dino117".hashCode())));
        users.get(users.size() - 1).setRol("user");
    }

    public static void fillGroups() {
        ArrayList<User> usuariosSystems = new ArrayList<User>();
        usuariosSystems.add(users.get(0));
        usuariosSystems.add(users.get(1));
        groups.add(new Group("System", usuariosSystems));

    }

    public static void fillFolders() {
        char[] permissions = { 'r', 'w', 'x' };

        folders.add(new Folder("lib", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("opt", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("usr", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("home", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("mnt", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("var", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("bin", permissions, users.get(0), groups.get(0)));
        folders.add(new Folder("boot", permissions, users.get(0), groups.get(0)));

    }

}
