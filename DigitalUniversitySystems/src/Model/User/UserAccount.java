/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.User;

import Model.Profile;


/**
 *
 * @author gagan
 */
public class UserAccount {
    
    String username;
    String password;
    Profile role;

    public UserAccount(String username, String password, Profile role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    
    public boolean isMatch(String id){
        if(String.valueOf(this.role.getPerson().getUNID()).equals(id)) return true;
        else return false;
    }
    public boolean IsValidUser(String un, String pw){
        
        if(username.equalsIgnoreCase(un) && password.equals(pw)) return true;
        else return false;
        
        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return role;
    }

    public void setProfile(Profile role) {
        this.role = role;
    }
    
    @Override
    public String toString(){
        return this.getProfile().getPerson().getName();
    }
    
}
