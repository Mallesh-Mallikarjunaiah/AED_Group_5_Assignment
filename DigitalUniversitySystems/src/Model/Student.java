/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author gagan
 */
public class Student extends Person {
    private double creditsCompleted;
    private double overallGPA;
    // Note: Use FinancialRecord to track actual tuition balance
 
    public Student(String name, String email, String ID, double creditsCompleted) {
        super(name, email, ID);
        this.creditsCompleted = creditsCompleted;
        this.overallGPA = 0.0;
    }
 
    // Getters and Setters
    public double getCreditsCompleted() {
        return creditsCompleted;
    }
 
    public void setCreditsCompleted(double creditsCompleted) {
        this.creditsCompleted = creditsCompleted;
    }
 
    public double getOverallGPA() {
        return overallGPA;
    }
    // Override toString for display in the StudentRegistrationJPanel
    @Override
    public String toString() {
        return getName() + " (" + getUNID() + ")";
    }
}
    
