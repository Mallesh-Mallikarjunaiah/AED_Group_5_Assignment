/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.FacultyRole;

import Model.*;
import Model.Faculty;
import Model.User.UserAccount;
import Model.accesscontrol.ConfigureJTable;
import Model.accesscontrol.GradeCalculator; // Use the provided GradeCalculator
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author talha
 */
public class StudentGradingJPanel extends javax.swing.JPanel {
    private JPanel workArea;
    private UserAccount userAccount;
    private Faculty faculty;
    private List<CourseOffering> facultyCourses;
    private Map<String, List<StudentGradeData>> courseStudentsMap; // CourseID -> List of students
    private CourseOffering selectedCourseOffering;
    
    // Inner class to hold temporary student data for grading session
    private class StudentGradeData {
        Student student;
        // Scores are stored as doubles, initialized to 0.0
        double assignmentScore;
        double midtermScore;
        double finalScore;
        double totalPercentage;
        String letterGrade;
        
        public StudentGradeData(Student student) {
            this.student = student;
            this.assignmentScore = 0.0;
            this.midtermScore = 0.0;
            this.finalScore = 0.0;
            this.totalPercentage = 0.0;
            this.letterGrade = "N/A";
        }
    }    
    
    public StudentGradingJPanel(JPanel workArea, UserAccount userAccount) {
        this.workArea = workArea;
        this.userAccount = userAccount;
        this.faculty = (Faculty) userAccount.getProfile();
        initComponents();
        
        loadFacultyData(); // Load courses and students from ConfigureJTable
        populateCourseDropdown();
        
        // Add listeners
        cmbSelectCourse.addActionListener(e -> loadStudentsForSelectedCourse());
        btnComputeGrade.addActionListener(e -> computeFinalGrades());
        btnRankStudents.addActionListener(e -> rankStudents());
        btnViewTranscriptSummary.addActionListener(e -> viewTranscriptSummary());
        btnSaveGrade.addActionListener(e -> saveGrades());
    }
    
    /**
     * Loads the faculty's assigned courses from the central store and 
     * simulates loading enrolled students (based on ConfigureJTable.enrollmentList).
     */
    private void loadFacultyData() {
        // 1. Get courses assigned to this faculty
        int facultyUNID = faculty.getPerson().getUNID();
        facultyCourses = ConfigureJTable.courseOfferingList.stream()
            .filter(o -> o.getFaculty() != null && o.getFaculty().getPerson().getUNID() == facultyUNID)
            .collect(Collectors.toList());
        
        courseStudentsMap = new HashMap<>();

        // 2. Simulate loading active students for each assigned course
        for (CourseOffering offering : facultyCourses) {
            String courseID = offering.getCourseID();
            
            List<StudentGradeData> enrolledStudents = ConfigureJTable.enrollmentList.stream()
                .filter(e -> e.getCourseOffering().getCourseID().equals(courseID) && e.isActive())
                .map(e -> new StudentGradeData(e.getStudent()))
                .collect(Collectors.toList());
            
            courseStudentsMap.put(courseID, enrolledStudents);
        }
    }

    /**
     * Populate course dropdown with faculty's courses
     */
    private void populateCourseDropdown() {
        cmbSelectCourse.removeAllItems();
        cmbSelectCourse.addItem("-- Select Course --");
        
        for (CourseOffering offering : facultyCourses) {
            cmbSelectCourse.addItem(offering.getCourseID() + " - " + offering.getCourseName());
        }
    }

    /**
     * Load students for the selected course into the JTable
     */
    private void loadStudentsForSelectedCourse() {
        int selectedIndex = cmbSelectCourse.getSelectedIndex();
        if (selectedIndex <= 0) { // Index 0 is "-- Select Course --"
            DefaultTableModel model = (DefaultTableModel) tblStudent.getModel();
            model.setRowCount(0);
            return;
        }
        
        // Adjust index because of the placeholder item
        selectedCourseOffering = facultyCourses.get(selectedIndex - 1);
        String courseID = selectedCourseOffering.getCourseID();
        
        List<StudentGradeData> students = courseStudentsMap.get(courseID);
        
        DefaultTableModel model = (DefaultTableModel) tblStudent.getModel();
        model.setRowCount(0); // Clear table
        
        if (students != null) {
            for (StudentGradeData data : students) {
                model.addRow(new Object[]{
                    data.student.getPerson().getUNID(),
                    data.student.getPerson().getName(),
                    data.assignmentScore,
                    data.midtermScore,
                    data.finalScore,
                    String.format("%.2f", data.totalPercentage),
                    data.letterGrade
                });
            }
        }
        
        // Clear summary fields
        txtClassAvgGPA.setText("");
        txtTopPerformer.setText("");
    }

