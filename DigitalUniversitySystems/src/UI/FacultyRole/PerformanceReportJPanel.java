package UI.FacultyRole;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import Model.*;
import Model.Faculty;
import Model.User.UserAccount;
import Model.accesscontrol.ConfigureJTable; // Central Data Store
import Model.accesscontrol.GradeCalculator; // GPA logic
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author talha
 */
public class PerformanceReportJPanel extends javax.swing.JPanel {
    private JPanel workArea;
    private UserAccount userAccount;
    private Faculty faculty;
    private Map<String, List<CoursePerformanceData>> semesterCoursesMap; // Semester -> List of Performance Summaries
    
    // Inner class to hold calculated data for report generation
    private class CoursePerformanceData {
        CourseOffering courseOffering;
        double averageGrade;
        Map<String, Long> gradeDistribution; // Letter grade -> count
        int enrollmentCount;
        
        public CoursePerformanceData(CourseOffering courseOffering) {
            this.courseOffering = courseOffering;
            this.averageGrade = 0.0;
            this.gradeDistribution = new HashMap<>();
            this.enrollmentCount = 0;
        }
    }
    
    public PerformanceReportJPanel(JPanel workArea, UserAccount userAccount) {
        this.workArea = workArea;
        this.userAccount = userAccount;
        this.faculty = (Faculty) userAccount.getProfile();
        initComponents();
        
        loadFacultyData(); 
        populateSemesterDropdown();
        
        cmbSelectSemester.addActionListener(e -> updateCourseDropdown());
        cmbSelectCourse.addActionListener(e -> displayPerformanceReport());
    }
    
    /**
     * Loads the faculty's assigned courses and enrollments from the central store.
     * This replaces the old hardcoded initializeMockData().
     */
    private void loadFacultyData() {
        int facultyUNID = faculty.getPerson().getUNID();
        
        // 1. Get all CourseOfferings assigned to this faculty
        List<CourseOffering> assignedOfferings = ConfigureJTable.courseOfferingList.stream()
            .filter(o -> o.getFaculty() != null && o.getFaculty().getPerson().getUNID() == facultyUNID)
            .collect(Collectors.toList());
        
        // 2. Map Performance Data: Group by semester and calculate current metrics
        semesterCoursesMap = assignedOfferings.stream()
            .map(offering -> calculatePerformanceMetrics(offering))
            .collect(Collectors.groupingBy(
                perf -> perf.courseOffering.getSemester(),
                Collectors.mapping(perf -> perf, Collectors.toList())
            ));
    }

    /**
     * Calculates GPA, Distribution, and Count for a single Course Offering.
     */
    private CoursePerformanceData calculatePerformanceMetrics(CourseOffering offering) {
        CoursePerformanceData perfData = new CoursePerformanceData(offering);
        
        // Filter active and graded enrollments for this specific offering
        List<Enrollment> gradedEnrollments = ConfigureJTable.enrollmentList.stream()
            .filter(e -> e.getCourseOffering() == offering && e.getGrade() != null && !e.getGrade().equalsIgnoreCase("N/A"))
            .collect(Collectors.toList());

        perfData.enrollmentCount = offering.getEnrolledCount();
        
        if (gradedEnrollments.isEmpty()) {
            return perfData;
        }
        
        // Calculate GPA sum and distribution
        double totalQualityPoints = 0.0;
        int totalCredits = 0;
        
        // Calculate distribution and quality points
        Map<String, Long> gradeDistribution = new HashMap<>();
        
        for (Enrollment e : gradedEnrollments) {
            String grade = e.getGrade();
            double gpa = GradeCalculator.letterGradeToGPA(grade);
            int credits = e.getCourseOffering().getCourse().getCredits();
            
            if (gpa >= 0) {
                totalQualityPoints += gpa * credits;
                totalCredits += credits;
                
                // Tally grade distribution
                gradeDistribution.put(grade, gradeDistribution.getOrDefault(grade, 0L) + 1);
            }
        }
        
        perfData.averageGrade = totalCredits > 0 ? totalQualityPoints / totalCredits : 0.0;
        perfData.gradeDistribution = gradeDistribution;
        
        return perfData;
    }

    /**
     * Populate semester dropdown (FIX: Now uses live data keys)
     */
    private void populateSemesterDropdown() {
        cmbSelectSemester.removeAllItems();
        cmbSelectSemester.addItem("-- Select Semester --");
        
        // FIX: The dropdown now correctly pulls the unique semester names from the map keys
        semesterCoursesMap.keySet().stream()
            .forEach(cmbSelectSemester::addItem);
    }

    // ... (updateCourseDropdown and displayPerformanceReport remain largely the same, relying on the updated semesterCoursesMap structure) ...

