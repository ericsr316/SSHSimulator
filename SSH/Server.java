/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericm
 */
public class Server implements Serializable {

    public static final int port = 22;
    private ServerSocket server;
    private Client client;
    private ObjectInputStream obin;
    private ObjectOutputStream obout;
    private DataInputStream in;
    private DataOutputStream out;
    private InetAddress host;
    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Group> groups = new ArrayList<Group>();
    public ArrayList<Folder> folders = new ArrayList<Folder>();
    private User user;

    public Server(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Folder> folders) {
        this.users = users;
        this.groups = groups;
        this.folders = folders;
        client = new Client(false);
        try {
            host = InetAddress.getLocalHost();
            this.server = new ServerSocket(22, 0, host);
            System.out.println("Server has started in " + server.getInetAddress().getHostAddress() + " in port: "
                    + server.getLocalPort());
            System.out.println("Running server in " + System.getProperty("os.name"));
            System.out.println("Java version: " + System.getProperty("java.version"));
            for (;;) {
                this.client.setClient(this.server.accept());
                if (!this.client.getClient().isClosed()) {
                    sendInfo(this.client.getClient());
                    reciveUser(this.client.getClient());
                }

                System.out.println("Client: " + this.getUser().getUserName() + " on "
                        + this.client.getClient().getInetAddress().getHostAddress() + "@"
                        + this.client.getClient().getInetAddress().getHostName());

                if (!this.client.getClient().isClosed()) {
                    this.recieveInfo(this.client.getClient());
                }

            }

        } catch (IOException e) {
            System.out.println("...");

        }
    }

    public void serverStatus() {

        System.out.println("Server Status:\n" + this.server.toString() + "\nStatus: on\n");

    }

    public SocketAddress getIp() {
        return this.server.getLocalSocketAddress();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getObin() {
        return obin;
    }

    public void setObin(ObjectInputStream obin) {
        this.obin = obin;
    }

    public ObjectOutputStream getObout() {
        return obout;
    }

    public void setObout(ObjectOutputStream obout) {
        this.obout = obout;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public ArrayList<Group> getMembers() {
        return this.groups;
    }

    public void setMembers(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public int getPosAccount(String username, String pass) {
        int pos = -1;
        for (int i = 0; i < this.getUsers().size(); i++) {
            if (this.getUsers().get(i).getUserName().equals(username)
                    && this.getUsers().get(i).getPass().equals(pass)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public void sendInfo(Socket c) {
        try {
            this.setObout(new ObjectOutputStream(c.getOutputStream()));
            this.getObout().writeObject(this.users);
            this.getObout().flush();

            this.setObout(new ObjectOutputStream(c.getOutputStream()));
            this.getObout().writeObject(this.groups);

            this.getObout().flush();

            this.setObout(new ObjectOutputStream(c.getOutputStream()));
            this.getObout().writeObject(this.folders);
            this.getObout().flush();
        } catch (IOException e) {
            System.out.println("Client isn't connected now");
        }
    }

    public void recieveInfo(Socket c) {
        try {
            if (!c.isClosed()) {
                this.setObin(new ObjectInputStream(c.getInputStream()));
                this.users = (ArrayList<User>) this.getObin().readObject();
                this.setObin(new ObjectInputStream(c.getInputStream()));
                this.groups = (ArrayList<Group>) this.getObin().readObject();
                this.setObin(new ObjectInputStream(c.getInputStream()));
                this.folders = (ArrayList<Folder>) this.getObin().readObject();

                for (int i = 0; i < users.size(); i++) {
                    System.out.println(users.get(i).toString());
                }

                for (int i = 0; i < groups.size(); i++) {
                    System.out.println(groups.get(i).toString());
                }

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
        } catch (IOException e) {
            System.out.println("Client isn't connected now");
        }

        catch (ClassNotFoundException a) {
            System.out.println("Client isn't connected now");
        }
    }

    public void reciveUser(Socket c) {
        try {
            this.setObin(new ObjectInputStream(c.getInputStream()));
            this.setUser((User) this.getObin().readObject());
        } catch (IOException ex) {
            System.out.println("...");
        } catch (ClassNotFoundException ex) {
            System.out.println("...");
        }
    }
}
