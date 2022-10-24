import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author ericm
 */
public class ClientApp {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Folder> folders = new ArrayList<Folder>();
    public static ArrayList<Group> groups = new ArrayList<Group>();
    private static Client client;

    public static void main(String args[]) throws ClassNotFoundException, IOException {
        client = new Client();
        System.out.print("Type IP ADDRESS: ");
        client.setIp(sc.next());
        client.setPort(22);
        System.out.print("Type your credentials\nType your username: ");
        client.setUserName(sc.next());
        System.out.print("Type your password: ");
        client.setPass(sc.next());
        client.connectToServer();
        fillGroups();
        fillFolders();
        for (;;) {
            execution(client);
        }
    }

    public static void fillGroups() {
        ArrayList<User> usuariosSystems = new ArrayList<User>();
        usuariosSystems.add(client.getUser());
        groups.add(new Group("System", usuariosSystems));
        usuariosSystems.clear();

    }

    public static void fillFolders() {
        char[] permissions = { 'r', 'w', 'x' };
        folders.add(new Folder("lib", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("opt", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("usr", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("home", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("mnt", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("var", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("bin", permissions, client.getUser(), groups.get(0)));
        folders.add(new Folder("boot", permissions, client.getUser(), groups.get(0)));
    }

    public static void execution(Client c) {
        String command = "";

        System.out.print(c.getClient().getInetAddress() + "@" + c.getUserName() + "~#");
        command = sc.next();
        switch (command) {
            case "ifconfig": {
                ifConfig(c);
                break;
            }
            case "exit": {
                exitServer(c);
                break;
            }
            case "echo": {
                echo();
                break;
            }
            case "ls": {
                ls();
                break;
            }
            case "adduser": {
                addUser();
                break;
            }
        }
    }

    public static void ifConfig(Client c) {
        System.out.println("NETWORK INFO");
        System.out.println("IP ADRESS:\n" + c.getClient().getInetAddress().getHostAddress() + "\n");
        System.out.println("HOSTNAME:\n" + c.getClient().getInetAddress().getHostName() + "\n");
    }

    public static void exitServer(Client c) {
        System.out.println("Exiting...");
        try {
            c.getClient().close();
        } catch (IOException e) {
            System.out.println("Something is wrong");
            e.printStackTrace();
        }
        System.exit(1);
    }

    public static void echo() {
        System.out.println(sc.next());
    }

    public static void ls() {
        System.out.println(folders.toString());
    }

    public static void addUser() {
        String username, passwd;
        System.out.println("Type your username");
        username = sc.next();
        System.out.println("Type your password");
        passwd = sc.next();
        User x = new User(username, passwd);
        x.setRol("user");
        users.add(x);
    }

}
