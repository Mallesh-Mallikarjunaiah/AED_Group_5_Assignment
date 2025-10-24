/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author jayan
 */
public class CourseOffering {
    private Course course; // Reference to base course
    private String semester;
    private Faculty faculty;    // Responsibility: Assign faculty 
    private int capacity;       // Responsibility: Set enrollment capacity 
    private String schedule;    // Responsibility: Update room/time schedules 
    private int enrolledCount;  // Tracks current enrollment for capacity checks

    public CourseOffering(Course course, String semester, Faculty faculty, int capacity, String schedule) {
        this.course = course;
        this.semester = semester;
        this.faculty = faculty;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledCount = 0; // Initialize count
    }

    // Getters and Setters (needed for CourseOfferingJPanel Edit/Save/Assign functions)
    public Course getCourse() { return course; }
    public String getSemester() { return semester; }
    
    // Getters/Setters for Registrar-managed fields
    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    
    // Enrollment tracking
    public int getEnrolledCount() { return enrolledCount; }
    public void incrementEnrolledCount() { this.enrolledCount++; }
    public void decrementEnrolledCount() { this.enrolledCount = Math.max(0, this.enrolledCount - 1); }

    // Display for the Registrar JTable (e.g., CourseOfferingJPanel)
    public String getCourseID() { return course.getCourseID(); }
    public String getCourseName() { return course.getName(); }
}
