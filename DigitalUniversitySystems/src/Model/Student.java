/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author gagan
 */
public class Student extends Profile {
    // Attributes specific to the Student role
    private double creditsCompleted;
    private double overallGPA;
    private double termGPA;
    private String academicStanding;
    private double tuitionBalance;
    private ArrayList<Course> enrolledCourses;
    private Department department;
 
    // Constructor to initialize a Student object
    public Student(Person p, Department department) {
        super(p);
        this.creditsCompleted = 0;
        this.overallGPA = 0.0;
        this.termGPA = 0.0;
        this.academicStanding = "Good Standing";
        this.tuitionBalance = 0.0;
        this.enrolledCourses = new ArrayList<>();
        this.department = department;
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
        return this.getPerson().getName() + " (" + this.getPerson().getUNID() + ")";
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Department getDepartment() {
        return department;
    }
    
    
}
