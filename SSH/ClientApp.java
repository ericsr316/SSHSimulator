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

            System.out.print("\033\143");
            System.out.flush();

            client.connectToServer();

            client.x = false;
            users = client.users;
            groups = client.groups;
            folders = client.folder;

            execution(client);

        }
    }

    public static void execution(Client c) {
        String command = "";

        System.out.print(c.getClient().getInetAddress().getHostName() + "@" + c.getUser().getUserName() + "~#");
        command = sc.nextLine();

        switch (command) {
            case "ifconfig": {
                ifConfig(c);
                c.x = true;
                break;
            }
            case "exit": {
                exitServer(c);
                c.x = true;
                break;
            }
            case "echo": {
                echo();
                c.x = true;
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

            case "mkgroup": {
                mkgroup(c);
                c.x = true;
                break;
            }

            case "adduxg": {
                addUserxGroup(c);
                c.x = true;
                break;
            }

            case "ls": {
                ls();
                c.x = true;
                break;
            }

            case "ls -a": {
                lall();
                c.x = true;
                break;
            }

            case "help": {
                help();
                c.x = true;
                break;
            }

            default: {
                System.out.println("Command not found");
                c.x = true;
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
        for (int i = 0; i < folders.size(); i++) {
            System.out.print("/" + folders.get(i).getName() + " ");
        }
        System.out.print("\n");
    }

    public static void lall() {
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
        int gid = -1;
        System.out.print("Type the folder's name: ");
        fname = sc.next();
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).getMembers().size(); j++) {
                if (c.getUserName().equals(groups.get(i).getMembers().get(j).getUserName())) {
                    gid = i;
                    break;
                }
            }
        }

        folders.add(new Folder(fname, permissions, c.getUser(), groups.get(gid)));

    }

    public static void mkgroup(Client c) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals("root")) {
                String name = "";
                System.out.println("Type a name for the new group");
                name = sc.next();
                groups.add(new Group(name, new ArrayList<User>()));
                System.out.println(name + " was created sucefully");
                break;
            }

        }

    }

    public static void addUserxGroup(Client c) {
        String user = "";
        String group = "";
        System.out.println("Type a username name");
        user = sc.next();
        System.out.println("Type a group name");
        group = sc.next();

        for (int i = 0; i < groups.size(); i++) {
            if (group.equals(groups.get(i).getName())) {
                for (int j = 0; j < users.size(); j++) {
                    if (user.equals(users.get(j).getUserName())) {

                        groups.get(i).getMembers().add(users.get(j));
                        System.out.println(groups.get(i).toString());

                        break;
                    }

                }
            }
        }
    }

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
        System.out.println("*******All commands aviable*****");
        System.out.println("*       echo - print a message *");
        System.out.println("*ifconfig - show network ip    *");
        System.out.println("*adduser - add a new user (only for root)     *");
        System.out.println("*mkgroup - add a new group(for root)     *");
        System.out.println("*adduxg - add a user to a group (only for root)*");
        System.out.println("*allusers - show all the users aviable (only for root)*");
        System.out.println("*ls - list all the directorys*");
        System.out.println("*ls -a list all the directory's info*");
        System.out.println("*help - i'm noob so teach me how use this powerfull system");
        System.out.println("*****exit - left the system******");
    }

}
