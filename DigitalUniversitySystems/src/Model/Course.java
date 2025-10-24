/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author jayan
 */
public class Course {
    private String courseID;
    private String name;
    private int credits;

    public Course(String courseID, String name, int credits) {
        this.courseID = courseID;
        this.name = name;
        this.credits = credits;
    }

    // Getters
    public String getCourseID() {
        return courseID;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}
