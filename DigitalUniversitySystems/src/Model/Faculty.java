/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author jayan
 */
public class Faculty extends Profile{
    private ArrayList<Course> courses;
    private Department department;

    // Constructor (must come first logically)
    public Faculty(Person p, Department department) {
        super(p);
        this.courses = new ArrayList<>();
        this.department = department;
    }

    // --- Getters ---
    
    public ArrayList<Course> getCourses() {
        return courses;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public String getName() {
        return this.getPerson().getName();
    }
    
    @Override
    public String getRole() {
        return "Faculty";
    }
    
    // --- Setters (Method Signatures Preserved) ---
    
    // Setter 1: Sets both the course list and updates the department
    public void setCourses(ArrayList<Course> courses, Department department) {
        this.courses = courses;
        this.department = department;
    }

    // Setter 2: Sets only the course list
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    
    // Setter 3: Sets only the department (for reassignment)
    public void setDepartment(Department department) {
        this.department = department;
    }

    // Override toString for easy display in JTables or dropdowns
    @Override
    public String toString() {
        // Assuming Person has getName() and getUNID()
        return this.getPerson().getName() + " (" + this.getPerson().getUNID() + ")";
    }
}