    /**
     * Compute final grades based on entered scores
     * Grading scheme: Assignments 30%, Midterm 30%, Final 40% (Assignment Requirement)
     */
    private void computeFinalGrades() {
        if (selectedCourseOffering == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first.", "No Course Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblStudent.getModel();
        String courseID = selectedCourseOffering.getCourseID();
        List<StudentGradeData> students = courseStudentsMap.get(courseID);
        
        if (students == null || students.isEmpty()) { return; }
        
        try {
            // Read scores from table and compute grades
            for (int i = 0; i < model.getRowCount(); i++) {
                StudentGradeData data = students.get(i);
                
                // Read scores from table (Editable columns: 2, 3, 4)
                double assignScore = Double.parseDouble(model.getValueAt(i, 2).toString());
                double midtermScore = Double.parseDouble(model.getValueAt(i, 3).toString());
                double finalScore = Double.parseDouble(model.getValueAt(i, 4).toString());
                
                // Validate scores (0-100)
                if (!isValidScore(assignScore) || !isValidScore(midtermScore) || !isValidScore(finalScore)) {
                    JOptionPane.showMessageDialog(this, 
                        "All scores must be between 0 and 100 for student: " + data.student.getPerson().getName(), 
                        "Invalid Score", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update data object
                data.assignmentScore = assignScore;
                data.midtermScore = midtermScore;
                data.finalScore = finalScore;
                
                // Calculate total percentage: Assignments 30%, Midterm 30%, Final 40%
                data.totalPercentage = GradeCalculator.calculateWeightedTotal(assignScore, midtermScore, finalScore);
                
                // Calculate letter grade
                data.letterGrade = GradeCalculator.calculateLetterGrade(data.totalPercentage);
                
                // Update table (Total=5, Grade=6)
                model.setValueAt(String.format("%.2f", data.totalPercentage), i, 5);
                model.setValueAt(data.letterGrade, i, 6);
            }
            
            JOptionPane.showMessageDialog(this, "Final grades computed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric scores for all students.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveGrades() {
        // NOTE: In a real system, this would update the central Enrollment list.
        // For simplicity, we assume computeFinalGrades() has already updated the local map.
        
        JOptionPane.showMessageDialog(this, 
            "Grades saved successfully to the system! (Local Mock Update)", 
            "Grades Saved", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isValidScore(double score) {
        return score >= 0 && score <= 100;
    }

    /**
     * Rank students by total grade percentage and show class GPA (Assignment Requirement)
     */
    private void rankStudents() {
        if (selectedCourseOffering == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first.", "No Course Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseID = selectedCourseOffering.getCourseID();
        List<StudentGradeData> students = courseStudentsMap.get(courseID);
        
        if (students == null || students.isEmpty()) { return; }
        
        // Check if grades are computed
        boolean gradesComputed = students.stream().anyMatch(data -> data.totalPercentage > 0);
        
        if (!gradesComputed) {
            JOptionPane.showMessageDialog(this, "Please compute final grades first.", "Grades Not Computed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Sort students by total percentage (descending)
        List<StudentGradeData> rankedStudents = new ArrayList<>(students);
        rankedStudents.sort((s1, s2) -> Double.compare(s2.totalPercentage, s1.totalPercentage));
        
        // Calculate class average GPA
        double totalQualityPoints = 0.0;
        int totalCredits = 0;
        
        for (StudentGradeData data : rankedStudents) {
            if (!data.letterGrade.equals("N/A")) {
                // Use GradeCalculator to get points
                double gpa = GradeCalculator.letterGradeToGPA(data.letterGrade);
                int credits = selectedCourseOffering.getCourse().getCredits();
                totalQualityPoints += gpa * credits;
                totalCredits += credits;
            }
        }
        
        double avgGPA = totalCredits > 0 ? totalQualityPoints / totalCredits : 0.0;
        
        // Update UI
        txtClassAvgGPA.setText(String.format("%.2f", avgGPA));
        
        if (!rankedStudents.isEmpty()) {
            StudentGradeData topStudent = rankedStudents.get(0);
            txtTopPerformer.setText(topStudent.student.getPerson().getName() + 
                " (" + String.format("%.2f", topStudent.totalPercentage) + "%)");
        }
        
        // Re-populate table with ranked order
        DefaultTableModel model = (DefaultTableModel) tblStudent.getModel();
        model.setRowCount(0);
        
        for (StudentGradeData data : rankedStudents) {
            model.addRow(new Object[]{
                data.student.getPerson().getUNID(),
                data.student.getPerson().getName(),
                String.format("%.2f", data.assignmentScore),
                String.format("%.2f", data.midtermScore),
                String.format("%.2f", data.finalScore),
                String.format("%.2f", data.totalPercentage),
                data.letterGrade
            });
        }
    }

    /**
     * View transcript summary for selected student (Assignment Requirement)
     */
    private void viewTranscriptSummary() {
        int selectedRow = tblStudent.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String courseID = selectedCourseOffering.getCourseID();
        List<StudentGradeData> students = courseStudentsMap.get(courseID);
        StudentGradeData selectedStudent = students.get(selectedRow);
        
        // Create transcript summary
        StringBuilder transcript = new StringBuilder();
        transcript.append("=== PROGRESS/TRANSCRIPT SUMMARY ===\n\n");
        transcript.append("Student: ").append(selectedStudent.student.getPerson().getName()).append("\n");
        transcript.append("ID: ").append(selectedStudent.student.getPerson().getUNID()).append("\n");
        transcript.append("Department: ").append(selectedStudent.student.getDepartment()).append("\n\n");
        transcript.append("--- Current Course Performance ---\n");
        transcript.append("Course: ").append(selectedCourseOffering.getCourseID()).append(" - ").append(selectedCourseOffering.getCourseName()).append("\n");
        transcript.append("Assignment Score: ").append(String.format("%.2f", selectedStudent.assignmentScore)).append("\n");
        transcript.append("Midterm Score: ").append(String.format("%.2f", selectedStudent.midtermScore)).append("\n");
        transcript.append("Final Exam Score: ").append(String.format("%.2f", selectedStudent.finalScore)).append("\n");
        transcript.append("Total Percentage: ").append(String.format("%.2f", selectedStudent.totalPercentage)).append("%\n");
        transcript.append("Letter Grade: ").append(selectedStudent.letterGrade).append("\n\n");
        transcript.append("--- Overall Academic Record (MOCK) ---\n");
        transcript.append("Overall GPA: ").append(String.format("%.2f", selectedStudent.student.getOverallGPA())).append("\n");
        transcript.append("Academic Standing: ").append(selectedStudent.student.getAcademicStanding()).append("\n");
        
        JOptionPane.showMessageDialog(this, transcript.toString(), "Transcript Summary", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbSelectCourse = new javax.swing.JComboBox<>();
        lblSelectCourse = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        btnComputeGrade = new javax.swing.JButton();
        btnSaveGrade = new javax.swing.JButton();
        btnViewTranscriptSummary = new javax.swing.JButton();
        btnRankStudents = new javax.swing.JButton();
        lblClassAvgGPA = new javax.swing.JLabel();
        lblTopPerformer = new javax.swing.JLabel();
        txtClassAvgGPA = new javax.swing.JTextField();
        txtTopPerformer = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setPreferredSize(new java.awt.Dimension(600, 465));

        lblSelectCourse.setText("Select Course :");

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Name", "Assign Score", "Midterm", "Final", "Total", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        btnComputeGrade.setText("Compute Final Grade");
        btnComputeGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComputeGradeActionPerformed(evt);
            }
        });

        btnSaveGrade.setText("Save Grades");
        btnSaveGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveGradeActionPerformed(evt);
            }
        });

        btnViewTranscriptSummary.setText("View Transcript Summary");

        btnRankStudents.setText("Rank Students");

        lblClassAvgGPA.setText("Class Avg GPA : ");

        lblTopPerformer.setText("Top Performer : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblSelectCourse)
                        .addGap(33, 33, 33)
                        .addComponent(cmbSelectCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnViewTranscriptSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRankStudents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveGrade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComputeGrade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClassAvgGPA)
                            .addComponent(lblTopPerformer))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClassAvgGPA, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtTopPerformer))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectCourse)
                    .addComponent(cmbSelectCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnComputeGrade)
                .addGap(18, 18, 18)
                .addComponent(btnSaveGrade)
                .addGap(18, 18, 18)
                .addComponent(btnViewTranscriptSummary)
                .addGap(18, 18, 18)
                .addComponent(btnRankStudents)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClassAvgGPA)
                    .addComponent(txtClassAvgGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTopPerformer)
                    .addComponent(txtTopPerformer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveGradeActionPerformed
        // TODO add your handling code here:
        if (selectedCourseOffering == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first.", "No Course Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // In a real application, you would save the grades to a database
        // For now, we just show a confirmation message
        JOptionPane.showMessageDialog(this, 
            "Grades saved successfully to the system!\n" +
            "Course: " + selectedCourseOffering.getCourseID() + " - " + 
            selectedCourseOffering.getCourseName() + "\n\n" +
            "Note: In production, this would persist to a database.", 
            "Grades Saved", 
            JOptionPane.INFORMATION_MESSAGE);       
    }//GEN-LAST:event_btnSaveGradeActionPerformed

    private void btnComputeGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComputeGradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnComputeGradeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComputeGrade;
    private javax.swing.JButton btnRankStudents;
    private javax.swing.JButton btnSaveGrade;
    private javax.swing.JButton btnViewTranscriptSummary;
    private javax.swing.JComboBox<String> cmbSelectCourse;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClassAvgGPA;
    private javax.swing.JLabel lblSelectCourse;
    private javax.swing.JLabel lblTopPerformer;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextField txtClassAvgGPA;
    private javax.swing.JTextField txtTopPerformer;
    // End of variables declaration//GEN-END:variables
}
