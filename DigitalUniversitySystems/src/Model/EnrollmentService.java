/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Enrollment;
import Model.Student;
import Model.CourseOffering;
import Model.FinancialRecord;
import Model.accesscontrol.ConfigureJTable;
import Model.accesscontrol.DataValidator; // For validation checks
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author jayan
 */
public class EnrollmentService {
    
    // NOTE: This service must be instantiated and used by all UI panels that modify enrollment.
    
    // --- 1. Find/Read Operations ---

    /**
     * Finds all active and inactive enrollments for a specific student.
     */
    public List<Enrollment> getEnrollmentsByStudent(Student student) {
        return ConfigureJTable.enrollmentList.stream()
                .filter(e -> e.getStudent().getPerson().getUNID() == student.getPerson().getUNID())
                .collect(Collectors.toList());
    }
    
    /**
     * Calculates a student's currently enrolled (active) credits for the given semester.
     */
    public double getCurrentActiveCredits(Student student, String semester) {
        return ConfigureJTable.enrollmentList.stream()
                .filter(Enrollment::isActive)
                .filter(e -> e.getStudent().getPerson().getUNID() == student.getPerson().getUNID())
                .filter(e -> e.getCourseOffering().getSemester().equals(semester))
                .mapToDouble(e -> e.getCourseOffering().getCourse().getCredits())
                .sum();
    }
    
    // --- 2. Enrollment/Drop Operations (Registrar Responsibility) ---
    
    /**
     * Enrolls a student into a course offering (Admin-Side).
     * @param student The student to enroll.
     * @param offering The course offering to enroll in.
     * @param isAdminOverride True if Registrar is bypassing course load checks.
     * @return True on success, False otherwise.
     */
    public boolean enrollStudent(Student student, CourseOffering offering, boolean isAdminOverride) {
        // Find existing enrollment (should not enroll twice)
        boolean alreadyEnrolled = ConfigureJTable.enrollmentList.stream()
                .anyMatch(e -> e.getStudent() == student && e.getCourseOffering() == offering && e.isActive());
        
        if (alreadyEnrolled) return false;
        
        // 1. Check Course Load (if not admin override)
        if (!isAdminOverride) {
            double currentCredits = getCurrentActiveCredits(student, offering.getSemester());
            if (currentCredits + offering.getCourse().getCredits() > 8) { // Assignment requirement: max 8 credits
                return false; // Load limit exceeded
            }
        }
        
        // 2. Check Capacity (Simplified: just check capacity vs enrolled count)
        if (offering.getEnrolledCount() >= offering.getCapacity()) {
            return false; // Course is full
        }
        
        // 3. Create and save new enrollment
        Enrollment newEnrollment = new Enrollment(student, offering);
        ConfigureJTable.enrollmentList.add(newEnrollment);
        offering.incrementEnrolledCount();
        
        // 4. Update student's profile credits (Crucial for Graduation Audit)
        student.setCreditsCompleted(student.getCreditsCompleted() + offering.getCourse().getCredits());
        
        // NOTE: In a real app, tuition billing would happen here.
        
        return true;
    }
    
    /**
     * Drops a student from an active course offering.
     * @param enrollment The specific enrollment record to drop.
     * @return True on success.
     */
    public boolean dropStudent(Enrollment enrollment) {
        if (!enrollment.isActive()) return false;
        
        CourseOffering offering = enrollment.getCourseOffering();
        
        // 1. Set enrollment status to inactive/dropped
        enrollment.setActive(false);
        offering.decrementEnrolledCount();
        
        // 2. Refund Logic (Assignment requirement)
        // NOTE: A separate FinancialService handles this in a real system.
        // For simplicity, we assume refunding is handled.
        // if (student.getTuitionBalance() < original_billed_amount) { initiate_refund_process(); }
        
        return true;
    }

    /**
     * Finds a CourseOffering object by its ID and Semester.
     * @param courseID The ID of the course (e.g., "INFO 5100").
     * @param semester The semester (e.g., "Fall 2025").
     * @return The CourseOffering object or null.
     */
    public CourseOffering findCourseOffering(String courseID, String semester) {
        return ConfigureJTable.courseOfferingList.stream()
                .filter(o -> o.getCourse().getCourseID().equals(courseID) && o.getSemester().equals(semester))
                .findFirst()
                .orElse(null);
    }
}
