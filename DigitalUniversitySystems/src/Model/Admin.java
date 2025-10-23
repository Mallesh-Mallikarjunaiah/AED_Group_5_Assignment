/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import Model.Person;

/**
 *
 * @author MALLESH
 */
public class Admin extends Profile {
    
    public Admin(Person p) {
        super(p); 
    }
    
    
    @Override
    public String getRole(){
        return  "Admin";
    }
   
}
