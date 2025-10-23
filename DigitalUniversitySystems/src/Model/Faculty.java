/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
/**
 *
 * @author jayan
 */
public class Faculty extends Person {
    private String department;

    public Faculty(String name, String email, String ID, String department) {
        super(name, email, ID);
        this.department = department;
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Override toString for easy display in JTables or dropdowns
    @Override
    public String toString() {
        return getName() + " (" + getUNID() + ")";
    }
}
