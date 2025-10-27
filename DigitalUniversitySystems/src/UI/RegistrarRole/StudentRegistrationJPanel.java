/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.RegistrarRole;

import Model.Student;
import Model.Enrollment;
import Model.CourseOffering;
import Model.EnrollmentService; 
import Model.CourseService;    // New service import for finding available courses
import Model.accesscontrol.ConfigureJTable; // For getting semester list
import Model.User.UserAccount;
import Model.User.UserAccountDirectory;
import UI.RegistrarRole.StudentSearchDialog; // Required for search button action
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author jayan
 */
public class StudentRegistrationJPanel extends javax.swing.JPanel {

    private Student currentStudent; 
    private final UserAccountDirectory accountDirectory;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService; // New CourseService instance
    
    // Stores the currently displayed enrollments (used only if table shows status, not offerings)
    private List<Enrollment> displayedEnrollments; 
    
    // Store all current offerings for the selected semester
    private List<CourseOffering> currentOfferings; 
    
    public StudentRegistrationJPanel(UserAccountDirectory directory) {
        initComponents();
        this.accountDirectory = directory;
        this.enrollmentService = new EnrollmentService();
        this.courseService = new CourseService(); // Initialize CourseService
        this.displayedEnrollments = new ArrayList<>();
        this.currentOfferings = new ArrayList<>();
        
        initializeSemesterDropdown(); // Call new method
        resetStudentDetails();
        
        // Attaching the JTable listener for selection events
        tblStudentRegistration.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblStudentRegistration.getSelectedRow() != -1) {
                updateEnrollDropButtons();
            }
        });
    }
    
    private void initializeSemesterDropdown() {
        jComboBoxSemester.removeAllItems();
        // NOTE: In a real app, this list comes from CourseOffering records.
        jComboBoxSemester.addItem("Fall 2025");
        jComboBoxSemester.addItem("Spring 2026");
        jComboBoxSemester.setSelectedIndex(0);
    }
    
    private void resetStudentDetails() {
        fieldName.setText("//Name");
        fieldID.setText("//ID");
        fieldCredits.setText("//Credits");
        currentStudent = null;
        DefaultTableModel model = (DefaultTableModel) tblStudentRegistration.getModel();
        model.setRowCount(0);
        btnEnroll.setEnabled(false);
        btnDrop.setEnabled(false);
        this.displayedEnrollments.clear();
        this.currentOfferings.clear();
    }
    
    private void displayStudent(Student student) {
        this.currentStudent = student;
        fieldName.setText(student.getPerson().getName());
        fieldID.setText(String.valueOf(student.getPerson().getUNID()));
        fieldCredits.setText(String.valueOf(student.getCreditsCompleted()));
        
        // Display student's current enrollments by default when profile is loaded
        populateEnrollmentTable(student); 
    }
    
    /**
     * Populates the table with the student's EXISTING ENROLLMENTS (Active/Dropped).
     */
    private void populateEnrollmentTable(Student student) {
        DefaultTableModel model = (DefaultTableModel) tblStudentRegistration.getModel();
        model.setRowCount(0);
        
        this.displayedEnrollments = enrollmentService.getEnrollmentsByStudent(student);

        // Change table headers back to Enrollment Status for clarity
        model.setColumnIdentifiers(new Object[]{"Course ID", "Course Name", "Enrollment Status"});

        for (Enrollment enrollment : displayedEnrollments) {
            CourseOffering offer = enrollment.getCourseOffering();
            model.addRow(new Object[]{
                offer.getCourse().getCourseID(), 
                offer.getCourse().getName(), 
                enrollment.isActive() ? "Enrolled" : "Dropped"
            });
        }
        this.currentOfferings.clear(); // Clear offerings list when showing status
        updateEnrollDropButtons();
    }
    
    /**
     * CRITICAL FIX: Populates the table with ALL AVAILABLE OFFERINGS for the selected semester.
     * This is triggered by the 'View' button.
     */
    private void populateAvailableCourses(String semester) {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(this, "Please select a student first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblStudentRegistration.getModel();
        model.setRowCount(0);
        
        // Change table headers to reflect Course Availability
        model.setColumnIdentifiers(new Object[]{"Course ID", "Course Name", "Availability"});

        // 1. Get ALL offerings for the semester
        List<CourseOffering> allOfferings = courseService.getOfferingsBySemester(semester);
        
        // 2. Get student's existing enrollments for quick lookup
        List<String> enrolledIDs = enrollmentService.getEnrollmentsByStudent(currentStudent).stream()
            .filter(Enrollment::isActive)
            .map(e -> e.getCourseOffering().getCourse().getCourseID())
            .collect(Collectors.toList());

        // 3. Populate table with Availability Status
        this.currentOfferings = new ArrayList<>(); // Store available offerings
        
        for (CourseOffering offer : allOfferings) {
            String courseID = offer.getCourse().getCourseID();
            String status;

            if (enrolledIDs.contains(courseID)) {
                status = "ENROLLED";
            } else if (offer.getEnrolledCount() >= offer.getCapacity()) {
                status = "FULL";
            } else {
                status = "AVAILABLE";
                currentOfferings.add(offer); // Only add truly available courses to the local list
            }

            model.addRow(new Object[]{
                courseID, 
                offer.getCourse().getName(), 
                status
            });
        }
        this.displayedEnrollments.clear(); // Clear old enrollment list
        updateEnrollDropButtons();
    }
    
    private void updateEnrollDropButtons() {
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0) {
            btnEnroll.setEnabled(false);
            btnDrop.setEnabled(false);
            return;
        }

        String status = (String) tblStudentRegistration.getValueAt(selectedRow, 2);
        
        // If showing AVAILABLE COURSES: Enable Enroll if status is AVAILABLE
        if (!currentOfferings.isEmpty()) {
            btnEnroll.setEnabled(status.equals("AVAILABLE"));
            btnDrop.setEnabled(false);
        } 
        // If showing STUDENT ENROLLMENTS: Enable Drop if status is ENROLLED
        else if (!displayedEnrollments.isEmpty()) {
            btnEnroll.setEnabled(false);
            btnDrop.setEnabled(status.equals("Enrolled"));
        } else {
            btnEnroll.setEnabled(false);
            btnDrop.setEnabled(false);
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

        btnSearchStudent = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudentRegistration = new javax.swing.JTable();
        lblName = new javax.swing.JLabel();
        fieldName = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        fieldID = new javax.swing.JLabel();
        lblCreditsEnrolled = new javax.swing.JLabel();
        fieldCredits = new javax.swing.JLabel();
        btnEnroll = new javax.swing.JButton();
        btnDrop = new javax.swing.JButton();
        jComboBoxSemester = new javax.swing.JComboBox<>();
        btnView = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setPreferredSize(new java.awt.Dimension(600, 465));

        btnSearchStudent.setBackground(new java.awt.Color(255, 204, 204));
        btnSearchStudent.setText("Search/Select Student");
        btnSearchStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchStudentActionPerformed(evt);
            }
        });

        tblStudentRegistration.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course ID", "Course Name", "Enrollment Status"
            }
        ));
        jScrollPane1.setViewportView(tblStudentRegistration);

        lblName.setText("Name: ");

        fieldName.setText("//Name");

        lblID.setText("ID: ");

        fieldID.setText("//ID");

        lblCreditsEnrolled.setText("Credits Enrolled:");

        fieldCredits.setText("//Credits");

        btnEnroll.setBackground(new java.awt.Color(255, 204, 204));
        btnEnroll.setText("Enroll");
        btnEnroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnrollActionPerformed(evt);
            }
        });

        btnDrop.setBackground(new java.awt.Color(255, 204, 204));
        btnDrop.setText("Drop");
        btnDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropActionPerformed(evt);
            }
        });

        jComboBoxSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSemesterActionPerformed(evt);
            }
        });

        btnView.setBackground(new java.awt.Color(255, 204, 204));
        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldName)
                                .addGap(54, 54, 54)
                                .addComponent(lblID)
                                .addGap(18, 18, 18)
                                .addComponent(fieldID)
                                .addGap(79, 79, 79)
                                .addComponent(lblCreditsEnrolled)
                                .addGap(18, 18, 18)
                                .addComponent(fieldCredits)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(btnEnroll)
                                .addGap(82, 82, 82)
                                .addComponent(btnDrop))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnSearchStudent)
                                .addGap(84, 84, 84)
                                .addComponent(jComboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnView)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchStudent)
                    .addComponent(jComboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(fieldName)
                    .addComponent(lblID)
                    .addComponent(fieldID)
                    .addComponent(lblCreditsEnrolled)
                    .addComponent(fieldCredits))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnroll)
                    .addComponent(btnDrop))
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchStudentActionPerformed
        // TODO add your handling code here:
        // NOTE: Passing 'null' for the parent frame is a simple way to create a JDialog; 
    // you can pass this JDialog's parent JFrame (MainJFrame) if needed for focus.
    StudentSearchDialog dialog = new StudentSearchDialog(null, true, accountDirectory);
    dialog.setVisible(true);
    
    // 2. Retrieve the Student object selected in the dialog
    Student selectedStudent = dialog.getSelectedStudent();
    
    if (selectedStudent != null) {
        // SUCCESS: The dialog successfully returned a validated Student object.
        displayStudent(selectedStudent);
    } else if (currentStudent == null) {
        // If the user closed the dialog without selecting a student, reset the view.
        resetStudentDetails();
    }
    }//GEN-LAST:event_btnSearchStudentActionPerformed

    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0 || !btnEnroll.isEnabled()) return;
        
        // Enrollment can only happen if currentOfferings list is populated
        if (currentOfferings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please click 'View' to see available courses for enrollment.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the selected Course ID from the table
        String courseID = (String) tblStudentRegistration.getValueAt(selectedRow, 0);
        String semester = (String) jComboBoxSemester.getSelectedItem();
        
        // Find the full offering object that the Registrar wants to enroll into
        CourseOffering offeringToEnroll = currentOfferings.stream()
            .filter(o -> o.getCourse().getCourseID().equals(courseID) && o.getSemester().equals(semester))
            .findFirst().orElse(null);
        
        if (offeringToEnroll != null) {
            // Check load limit before performing Admin-side enrollment
            double currentCredits = enrollmentService.getCurrentActiveCredits(currentStudent, semester);
            if (currentCredits + offeringToEnroll.getCourse().getCredits() > 8) {
                JOptionPane.showMessageDialog(this, "Enrollment Failed: Student would exceed 8 credit hour limit.", "Load Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 2. Enroll the student (Admin-Side is implied true here)
            boolean success = enrollmentService.enrollStudent(currentStudent, offeringToEnroll, true);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Admin-side ENROLLMENT successful for " + courseID, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Enrollment Failed (Course Full).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
             JOptionPane.showMessageDialog(this, "Offering object not found in current list. Please search again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        populateEnrollmentTable(currentStudent); // Switch back to showing student's status
    }//GEN-LAST:event_btnEnrollActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0 || !btnDrop.isEnabled()) return;
        
        // Drop can only happen if displayedEnrollments list is populated
        if (displayedEnrollments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please view student's current enrollment status first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Enrollment enrollmentToDrop = displayedEnrollments.get(selectedRow); 
        
        int confirm = JOptionPane.showConfirmDialog(this, "Confirm Admin-side DROP for " + enrollmentToDrop.getCourseOffering().getCourse().getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = enrollmentService.dropStudent(enrollmentToDrop);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Admin-side DROP successful. Tuition Refund initiated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Drop failed (enrollment not active).", "Error", JOptionPane.ERROR_MESSAGE);
            }
            populateEnrollmentTable(currentStudent); // Refresh data
        }
    }//GEN-LAST:event_btnDropActionPerformed

    private void jComboBoxSemesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSemesterActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxSemesterActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        String semester = (String) jComboBoxSemester.getSelectedItem();
        if (semester == null) {
             JOptionPane.showMessageDialog(this, "Please select a semester.", "Warning", JOptionPane.WARNING_MESSAGE);
             return;
        }
        populateAvailableCourses(semester);
    }//GEN-LAST:event_btnViewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnEnroll;
    private javax.swing.JButton btnSearchStudent;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel fieldCredits;
    private javax.swing.JLabel fieldID;
    private javax.swing.JLabel fieldName;
    private javax.swing.JComboBox<String> jComboBoxSemester;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreditsEnrolled;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblStudentRegistration;
    // End of variables declaration//GEN-END:variables
}
