/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author jayan
 */
public class Registrar extends Person {
    private String officeHours;

    public Registrar(String name, String email, String ID, String officeHours) {
        super(name, email, ID);
        this.officeHours = officeHours;
    }

    // Getters and Setters (Needed for Profile Management)
    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }
}
