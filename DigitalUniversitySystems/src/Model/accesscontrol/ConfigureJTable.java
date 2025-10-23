/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.accesscontrol;

/**
 *
 * @author talha
 */
public class ConfigureJTable {
public static void initialize() {
        // Sample persons and roles for table initialization
        Person admin = new Admin("John Smith");
        Person facultyMember = new Faculty("Gina Montana");
        Person student = new Student("Adam Rollen");
        Person registrar = new Registrar("Jim Dellon");
        
        // Further data setup for JTable initialization can be added here
        
        // Example: configure table columns, cell renderers, data models, etc.
        
    }
    
    // Optional: Additional helpers or configuration for UI tables can be added here
    
}
}