    /**
     * Update course dropdown based on selected semester
     */
    private void updateCourseDropdown() {
        cmbSelectCourse.removeAllItems();
        cmbSelectCourse.addItem("-- Select Course --");
        
        String selectedSemester = (String) cmbSelectSemester.getSelectedItem();
        
        if (selectedSemester == null || selectedSemester.equals("-- Select Semester --")) {
            clearReportFields();
            return;
        }
        
        List<CoursePerformanceData> courses = semesterCoursesMap.get(selectedSemester);
        
        if (courses != null) {
            for (CoursePerformanceData perfData : courses) {
                CourseOffering offering = perfData.courseOffering;
                cmbSelectCourse.addItem(offering.getCourseID() + " - " + offering.getCourseName());
            }
        }
        
        clearReportFields();
    }

    /**
     * Display performance report for selected course
     */
    private void displayPerformanceReport() {
        String selectedSemester = (String) cmbSelectSemester.getSelectedItem();
        int courseIndex = cmbSelectCourse.getSelectedIndex();
        
        if (selectedSemester == null || selectedSemester.equals("-- Select Semester --") ||
            courseIndex <= 0) {
            clearReportFields();
            return;
        }
        
        List<CoursePerformanceData> courses = semesterCoursesMap.get(selectedSemester);
        
        if (courses != null && courseIndex - 1 < courses.size()) {
            CoursePerformanceData perfData = courses.get(courseIndex - 1);
            
            // Display average grade
            txtAvgGrade.setText(String.format("%.2f (GPA)", perfData.averageGrade));
            
            // Display grade distribution
            StringBuilder distribution = new StringBuilder();
            List<String> gradeOrder = Arrays.asList("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F");
            
            for (String grade : gradeOrder) {
                if (perfData.gradeDistribution.containsKey(grade)) {
                    long count = perfData.gradeDistribution.get(grade);
                    if (distribution.length() > 0) distribution.append(", ");
                    distribution.append(grade).append(": ").append(count);
                }
            }
            
            txtGradeDistribution.setText(distribution.toString());
            
            // Display enrollment count
            txtEnrollmentCount.setText(String.valueOf(perfData.enrollmentCount));
        }
    }

    /**
     * Clear all report fields
     */
    private void clearReportFields() {
        txtAvgGrade.setText("");
        txtGradeDistribution.setText("");
        txtEnrollmentCount.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSelectSemester = new javax.swing.JLabel();
        cmbSelectSemester = new javax.swing.JComboBox<>();
        lblSelectCourse = new javax.swing.JLabel();
        cmbSelectCourse = new javax.swing.JComboBox<>();
        lblAvgGrade = new javax.swing.JLabel();
        lblGradeDistribution = new javax.swing.JLabel();
        lblEnrollmentCount = new javax.swing.JLabel();
        txtAvgGrade = new javax.swing.JTextField();
        txtGradeDistribution = new javax.swing.JTextField();
        txtEnrollmentCount = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setPreferredSize(new java.awt.Dimension(600, 465));

        lblSelectSemester.setText("Select Semester");

        lblSelectCourse.setText("Select Course ");

        lblAvgGrade.setText("Average Grade");

        lblGradeDistribution.setText("Grade Distribution");

        lblEnrollmentCount.setText("Enrollment Count");

        txtAvgGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAvgGradeActionPerformed(evt);
            }
        });

        txtGradeDistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGradeDistributionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblSelectCourse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSelectSemester, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSelectSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSelectCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAvgGrade)
                            .addComponent(lblGradeDistribution)
                            .addComponent(lblEnrollmentCount))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEnrollmentCount, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtAvgGrade, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                .addComponent(txtGradeDistribution)))))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectSemester)
                    .addComponent(cmbSelectSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelectCourse)
                    .addComponent(cmbSelectCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvgGrade)
                    .addComponent(txtAvgGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGradeDistribution)
                    .addComponent(txtGradeDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEnrollmentCount)
                    .addComponent(txtEnrollmentCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(188, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAvgGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAvgGradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAvgGradeActionPerformed

    private void txtGradeDistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGradeDistributionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGradeDistributionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbSelectCourse;
    private javax.swing.JComboBox<String> cmbSelectSemester;
    private javax.swing.JLabel lblAvgGrade;
    private javax.swing.JLabel lblEnrollmentCount;
    private javax.swing.JLabel lblGradeDistribution;
    private javax.swing.JLabel lblSelectCourse;
    private javax.swing.JLabel lblSelectSemester;
    private javax.swing.JTextField txtAvgGrade;
    private javax.swing.JTextField txtEnrollmentCount;
    private javax.swing.JTextField txtGradeDistribution;
    // End of variables declaration//GEN-END:variables
}
