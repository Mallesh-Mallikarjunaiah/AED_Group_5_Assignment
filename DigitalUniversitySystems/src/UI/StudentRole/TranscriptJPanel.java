/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.StudentRole;

import Model.*;
import Model.accesscontrol.ConfigureJTable; // Central Data Source
import Model.accesscontrol.GradeCalculator; // Use GradeCalculator for GPA conversion
import Model.User.UserAccount;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author MALLESH
 */
public class TranscriptJPanel extends javax.swing.JPanel {
    private JPanel workArea;
    private UserAccount userAccount;
    private Student student;
    private List<Enrollment> allEnrollments; // All graded/completed enrollments for the student

    // Constructor with data initialization
    public TranscriptJPanel(JPanel workArea, UserAccount account) {
        initComponents();
        this.workArea = workArea;
        this.userAccount = account;
        this.student = (Student) account.getProfile();
        
        // Load persistent data from central store
        loadAllCompletedEnrollments();
        
        // Make text fields read-only
        txtTermGPA.setEditable(false);
        txtOverallGPA.setEditable(false);

        populateSemesterComboBox();
        checkTuitionStatusAndDisplay();
    }
    
    /**
     * Loads all completed/graded enrollments from the central store.
     */
    private void loadAllCompletedEnrollments() {
        int studentUNID = student.getPerson().getUNID();
        
        // Filter the central list for enrollments belonging to this student AND marked completed/graded
        this.allEnrollments = ConfigureJTable.enrollmentList.stream()
            .filter(e -> e.getStudent().getPerson().getUNID() == studentUNID)
            .filter(Enrollment::isCompleted)
            .collect(Collectors.toList());
        
        // Initial calculation to set Overall GPA and Credits on the Student object
        calculateOverallGPA();
        student.setAcademicStanding(determineAcademicStanding(student.getOverallGPA(), student.getOverallGPA()));
    }
    
    /**
     * CRITICAL REQUIREMENT: Checks if the student's tuition is paid and controls visibility.
     */
    private void checkTuitionStatusAndDisplay() {
        if (student.getTuitionBalance() > 0.0) {
            // Tuition is owed (UNPAID)
            JScrollPane scrollPane = (JScrollPane) jScrollPane1.getParent(); // Get parent component
            
            // Hide the table and show a warning label
            tblTranscript.setVisible(false);
            scrollPane.setViewportView(new JLabel("<html><h2 style='color:red;'>ACCESS DENIED:</h2>Tuition balance must be paid to view transcript.<br>Current Balance: $" + String.format("%.2f", student.getTuitionBalance()) + "</html>", SwingConstants.CENTER));
            
            // Hide GPA summary
            txtTermGPA.setText("---");
            txtOverallGPA.setText("---");
            btnFilter.setEnabled(false);
        } else {
            // Paid off or balance is 0 or negative
            tblTranscript.setVisible(true);
            populateTranscriptTable(null); // Load all semesters by default
            btnFilter.setEnabled(true);
        }
    }

    /**
     * Populate semester dropdown with unique completed semesters.
     */
    private void populateSemesterComboBox() {
        Set<String> semesters = new LinkedHashSet<>();
        semesters.add("All Semesters");
        
        allEnrollments.stream()
            .map(e -> e.getCourseOffering().getSemester())
            .forEach(semesters::add);
        
        comboBoxSemester.removeAllItems();
        semesters.forEach(comboBoxSemester::addItem);
    }

    /**
     * Populates transcript table with filtering by semester.
     */
    private void populateTranscriptTable(String semesterFilter) {
        if (student.getTuitionBalance() > 0.0) {
            return; // Gatekeeper check
        }
        
        DefaultTableModel model = (DefaultTableModel) tblTranscript.getModel();
        model.setRowCount(0);

        for (Enrollment e : allEnrollments) {
            String semester = e.getCourseOffering().getSemester();
            
            // Apply semester filter
            if (semesterFilter != null && !semesterFilter.equals("All Semesters") &&
                !semester.equalsIgnoreCase(semesterFilter)) {
                continue;
            }
            
            CourseOffering co = e.getCourseOffering();
            double termGPAForCourse = GradeCalculator.letterGradeToGPA(e.getGrade());
            
            // Calculate Term GPA just for the purpose of getting the Academic Standing for the displayed term
            double currentTermGPA = 0; 
            if (!semester.equals("All Semesters")) {
                currentTermGPA = calculateTermGPAForDisplay(semester);
            }
            
            Object[] row = new Object[7];
            row[0] = semester;
            row[1] = co.getCourse().getCourseID();
            row[2] = co.getCourse().getName();
            row[3] = e.getGrade();
            row[4] = String.format("%.2f", currentTermGPA); // Term GPA Points
            row[5] = String.format("%.2f", student.getOverallGPA()); // Overall GPA Points
            row[6] = student.getAcademicStanding(); // Academic Standing (Based on Overall/Current Term)
            model.addRow(row);
        }
    }

