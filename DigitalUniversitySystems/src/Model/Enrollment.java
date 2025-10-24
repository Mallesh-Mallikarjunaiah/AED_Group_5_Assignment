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

    public Enrollment(Student student, CourseOffering courseOffering) {
        this.student = student;
        this.courseOffering = courseOffering;
        this.isActive = true; // Default enrollment status
        this.grade = "N/A";
    }

    // Getters and Setters (needed for the Registrar's 'Drop' action)
    public Student getStudent() { return student; }
    public CourseOffering getCourseOffering() { return courseOffering; }
    public boolean isActive() { return isActive; }
    
    // Setter used when Registrar drops a student
    public void setActive(boolean active) { this.isActive = active; }
    
    // Helper getter for JTable display in StudentRegistrationJPanel
    public String getEnrollmentStatus() { return isActive ? "Enrolled" : "Dropped"; }
}
