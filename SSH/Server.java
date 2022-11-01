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
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Group> groups = new ArrayList<Group>();
    private ArrayList<Folder> folders = new ArrayList<Folder>();
    private User user;

    public Server(ArrayList<User> users) {
        this.users = users;
        client = new Client();
        this.users = users;
        try {
            host = InetAddress.getLocalHost();
            this.server = new ServerSocket(22, 0, host);
            System.out.println("Server has started in " + server.getInetAddress().getHostAddress() + " in port: "
                    + server.getLocalPort());
            for (;;) {

                this.client.setClient(this.server.accept());
                System.out.println("Client on " + this.client.getClient().getInetAddress().getHostAddress() + "@"
                        + this.client.getClient().getInetAddress().getHostName());
                this.setIn(new DataInputStream(new BufferedInputStream(this.client.getClient().getInputStream())));
                this.setOut(new DataOutputStream(this.client.getClient().getOutputStream()));

                this.login(this.getIn());
                if (this.getUser() != null) {
                    this.sendUserClient(this.getOut());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();

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

    public void login(DataInputStream in) {
        try {
            this.setIn(in);
            String user, pass;
            int pos = 0;
            user = this.getIn().readUTF();
            pass = this.getIn().readUTF();
            pos = getPosAccount(user, pass);
            if (pos != -1) {
                System.out.println(this.getUsers().get(pos).getUserName() + " in "
                        + this.client.getClient().getInetAddress().getHostAddress());
                this.setUser(this.getUsers().get(pos));
            }

        } catch (IOException ex) {
            System.out.println(ex.toString());

        }
    }

    public void sendUserClient(DataOutputStream out) {
        if (user != null) {
            try {
                this.setOut(out);
                this.getOut().writeUTF(this.getUser().getUserName());
                this.getOut().flush();
                this.getOut().writeUTF(this.getUser().getPass());
                this.getOut().flush();
                this.getOut().writeUTF(this.getUser().getRol());
                this.getOut().flush();
                this.getOut().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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

}
