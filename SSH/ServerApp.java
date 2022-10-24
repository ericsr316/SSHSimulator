/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ericm
 */
public class ServerApp {

    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Folder> folders = new ArrayList<Folder>();
    public static ArrayList<Group> groups = new ArrayList<Group>();
    private static Server server;

    public static void main(String args[]) throws IOException {
        fillUsers();

        server = new Server(users);
        server.setUsers(users);
        server.setMembers(groups);
        server.setFolders(folders);

    }

    public static void fillUsers() {
        users.add(new User("root", "12345"));
        users.get(users.size() - 1).setRol("root");
        users.add(new User("emmc", "12345"));
        users.get(users.size() - 1).setRol("user");
    }

}
