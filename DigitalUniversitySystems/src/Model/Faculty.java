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

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses, Department department) {
        this.courses = courses;
        this.department = department;
    }

    public Faculty(Person p, Department department) {
        super(p);
        this.courses = new ArrayList<>();
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Faculty";
    }
    
    
    // Override toString for easy display in JTables or dropdowns
    @Override
    public String toString() {
        return this.getPerson().getName() + " (" + this.getPerson().getUNID() + ")";
    }
}
