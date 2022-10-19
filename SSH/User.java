/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;
/**
 *
 * @author ericm
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String pass;
    private String rol;
    
    public User(){
        
    }
    public User(String userName,String pass){
    this.userName = userName;
    this.pass = pass;
    
    

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + this.getUserName() + ", pass=" + this.getPass() + ", rol=" + this.getRol() + '}';
    }
    


}
