/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.StudentRole;

import Model.*;
import Model.accesscontrol.ConfigureJTable; // Central Data Source
import Model.User.UserAccount;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author MALLESH
 */
public class GraduationAuditJPanel extends javax.swing.JPanel {
    private JPanel mainWorkArea;
    private UserAccount userAccount;
    private Student student;
    
    // --- Data Source ---
    // The list of completed (graded) courses for this student
    private List<Enrollment> completedEnrollments;

    private static final int TOTAL_REQUIRED_CREDITS = 32;
    private static final String MANDATORY_COURSE_ID = "INFO 5100"; // Based on ConfigureJTable data
    
    /**
     * Constructor with persistent data initialization
     */
    public GraduationAuditJPanel(JPanel mainWorkArea, UserAccount userAccount) {
        initComponents();
        this.mainWorkArea = mainWorkArea;
        this.userAccount = userAccount;
        this.student = (Student) userAccount.getProfile();
        
        // --- DATA INTEGRATION FIX ---
        loadCompletedEnrollmentsFromCentralStore(); 
        
        // Make fields read-only
        txtName.setEditable(false);
        txtUniversityID.setEditable(false);
        txtProgram.setEditable(false);
        txtTotalCreditEarned.setEditable(false);
        txtMandatory.setEditable(false);
        jTextField6.setEditable(false);

        populateStudentInfo();
        populateCoursesCompletedTable();
        calculateCreditSummary();
        checkGraduationEligibility();
    }
    
    /**
     * FIX: Loads completed/graded enrollments from the central ConfigureJTable.
     */
    private void loadCompletedEnrollmentsFromCentralStore() {
        int studentUNID = student.getPerson().getUNID();
        
        // Filter the central list for enrollments belonging to this student AND marked completed/graded
        completedEnrollments = ConfigureJTable.enrollmentList.stream()
            .filter(e -> e.getStudent().getPerson().getUNID() == studentUNID)
            .filter(Enrollment::isCompleted) // Assuming isCompleted checks for a grade != "N/A"
            .collect(Collectors.toList());
    }
    
    /**
     * Populate student information
     */
    private void populateStudentInfo() {
        txtName.setText(student.getPerson().getName());
        txtUniversityID.setText(String.valueOf(student.getPerson().getUNID()));
        txtProgram.setText("MSIS - " + student.getDepartment().toString()); // Assuming MSIS
    }
    
    /**
     * Populate courses completed table
     */
    private void populateCoursesCompletedTable() {
        DefaultTableModel model = (DefaultTableModel) tblCoursesCompleted.getModel();
        model.setRowCount(0);

        for (Enrollment e : completedEnrollments) {
            CourseOffering co = e.getCourseOffering();
            Object[] row = new Object[5];
            row[0] = co.getCourse().getCourseID();
            row[1] = co.getCourse().getName();
            row[2] = co.getCourse().getCredits();
            row[3] = co.getSemester();
            row[4] = e.getGrade() != null ? e.getGrade() : "In Progress";
            model.addRow(row);
        }
    }
    
    /**
     * Calculate credit summary and check requirements
     */
    private void calculateCreditSummary() {
        int totalEarned = 0;
        boolean mandatoryCompleted = false;
        int electiveCredits = 0;

        for (Enrollment e : completedEnrollments) {
            int credits = e.getCourseOffering().getCourse().getCredits();
            totalEarned += credits;

            // Check mandatory course (INFO 5100 - 4 credits)
            if (e.getCourseOffering().getCourse().getCourseID().equalsIgnoreCase(MANDATORY_COURSE_ID)) {
                mandatoryCompleted = true;
                // Exclude the mandatory course credits from the elective total for accurate breakdown
            } else {
                electiveCredits += credits;
            }
        }

        // Populate summary fields
        txtTotalCreditEarned.setText(String.valueOf(totalEarned));
        txtMandatory.setText(mandatoryCompleted ? "Completed ✓" : "Pending ✗");
        // Total Credits Exceeding Core (Total Earned - Mandatory Credits)
        jTextField6.setText(String.valueOf(Math.max(0, totalEarned - 4))); 

        // Update label with progress
        int remaining = Math.max(0, TOTAL_REQUIRED_CREDITS - totalEarned);
        lblTotalCreditsRequired.setText("Total Credits Required: " + TOTAL_REQUIRED_CREDITS + 
                                         " (Remaining: " + remaining + ")");
        
        // Update student's credits completed (CRUCIAL for load limit checks)
        student.setCreditsCompleted(totalEarned); 
    }
    
