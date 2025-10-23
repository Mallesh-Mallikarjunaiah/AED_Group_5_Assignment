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
    
    // Attributes specific to the Student role
    private double creditsCompleted;
    private double overallGPA;
    private double termGPA;
    private String academicStanding;
    private double tuitionBalance;

    // Constructor to initialize a Student object
    public Student(String name, String email, String ID, double creditsCompleted) {
        super(name, email, ID);
        this.creditsCompleted = creditsCompleted;
        this.overallGPA = 0.0;
        this.termGPA = 0.0;
        this.academicStanding = "Good Standing";
        this.tuitionBalance = 0.0;
    }

    // --- Getters ---
    
    // The previous getUNID() is now inherited from Person as getID()
    
    public double getCreditsCompleted() {
        return creditsCompleted;
    }

    public double getOverallGPA() {
        return overallGPA;
    }

    public double getTermGPA() {
        return termGPA;
    }
    
    public String getAcademicStanding() {
        return academicStanding;
    }

    public double getTuitionBalance() {
        return tuitionBalance;
    }

    // --- Setters ---
    // Used by EnrollmentService/GPACalculator
    public void setCreditsCompleted(double creditsCompleted) {
        this.creditsCompleted = creditsCompleted;
    }

    public void setOverallGPA(double overallGPA) {
        this.overallGPA = overallGPA;
    }
    
    public void setTermGPA(double termGPA) {
        this.termGPA = termGPA;
    }

    // Used by GPACalculator logic (e.g., Academic Warning, Probation)
    public void setAcademicStanding(String academicStanding) {
        this.academicStanding = academicStanding;
    }

    // Used by FinancialService when tuition is billed or paid
    public void setTuitionBalance(double tuitionBalance) {
        this.tuitionBalance = tuitionBalance;
    }
    
    // Override toString for easy display in UI components (e.g., JComboBoxes)
    @Override
    public String toString() {
        return getName() + " (" + getUNID() + ")";
    }
}
