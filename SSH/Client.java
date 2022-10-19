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
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ericm
 */
public class Client{

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

public Client(){
   this.user = null;

}


    public void connectToServer(){
       
       try {
        client = new Socket(ip,port);
        typeCredentials();
        this.user = this.reciveUser();
        if(user!=null){  
        System.out.println("Wellcome "+this.user.toString());
     
        }
     
        
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
        
      
    }
    
    public void disconnectFromServer(){
    try {
        if(this.getClient().isConnected()){
        this.getClient().close();
        }
        else{
            System.out.println("The is not a conection");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
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
    
    

     public void typeCredentials(){
 
    try {
        this.setOut(new DataOutputStream(client.getOutputStream()));
        this.getOut().writeUTF(this.getUserName());
        this.getOut().flush();
        this.getOut().writeUTF(this.getPass());
        this.getOut().flush();
  
    } catch (IOException ex) {
        ex.printStackTrace();
    }
     }
     
     public User reciveUser() throws IOException{
     User x = new User();
         try {   
        this.setIn(new DataInputStream(new BufferedInputStream(this.client.getInputStream())));
        x.setUserName(this.getIn().readUTF());
        x.setPass(this.getIn().readUTF());
        x.setRol(this.getIn().readUTF());
        this.getIn().close();
    } catch (IOException ex) {
             ex.printStackTrace();
   } 

         return x;
     }
    
}
