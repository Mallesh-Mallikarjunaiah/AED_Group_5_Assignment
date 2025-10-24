/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gagan
 */
public enum ProfileEnum {
    ADMIN("Admin"), STUDENT("Student"), FACULTY("Faculty"), REGISTRAR("Registrar");
    
    private final String profile;
    
    private ProfileEnum(String value) {
        profile = value;
    }

    @Override
    public String toString() {
        return this.profile;
    }
    
    
}
