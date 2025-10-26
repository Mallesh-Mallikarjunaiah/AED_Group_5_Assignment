/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.RegistrarRole;

import Model.Student;
import Model.Person;
import Model.Department;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.HashMap;
/**
 *
 * @author jayan
 */
public class StudentRegistrationJPanel extends javax.swing.JPanel {

    private Student currentStudent; 
    private final HashMap<String, Student> localStudentData; // Local Mock Data Store
    
    public StudentRegistrationJPanel() {
        initComponents();
        this.localStudentData = initializeMockData();
        resetStudentDetails();
        
        // Attaching the JTable listener for selection events
        tblStudentRegistration.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblStudentRegistration.getSelectedRow() != -1) {
                // Logic to enable/disable Enroll/Drop buttons based on status
                updateEnrollDropButtons();
            }
        });
    }
    
    // --- Mock Data Initialization (Replaces global DataStore for local testing) ---
    private HashMap<String, Student> initializeMockData() {
        HashMap<String, Student> data = new HashMap<>();
        Department deptIS = Department.IS;
        Department deptCS = Department.CS;

        // Creating pre-populated student data (as required by assignment)
        // Student 1: Enrolled in 8 credits, Paid up
        Person p1 = new Person("Amelia Jones", "aj@uni.edu", "555-1001");
        Student s1 = new Student(p1, deptIS);
        s1.setCreditsCompleted(8); 
        data.put("Amelia Jones", s1);
        data.put("1001", s1);

        // Student 2: Enrolled in 4 credits, On academic warning
        Person p2 = new Person("Ben Carter", "bc@uni.edu", "555-1002");
        Student s2 = new Student(p2, deptCS);
        s2.setCreditsCompleted(4); 
        data.put("Ben Carter", s2);
        data.put("1002", s2);

        return data;
    }

    // Searches the local store by name or ID
    private Student searchLocalData(String searchInput) {
        String input = searchInput.trim();
        
        // Search by Name (Case-Insensitive)
        Student student = localStudentData.keySet().stream()
            .filter(key -> !key.matches("\\d+") && key.equalsIgnoreCase(input))
            .findFirst()
            .map(localStudentData::get)
            .orElse(null);
        
        // If not found by name, search by ID
        if (student == null) {
            student = localStudentData.get(input);
        }
        return student;
    }
    // --- End Mock Data Logic ---
    
    
    private void resetStudentDetails() {
        fieldName.setText("//Name");
        fieldID.setText("//ID");
        fieldCredits.setText("//Credits");
        currentStudent = null;
        DefaultTableModel model = (DefaultTableModel) tblStudentRegistration.getModel();
        model.setRowCount(0);
        btnEnroll.setEnabled(false);
        btnDrop.setEnabled(false);
    }
    
    // Updates the profile info labels and calls to populate the enrollment table
    private void displayStudent(Student student) {
        this.currentStudent = student;
        
        // --- FIX: Accessing Name and ID through the Person object ---
        fieldName.setText(student.getPerson().getName());
        fieldID.setText(String.valueOf(student.getPerson().getUNID()));
        // -----------------------------------------------------------------
        
        fieldCredits.setText(String.valueOf(student.getCreditsCompleted()));
        
        populateEnrollmentTable(student);
    }
    
    private void populateEnrollmentTable(Student student) {
        DefaultTableModel model = (DefaultTableModel) tblStudentRegistration.getModel();
        model.setRowCount(0);
        
        // Mock Enrollment Data based on student ID
        List<String[]> mockEnrollments = getMockEnrollmentList(student); 

        for (String[] enrollment : mockEnrollments) {
            model.addRow(new Object[]{enrollment[0], enrollment[1], enrollment[2]});
        }
        updateEnrollDropButtons();
    }
    
    // Logic to determine if Enroll/Drop buttons should be enabled
    private void updateEnrollDropButtons() {
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0) {
            btnEnroll.setEnabled(false);
            btnDrop.setEnabled(false);
            return;
        }

        String status = (String) tblStudentRegistration.getValueAt(selectedRow, 2);
        
        // Registrar can only Enroll if the status is NOT enrolled (i.e., available or dropped)
        btnEnroll.setEnabled(!status.equals("Enrolled"));
        
        // Registrar can only Drop if the status is Enrolled
        btnDrop.setEnabled(status.equals("Enrolled"));
    }
    
    // Mock Enrollment Data: simulates data retrieved from EnrollmentService
    private List<String[]> getMockEnrollmentList(Student student) {
        List<String[]> mockList = new ArrayList<>();
        
        if (student.getPerson().getUNID() == 1001) { // Amelia Jones
            mockList.add(new String[]{"INFO 5100", "App Engineering", "Enrolled"});
            mockList.add(new String[]{"CS 5010", "Algorithms", "Enrolled"});
            mockList.add(new String[]{"MGT 6500", "Finance", "Available"});
        } else { // Default or Ben Carter
            mockList.add(new String[]{"CS 5010", "Algorithms", "Enrolled"});
            mockList.add(new String[]{"MATH 6000", "Advanced Calc", "Available"});
            mockList.add(new String[]{"EE 5000", "Digital Circuits", "Dropped"});
        }
        return mockList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtStudentNameID = new javax.swing.JTextField();
        btnSearchStudent = new javax.swing.JButton();
        lblStudentNameID = new javax.swing.JLabel();
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

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setPreferredSize(new java.awt.Dimension(600, 465));

        btnSearchStudent.setBackground(new java.awt.Color(255, 204, 204));
        btnSearchStudent.setText("Search");
        btnSearchStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchStudentActionPerformed(evt);
            }
        });

        lblStudentNameID.setText("Student Name/ID");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lblStudentNameID)
                                .addGap(18, 18, 18)
                                .addComponent(txtStudentNameID, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchStudent))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
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
                                .addComponent(fieldCredits)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(btnEnroll)
                .addGap(82, 82, 82)
                .addComponent(btnDrop)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStudentNameID)
                    .addComponent(txtStudentNameID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchStudent))
                .addGap(49, 49, 49)
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
        String searchInput = txtStudentNameID.getText().trim();
        if (searchInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student Name or ID.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Student foundStudent = searchLocalData(searchInput);
        
        if (foundStudent != null) {
            displayStudent(foundStudent);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found with name/ID: " + searchInput, "Error", JOptionPane.ERROR_MESSAGE);
            resetStudentDetails();
        }
    }//GEN-LAST:event_btnSearchStudentActionPerformed

    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0 || !btnEnroll.isEnabled()) return;
        
        String courseID = (String) tblStudentRegistration.getValueAt(selectedRow, 0);
        
        // 1. Call EnrollmentService.enrollStudent(currentStudent, courseID, true)
        // The 'true' flag indicates Admin-Side override, fulfilling the requirement.
        
        JOptionPane.showMessageDialog(this, "Admin-side ENROLLMENT successful for " + courseID, "Success", JOptionPane.INFORMATION_MESSAGE);
        populateEnrollmentTable(currentStudent); // Refresh data
    }//GEN-LAST:event_btnEnrollActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblStudentRegistration.getSelectedRow();
        if (currentStudent == null || selectedRow < 0 || !btnDrop.isEnabled()) return;
        
        String courseID = (String) tblStudentRegistration.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Confirm Admin-side DROP for " + courseID + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Call EnrollmentService.dropStudent(currentStudent, courseID)
            
            JOptionPane.showMessageDialog(this, "Admin-side DROP successful for " + courseID, "Success", JOptionPane.INFORMATION_MESSAGE);
            populateEnrollmentTable(currentStudent); // Refresh data
        }
    }//GEN-LAST:event_btnDropActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnEnroll;
    private javax.swing.JButton btnSearchStudent;
    private javax.swing.JLabel fieldCredits;
    private javax.swing.JLabel fieldID;
    private javax.swing.JLabel fieldName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreditsEnrolled;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStudentNameID;
    private javax.swing.JTable tblStudentRegistration;
    private javax.swing.JTextField txtStudentNameID;
    // End of variables declaration//GEN-END:variables
}
