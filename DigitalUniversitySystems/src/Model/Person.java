/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author MALLESH
 */
public class Person {
    
    String name;
    static int count=0;
    int UNID;
    String email;
    String contactNumber;

    public Person(String name,String email, String contactNumber) {
        UNID=++count;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        
        
        
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUNID() {
        return UNID;
    }
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    
    
  
}
