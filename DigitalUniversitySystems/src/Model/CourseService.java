/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.User.UserAccountDirectory; // Needed for findFacultyByName
import Model.accesscontrol.ConfigureJTable; // Central data source
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author jayan
 */
public class CourseService {
    
    public CourseOffering findCourseOffering(String courseID, String semester) {
        return ConfigureJTable.courseOfferingList.stream()
                .filter(o -> o.getCourse().getCourseID().equals(courseID) && o.getSemester().equals(semester))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Placeholder method: Finds a specific course object by ID.
     */
    public Course getCourseById(String courseID) {
        return ConfigureJTable.courseList.stream()
                .filter(c -> c.getCourseID().equals(courseID))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Placeholder method: Finds a Faculty profile by name (for assignment/reassignment).
     * NOTE: In a real system, you would search the UserAccountDirectory.
     */
    public Faculty findFacultyByName(String name) {
        // You need to pass the UserAccountDirectory instance to this service or make it globally accessible.
        // Assuming ConfigureJTable.directory is available (for mock purposes)
        UserAccountDirectory directory = ConfigureJTable.directory;
        if (directory == null) return null;
        
        return (Faculty) directory.getUserAccountList().stream()
                .filter(ua -> ua.getProfile() instanceof Faculty)
                .filter(ua -> ua.getProfile().getPerson().getName().equalsIgnoreCase(name))
                .map(ua -> ua.getProfile())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Updates an existing CourseOffering (Registrar responsibility).
     */
    public boolean updateCourseOffering(String courseID, String semester, Faculty newFaculty, int capacity, String roomTime) {
        
        // RESOLVES ERROR 1 & 2: By defining findCourseOffering and having it return the object
        CourseOffering existing = findCourseOffering(courseID, semester); 
        if (existing == null) {
            return false;
        }
        
        // Update fields managed by the Registrar
        existing.setFaculty(newFaculty);
        existing.setCapacity(capacity);
        existing.setSchedule(roomTime);
        
        return true;
    }

    /**
     * Deletes an existing CourseOffering (Registrar responsibility).
     */
    public boolean deleteOffering(String courseID, String semester) {
        CourseOffering offering = findCourseOffering(courseID, semester);
        if (offering != null) {
            if (offering.getEnrolledCount() > 0) {
                return false; 
            }
            return ConfigureJTable.courseOfferingList.remove(offering);
        }
        return false;
    }
    
    // --- Additional Required Getter ---
    public List<CourseOffering> getOfferingsBySemester(String semester) {
        return ConfigureJTable.courseOfferingList.stream()
                .filter(o -> o.getSemester().equals(semester))
                .collect(Collectors.toList());
    }
}


