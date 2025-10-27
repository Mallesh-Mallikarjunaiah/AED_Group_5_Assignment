/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author jayan
 */
public class Enrollment {
    private Student student;
    private CourseOffering courseOffering;
    private String grade;
    private boolean isActive; // True = enrolled, False = dropped
    private boolean isCompleted; // NEW: True = course completed with grade

    public Enrollment(Student student, CourseOffering courseOffering) {
        this.student = student;
        this.courseOffering = courseOffering;
        this.isActive = true; // Default enrollment status
        this.grade = "N/A";
        this.isCompleted = false; // Initialize the new field
    }

    // --- Getters ---
    
    public Student getStudent() { return student; }
    public CourseOffering getCourseOffering() { return courseOffering; }
    public boolean isActive() { return isActive; }
    public String getGrade() { return grade; }
    
    // Getter for the dedicated completion status field
    public boolean isCompleted() { return isCompleted; }
    
    // --- Setters / Actions ---

    // Setter used when Registrar drops a student
    public void setActive(boolean active) { this.isActive = active; }
    
    /**
     * Setter for grade (used by faculty). Automatically marks the course as completed
     * if a valid grade is entered (i.e., not N/A).
     */
    public void setGrade(String grade) { 
        this.grade = grade;
        // Logic to mark as completed upon receiving a final grade
        if (grade != null && !grade.equalsIgnoreCase("N/A")) {
            this.isCompleted = true;
            this.isActive = false; // Typically, a completed course is no longer actively 'enrolled'
        }
    }
    
    // Setter for completion status (retained for completeness)
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
    
    // Helper getter for JTable display
    public String getEnrollmentStatus() { return isActive ? "Enrolled" : "Dropped"; }
    
    // Removed the conflicting markCompleted() method from the original code
}