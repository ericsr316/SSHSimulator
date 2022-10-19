/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ericm
 */
public class ServerApp {
    
       public static Scanner sc = new Scanner(System.in);
       public static ArrayList<User> users = new ArrayList<User>();
              
        public static void main (String args[]){
            fillUsers();
            
            Server server = new Server(users);
            server.setUsers(users);
        }
        
        
        public static void fillUsers(){
        users.add(new User("root","12345"));
        users.get(users.size()-1).setRol("root");
                users.add(new User("emmc","12345"));
        users.get(users.size()-1).setRol("user");
        }
        }
        

        