    /**
     * Check graduation eligibility and display message (Assignment Requirement)
     */
    private void checkGraduationEligibility() {
        int totalEarned = Integer.parseInt(txtTotalCreditEarned.getText());
        boolean mandatoryCompleted = txtMandatory.getText().contains("✓");
        
        if (totalEarned >= TOTAL_REQUIRED_CREDITS && mandatoryCompleted) {
            JOptionPane.showMessageDialog(this, 
                "🎓 Congratulations! You are eligible to graduate!\n\n" +
                "✓ Completed " + totalEarned + "/" + TOTAL_REQUIRED_CREDITS + " credits\n" +
                "✓ Core course INFO 5100 completed\n\n" +
                "Please contact the registrar's office to apply for graduation.", 
                "Graduation Eligible", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("You are NOT yet eligible to graduate.\n\n");
            
            if (totalEarned < TOTAL_REQUIRED_CREDITS) {
                int needed = TOTAL_REQUIRED_CREDITS - totalEarned;
                message.append("✗ Need ").append(needed).append(" more credits\n");
            } else {
                message.append("✓ Credit requirement met (").append(totalEarned).append("/32)\n");
            }
            
            if (!mandatoryCompleted) {
                message.append("✗ Core course INFO 5100 (4 credits) NOT completed\n");
            } else {
                message.append("✓ Core course INFO 5100 completed\n");
            }
            
            JOptionPane.showMessageDialog(this, 
                message.toString(), 
                "Graduation Requirements", 
                JOptionPane.WARNING_MESSAGE);
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

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblUniversityId = new javax.swing.JLabel();
        lblProgram = new javax.swing.JLabel();
        txtTotalCreditEarned = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblTotalCreditEarned = new javax.swing.JLabel();
        lblMandatoryCourse = new javax.swing.JLabel();
        txtMandatory = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        lblTotalCreditsRequired = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCoursesCompleted = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        txtUniversityID = new javax.swing.JTextField();
        txtProgram = new javax.swing.JTextField();

        jFormattedTextField1.setText("jFormattedTextField1");

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Graduation Audit");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Student Info");

        lblName.setText("Name");

        lblUniversityId.setText("University ID");

        lblProgram.setText("Program");

        txtTotalCreditEarned.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCreditEarnedActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Credit Summary");

        lblTotalCreditEarned.setText("Total Credit Earned");

        lblMandatoryCourse.setText("mandatory INFO 5100 (4 credits) course");

        txtMandatory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMandatoryActionPerformed(evt);
            }
        });

        jLabel9.setText("Total Credits Exceeding the core course");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        lblTotalCreditsRequired.setText("Total Credits Required: 32Credit hours");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Note:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");

        tblCoursesCompleted.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Course ID", "Course Name", "Credit Hours", "Term/Year Completed", "Grade"
            }
        ));
        jScrollPane1.setViewportView(tblCoursesCompleted);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtUniversityID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUniversityIDActionPerformed(evt);
            }
        });

        txtProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProgramActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblName)
                                        .addComponent(lblUniversityId)
                                        .addComponent(lblProgram))
                                    .addComponent(jLabel2))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                    .addComponent(txtUniversityID)
                                    .addComponent(txtProgram))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalCreditsRequired))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMandatoryCourse)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(jLabel6))
                                .addComponent(lblTotalCreditEarned))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMandatory, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalCreditEarned, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUniversityID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUniversityId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProgram))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCreditEarned)
                    .addComponent(txtTotalCreditEarned, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMandatoryCourse)
                    .addComponent(txtMandatory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCreditsRequired)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTotalCreditEarnedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCreditEarnedActionPerformed
        // TODO add your handling code here:
        calculateCreditSummary();
    }//GEN-LAST:event_txtTotalCreditEarnedActionPerformed

    private void txtMandatoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMandatoryActionPerformed
        // TODO add your handling code here:
         txtMandatory.setEditable(false);
    txtMandatory.setToolTipText("Auto-filled: Completion status of INFO 5100");
    }//GEN-LAST:event_txtMandatoryActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
        jTextField6.setEditable(false);
    jTextField6.setToolTipText("Auto-calculated credits exceeding the core course");
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
         txtName.setEditable(false);
    txtName.setToolTipText("Student name fetched from profile");
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtUniversityIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUniversityIDActionPerformed
        // TODO add your handling code here:
         txtUniversityID.setEditable(false);
    txtUniversityID.setToolTipText("Student university ID (read-only)");
    }//GEN-LAST:event_txtUniversityIDActionPerformed

    private void txtProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProgramActionPerformed
        // TODO add your handling code here:
        txtProgram.setEditable(false);
    txtProgram.setToolTipText("Program or department (read-only)");
    }//GEN-LAST:event_txtProgramActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel lblMandatoryCourse;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblProgram;
    private javax.swing.JLabel lblTotalCreditEarned;
    private javax.swing.JLabel lblTotalCreditsRequired;
    private javax.swing.JLabel lblUniversityId;
    private javax.swing.JTable tblCoursesCompleted;
    private javax.swing.JTextField txtMandatory;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtProgram;
    private javax.swing.JTextField txtTotalCreditEarned;
    private javax.swing.JTextField txtUniversityID;
    // End of variables declaration//GEN-END:variables
}
