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
public class Faculty {
    private ArrayList<Course> courses;

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Faculty() {
        this.courses = new ArrayList<>();
    }
    
    
    // Override toString for easy display in JTables or dropdowns
    @Override
    public String toString() {
        return getName() + " (" + getUNID() + ")";
    }
}