    /**
     * Calculates Term GPA for the selected semester only (used by filter).
     */
    private double calculateTermGPAForDisplay(String semester) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : allEnrollments) {
            if (e.isCompleted() && e.getCourseOffering().getSemester().equalsIgnoreCase(semester)) {
                double gradePoint = GradeCalculator.letterGradeToGPA(e.getGrade());
                int credits = e.getCourseOffering().getCourse().getCredits();
                totalPoints += (gradePoint * credits);
                totalCredits += credits;
            }
        }
        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }

    /**
     * Calculates the student's Overall GPA and updates the persistent Student record.
     */
    private void calculateOverallGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : allEnrollments) {
            if (e.isCompleted()) {
                double gradePoint = GradeCalculator.letterGradeToGPA(e.getGrade());
                int credits = e.getCourseOffering().getCourse().getCredits();
                totalPoints += (gradePoint * credits);
                totalCredits += credits;
            }
        }

        double overallGPA = totalCredits == 0 ? 0 : totalPoints / totalCredits;
        
        // Update persistent student record (CRUCIAL for Grad Audit/Registrar)
        student.setOverallGPA(overallGPA);
        student.setCreditsCompleted(totalCredits);
    }

    /**
     * Determine Academic Standing based on the provided assignment rules.
     */
    private String determineAcademicStanding(double termGPA, double overallGPA) {
        if (overallGPA < 3.0) {
            return "Academic Probation"; // regardless of term GPA
        } else if (termGPA < 3.0) {
            return "Academic Warning"; // if term GPA drops below 3.0
        } else {
            return "Good Standing";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblSemester = new javax.swing.JLabel();
        comboBoxSemester = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTranscript = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lblTeamGPA = new javax.swing.JLabel();
        txtTermGPA = new javax.swing.JTextField();
        lblOverallGPA = new javax.swing.JLabel();
        txtOverallGPA = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Transcript Review");

        lblSemester.setText("Semester");

        comboBoxSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSemesterActionPerformed(evt);
            }
        });

        btnFilter.setText("Filter");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        jLabel3.setText("Transcript Display Table ");

        tblTranscript.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Term", "Course ID", "Course Name", "Grade", "Term GPA", "Overall GPA", "Academic Standing"
            }
        ));
        jScrollPane1.setViewportView(tblTranscript);

        jLabel4.setText("GPA Summary Section");

        lblTeamGPA.setText("Term GPA");

        txtTermGPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTermGPAActionPerformed(evt);
            }
        });

        lblOverallGPA.setText("Overall GPA");

        txtOverallGPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOverallGPAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnFilter))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblSemester)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTeamGPA)
                                    .addComponent(lblOverallGPA))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtOverallGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTermGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSemester)
                    .addComponent(comboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTeamGPA)
                    .addComponent(txtTermGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOverallGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOverallGPA))
                .addContainerGap(128, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTermGPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTermGPAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTermGPAActionPerformed

    private void txtOverallGPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOverallGPAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOverallGPAActionPerformed

    private void comboBoxSemesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSemesterActionPerformed
        // TODO add your handling code here:
        btnFilterActionPerformed(evt);
    }//GEN-LAST:event_comboBoxSemesterActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
        String selectedSemester = (String) comboBoxSemester.getSelectedItem();
        populateTranscriptTable(selectedSemester);
        
        // Update GPA Display
        if (selectedSemester != null && !selectedSemester.equals("All Semesters")) {
            double termGPA = calculateTermGPAForDisplay(selectedSemester);
            txtTermGPA.setText(String.format("%.2f", termGPA));
            
            // Academic standing label is based on current display (Overall remains static)
            String standing = determineAcademicStanding(termGPA, student.getOverallGPA());
            // Since this label is not in the design, we use a dialog/message
            // JOptionPane.showMessageDialog(this, "Academic Standing for " + selectedSemester + ": " + standing); 
        } else {
            // Show overall values when filtering by all semesters
            txtTermGPA.setText(String.format("%.2f", student.getOverallGPA()));
        }
    }//GEN-LAST:event_btnFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JComboBox<String> comboBoxSemester;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOverallGPA;
    private javax.swing.JLabel lblSemester;
    private javax.swing.JLabel lblTeamGPA;
    private javax.swing.JTable tblTranscript;
    private javax.swing.JTextField txtOverallGPA;
    private javax.swing.JTextField txtTermGPA;
    // End of variables declaration//GEN-END:variables
}
