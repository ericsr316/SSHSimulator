import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import javax.swing.SpinnerDateModel;

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
        client.setPass(String.valueOf(sc.next().hashCode()));
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
        User u;
        if (client.getUser().getUserName().equals("root")) {
            u = client.getUser();
        } else {
            u = new User("root", "12345");
            u.setRol("root");
        }

        folders.add(new Folder("lib", permissions, u, groups.get(0)));
        folders.add(new Folder("opt", permissions, u, groups.get(0)));
        folders.add(new Folder("usr", permissions, u, groups.get(0)));
        folders.add(new Folder("home", permissions, u, groups.get(0)));
        folders.add(new Folder("mnt", permissions, u, groups.get(0)));
        folders.add(new Folder("var", permissions, u, groups.get(0)));
        folders.add(new Folder("bin", permissions, u, groups.get(0)));
        folders.add(new Folder("boot", permissions, u, groups.get(0)));
    }

    public static void execution(Client c) {
        String command = "";

        System.out.print(c.getClient().getInetAddress() + "@" + c.getUser().getUserName() + "~#");
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

            case "lall": {
                lall();
                break;
            }

            case "adduser": {
                addUser();
                break;
            }

            case "su": {
                su();
                break;
            }

            case "mkdir": {
                mkdir(c);
                break;
            }

            case "allusers": {
                allUsers(c);
                break;
            }

            case "help": {
                help();
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
            c.disconnectFromServer();
        } catch (IOException e) {
            System.out.println("Something is wrong");
            e.printStackTrace();
        }
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
        int gid = 0;
        System.out.print("Type the folder's name: ");
        fname = sc.next();
        folders.add(new Folder(fname, permissions, c.getUser(), groups.get(0)));

    }

    public static void su() {
        String usu;
        String pass;
        System.out.print("su: ");
        usu = sc.next();
        for (int i = 0; i < users.size(); i++) {
            if (usu.equals(users.get(i).getUserName())) {
                System.out.print("Type your password: ");
                pass = String.valueOf(sc.next().hashCode());
                if (pass.equals(users.get(i).getPass())) {
                    System.out.println("Goodbye " + client.getUserName() + " see u later");
                    client.setUser(users.get(i));
                    break;
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
        System.out.println("All commands aviable");
        System.out.println("ls - list all folders");
        System.out.println("lall - list all folders with its information");
        System.out.println("echo - print a message");
        System.out.println("ifconfig - show network ip");
        System.out.println("adduser - add a new user");
        System.out.println("exit - left the system");
    }

}
