/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Scanner;

/**
 *
 * @author ericm
 */
public class ClientApp {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String args[]){
        Client client = new Client();
        client.setIp("192.168.1.68");
        client.setPort(22);
        client.setUserName("root");
        client.setPass("12345");
        client.connectToServer();
}
}
