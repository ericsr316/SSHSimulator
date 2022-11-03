import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author ericm
 */
public class ClientApp implements Serializable {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Folder> folders = new ArrayList<Folder>();
    public static ArrayList<Group> groups = new ArrayList<Group>();
    private static Client client;
    public static boolean x = false;

    public static void main(String args[]) {

        client = new Client(x);
        client.setIp(args[0]);
        client.setPort(22);
        client.setUserName(args[1]);
        client.setPass(String.valueOf(args[2].hashCode()));

        for (;;) {
            client.connectToServer();
            System.out.println("*******All commands aviable*****");
            System.out.println("*       echo - print a message *");
            System.out.println("*ifconfig - show network ip    *");
            System.out.println("*adduser - add a new user      *");
            System.out.println("*allusers - show all the users aviable (only for root)*");
            System.out.println("*****exit - left the system******");
            client.x = false;
            users = client.users;
            groups = client.groups;
            folders = client.folder;

            for (int i = 0; i < folders.size(); i++) {
                char permissions[] = folders.get(i).getPermissions();
                System.out.print("Name: " + folders.get(i).getName() + "; permissions: ");
                for (int j = 0; j < permissions.length; j++) {
                    System.out.print(permissions[j] + " ");
                }
                System.out.print(";Group: " + folders.get(i).getGroup().getName() + "; Owner: "
                        + folders.get(i).getOwn().getUserName());
                System.out.print("\n");
            }

            execution(client);

        }
    }

    public static void execution(Client c) {
        String command = "";

        System.out.print(c.getClient().getInetAddress().getHostName() + "@" + c.getUser().getUserName() + "~#");
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

            case "adduser": {
                addUser();
                c.x = true;
                break;
            }

            case "mkdir": {
                mkdir(c);
                c.x = true;
                break;
            }

            case "allusers": {
                allUsers(c);
                c.x = true;
                break;
            }

            case "help": {
                help();
                break;
            }

            default: {
                System.out.println("Command not found");
            }
        }
        if (c.x) {
            c.sendInfo();
        }
    }

    public static void ifConfig(Client c) {
        System.out.println("NETWORK INFO");
        System.out.println("IP ADRESS:\n" + c.getClient().getInetAddress().getHostAddress() + "\n");
        System.out.println("HOSTNAME:\n" + c.getClient().getInetAddress().getHostName() + "\n");
    }

    public static void exitServer(Client c) {
        System.out.println("Exiting...");

        c.disconnectFromServer();

        System.exit(1);
    }

    public static void echo() {
        System.out.println(sc.nextLine());
    }

    public static void ls() {
        System.out.println(folders.size());
        for (int i = 0; i < folders.size(); i++) {
            System.out.println("/" + folders.get(i).getName() + " ");
        }
        System.out.print("\n");
    }

    public static void addUser() {
        String username, passwd;
        System.out.println("Type your username");
        username = sc.next();
        System.out.println("Type your password");
        passwd = String.valueOf(sc.next().hashCode());
        User x = new User(username, passwd);
        x.setRol("user");
        users.add(x);

    }

    public static void mkdir(Client c) {
        char[] permissions = { 'r', 'w', 'x' };
        String fname;
        int gid = 0;
        System.out.print("Type the folder's name: ");
        fname = sc.next();
        folders.add(new Folder(fname, permissions, c.getUser(), groups.get(0)));

    }

    /*
     * public static void su() {
     * String usu;
     * String pass;
     * System.out.print("su: ");
     * usu = sc.next();
     * for (int i = 0; i < users.size(); i++) {
     * if (usu.equals(users.get(i).getUserName())) {
     * System.out.print("Type your password: ");
     * pass = String.valueOf(sc.next().hashCode());
     * if (pass.equals(users.get(i).getPass())) {
     * System.out.println("Goodbye " + client.getUserName() + " see u later");
     * client.setUser(users.get(i));
     * client.sendUser();
     * break;
     * }
     * }
     * }
     * }
     */
    public static void allUsers(Client c) {
        if (c.getUser().getRol().equals("root")) {
            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i).toString());
            }

        }

        else {
            System.out.println("This commmand just it's allowed to admin users.");
        }
    }

    public static void help() {

    }

}
