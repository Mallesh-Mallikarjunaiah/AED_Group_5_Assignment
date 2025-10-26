/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.FacultyRole;
import Model.*;
import Model.Faculty;
import Model.User.UserAccount;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.*;
/**
 *
 * @author talha
 */
public class PerformanceReportJPanel extends javax.swing.JPanel {
    private JPanel workArea;
    private UserAccount userAccount;
    private Faculty faculty;
    private Map<String, List<CoursePerformanceData>> semesterCoursesMap;
    private class CoursePerformanceData {
        CourseOffering courseOffering;
        double averageGrade;
        Map<String, Integer> gradeDistribution; // Letter grade -> count
        int enrollmentCount;
        
        public CoursePerformanceData(CourseOffering courseOffering) {
            this.courseOffering = courseOffering;
            this.averageGrade = 0.0;
            this.gradeDistribution = new HashMap<>();
            this.enrollmentCount = 0;
        }
    }
    /**
     * Creates new form PerformanceReportJPanel
     */
    public PerformanceReportJPanel(JPanel workArea, UserAccount userAccount) {
        this.workArea = workArea;
        this.userAccount = userAccount;
        this.faculty = (Faculty) userAccount.getProfile();
        initComponents();
        initializeMockData();
        populateSemesterDropdown();
        cmbSelectSemester.addActionListener(e -> updateCourseDropdown());
        cmbSelectCourse.addActionListener(e -> displayPerformanceReport());
        
    }
    
    private void initializeMockData() {
        semesterCoursesMap = new HashMap<>();
        
        // Fall 2024 courses
        List<CoursePerformanceData> fall2024Courses = new ArrayList<>();
        
        Course course1 = new Course("CS5010", "Program Design Paradigm", 4);
        Course course2 = new Course("CS5800", "Algorithms", 4);
        
        CourseOffering offering1 = new CourseOffering(course1, "Fall 2024", faculty, 60, "Mon/Wed 2:00-3:30 PM");
        CourseOffering offering2 = new CourseOffering(course2, "Fall 2024", faculty, 50, "Tue/Thu 10:00-11:30 AM");
        
        // CS5010 Performance Data
        CoursePerformanceData perf1 = new CoursePerformanceData(offering1);
        perf1.averageGrade = 85.5;
        perf1.enrollmentCount = 45;
        perf1.gradeDistribution.put("A", 12);
        perf1.gradeDistribution.put("A-", 8);
        perf1.gradeDistribution.put("B+", 10);
        perf1.gradeDistribution.put("B", 8);
        perf1.gradeDistribution.put("B-", 4);
        perf1.gradeDistribution.put("C+", 2);
        perf1.gradeDistribution.put("C", 1);
        
        // CS5800 Performance Data
        CoursePerformanceData perf2 = new CoursePerformanceData(offering2);
        perf2.averageGrade = 78.3;
        perf2.enrollmentCount = 38;
        perf2.gradeDistribution.put("A", 8);
        perf2.gradeDistribution.put("A-", 6);
        perf2.gradeDistribution.put("B+", 7);
        perf2.gradeDistribution.put("B", 9);
        perf2.gradeDistribution.put("B-", 5);
        perf2.gradeDistribution.put("C+", 2);
        perf2.gradeDistribution.put("C", 1);
        
        fall2024Courses.add(perf1);
        fall2024Courses.add(perf2);
        
        semesterCoursesMap.put("Fall 2024", fall2024Courses);
        
        // Spring 2025 courses
        List<CoursePerformanceData> spring2025Courses = new ArrayList<>();
        
        Course course3 = new Course("CS6220", "Data Mining", 3);
        CourseOffering offering3 = new CourseOffering(course3, "Spring 2025", faculty, 40, "Mon/Wed 6:00-7:30 PM");
        
        CoursePerformanceData perf3 = new CoursePerformanceData(offering3);
        perf3.averageGrade = 82.7;
        perf3.enrollmentCount = 35;
        perf3.gradeDistribution.put("A", 10);
        perf3.gradeDistribution.put("A-", 7);
        perf3.gradeDistribution.put("B+", 8);
        perf3.gradeDistribution.put("B", 6);
        perf3.gradeDistribution.put("B-", 3);
        perf3.gradeDistribution.put("C+", 1);
        
        spring2025Courses.add(perf3);
        
        semesterCoursesMap.put("Spring 2025", spring2025Courses);
    }

    /**
     * Populate semester dropdown
     */
    private void populateSemesterDropdown() {
        cmbSelectSemester.removeAllItems();
        cmbSelectSemester.addItem("-- Select Semester --");
        
        for (String semester : semesterCoursesMap.keySet()) {
            cmbSelectSemester.addItem(semester);
        }
    }

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
            txtAvgGrade.setText(String.format("%.2f%%", perfData.averageGrade));
            
            // Display grade distribution
            StringBuilder distribution = new StringBuilder();
            List<String> gradeOrder = Arrays.asList("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F");
            
            for (String grade : gradeOrder) {
                if (perfData.gradeDistribution.containsKey(grade)) {
                    int count = perfData.gradeDistribution.get(grade);
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
