/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gagan
 */
public abstract class Profile {
    Person person;
    private boolean active;
     
    public Profile(Person p){
        person = p;
    }
    
    
    
    public abstract String getRole();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Person getPerson(){
        return person;
    }
     

    public boolean isMatchId(String id) {
        if (String.valueOf(person.getUNID()).equals(id)) {
            return true;
        }
        return false;
    }
    
}
