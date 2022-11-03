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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericm
 */
public class Client implements Serializable {

    private Socket client;
    private String ip;
    private int port;
    private ObjectInputStream obin;
    private ObjectOutputStream obout;
    private DataInputStream in;
    private DataOutputStream out;
    private String userName;
    private String pass;
    private User user;
    public Scanner sc = new Scanner(System.in);
    public ArrayList<User> users;
    public ArrayList<Group> groups;
    public ArrayList<Folder> folder;
    public boolean x = false;

    public Client(boolean x) {
        this.x = x;
        this.user = null;

    }

    public void connectToServer() {

        try {

            client = new Socket(ip, port);
            getInfo();
            typeCredentials();

        } catch (IOException ex) {
            System.out.println("...");
        }

    }

    public void disconnectFromServer() {
        try {
            if (this.getClient().isConnected()) {
                this.getClient().close();
            }
        } catch (IOException e) {
            System.out.println("...");
        }

    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void typeCredentials() {

        for (int i = 0; i < users.size(); i++) {
            if (this.getUserName().equals(users.get(i).getUserName()) &&
                    this.getPass().equals(users.get(i).getPass())) {
                System.out.println("User found!");
                User z = users.get(i);
                this.setUser(z);
                sendUser();
                break;
            }
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void getInfo() {
        try {
            this.setObin(new ObjectInputStream(this.getClient().getInputStream()));
            this.users = (ArrayList<User>) this.getObin().readObject();
            this.setObin(new ObjectInputStream(this.getClient().getInputStream()));
            this.groups = (ArrayList<Group>) this.getObin().readObject();
            this.setObin(new ObjectInputStream(this.getClient().getInputStream()));
            this.folder = (ArrayList<Folder>) this.getObin().readObject();
        } catch (IOException e) {
            System.out.println("...");
        }

        catch (ClassNotFoundException f) {
            System.out.println("...");
        }
    }

    public void sendInfo() {
        try {

            System.out.println("sending...");

            this.setObout(new ObjectOutputStream(this.client.getOutputStream()));
            this.getObout().writeObject(this.users);
            this.getObout().flush();

            this.setObout(new ObjectOutputStream(this.client.getOutputStream()));
            this.getObout().writeObject(this.groups);
            this.getObout().flush();

            this.setObout(new ObjectOutputStream(this.client.getOutputStream()));
            this.getObout().writeObject(this.folder);
            this.getObout().flush();
        } catch (IOException ex) {
            System.out.println("...");
        }
    }

    public void sendUser() {
        try {
            this.setObout(new ObjectOutputStream(this.client.getOutputStream()));
            this.getObout().writeObject(this.getUser());
            this.getObout().flush();
        } catch (IOException ex) {
            System.out.println("...");
        }
    }

}
