/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.StudentRole;
import Model.CourseOffering;
import Model.Enrollment;
import Model.Student;
import Model.Faculty;
import Model.User.UserAccount;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MALLESH
 */
public class CourseRegistrationJPanel extends javax.swing.JPanel {
    private JPanel mainWorkArea;
    private UserAccount userAccount;
    private Student student;
    private ArrayList<CourseOffering> availableOfferings;
    private ArrayList<Enrollment> enrollmentList;
    /**
     * Creates new form CourseRegistrationJPanel
     */
    public CourseRegistrationJPanel(JPanel mainWorkArea, UserAccount userAccount,
            ArrayList<CourseOffering> offerings, ArrayList<Enrollment> enrollments) {
        initComponents();
        this.mainWorkArea = mainWorkArea;
        this.userAccount = userAccount;
        this.student = (Student) userAccount.getProfile();
        this.availableOfferings = offerings;
        this.enrollmentList = enrollments;
        filterComboBox.removeAllItems();    
        filterComboBox.addItem("Course ID");
        filterComboBox.addItem("Course Name");
        filterComboBox.addItem("Faculty");
        filterComboBox.addItem("Department");
        TxtCreditsCurrentlyRegistered.setEditable(false);
        TxtCreditsCurrentlyRegistered.setToolTipText("Auto-calculated from your active enrollments");


        populateSemesterDropdown();
        populateAvailableCoursesTable();
        populateMyCoursesTable();
        updateCreditSummary();
    }
    
    
         private void populateSemesterDropdown() {
        SemesterjComboBox.removeAllItems();
        ArrayList<String> semesters = new ArrayList<>();
        for (CourseOffering co : availableOfferings) {
            if (!semesters.contains(co.getSemester())) {
                semesters.add(co.getSemester());
            }
        }
        for (String s : semesters) {
            SemesterjComboBox.addItem(s);
        }
    }
          /** Populates Course Offerings Table */
    private void populateAvailableCoursesTable() {
        DefaultTableModel model = (DefaultTableModel) courseOfferingsJTable.getModel();
        model.setRowCount(0);

        String selectedSemester = (String) SemesterjComboBox.getSelectedItem();

        for (CourseOffering co : availableOfferings) {
            if (selectedSemester == null || co.getSemester().equals(selectedSemester)) {
                Object[] row = new Object[8];
                row[0] = co.getCourse().getCourseID();
                row[1] = co.getCourse().getName();
                row[2] = (co.getFaculty() != null) ? co.getFaculty().getPerson().getName() : "TBA";
                row[3] = (co.getFaculty() != null) ? co.getFaculty().getDepartment().toString() : "-";
                row[4] = co.getCourse().getCredits();
                row[5] = co.getSchedule();
                row[6] = co.getEnrolledCount() + "/" + co.getCapacity();
                row[7] = (isAlreadyEnrolled(co)) ? "Enrolled" : "Available";
                model.addRow(row);
            }
        }
    }
       /** Populates Current Enrollment Table */
    private void populateMyCoursesTable() {
        DefaultTableModel model = (DefaultTableModel) currentEnrollmentJTable.getModel();
        model.setRowCount(0);

        for (Enrollment e : enrollmentList) {
            if (e.getStudent().equals(student) && e.isActive()) {
                Object[] row = new Object[5];
                row[0] = e.getCourseOffering().getCourse().getCourseID();
                row[1] = e.getCourseOffering().getCourse().getName();
                row[2] = e.getCourseOffering().getCourse().getCredits();
                row[3] = e.getCourseOffering().getFaculty() != null ?
                        e.getCourseOffering().getFaculty().getPerson().getName() : "TBA";
                row[4] = e.getCourseOffering().getSemester();
                model.addRow(row);
            }
        }
    }

    /** Checks if student already enrolled in this offering */
    private boolean isAlreadyEnrolled(CourseOffering offering) {
        for (Enrollment e : enrollmentList) {
            if (e.getStudent().equals(student) &&
                e.getCourseOffering().equals(offering) &&
                e.isActive()) {
                return true;
            }
        }
        return false;
    }
        private void updateCreditSummary() {
        int totalCredits = 0;
        for (Enrollment e : enrollmentList) {
            if (e.getStudent().equals(student) && e.isActive()) {
                totalCredits += e.getCourseOffering().getCourse().getCredits();
            }
        }
        TxtCreditsCurrentlyRegistered.setText(String.valueOf(totalCredits));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        SemesterjComboBox = new javax.swing.JComboBox<>();
        lblSelectSemester = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSearchHere = new javax.swing.JTextField();
        filterComboBox = new javax.swing.JComboBox<>();
        lblFilter = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        courseOfferingsJTable = new javax.swing.JTable();
        btnEnroll = new javax.swing.JButton();
        btnDrop = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentEnrollmentJTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        TxtCreditsCurrentlyRegistered = new javax.swing.JTextField();
        lblSearchHere = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Course Registration Panel");

        SemesterjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SemesterjComboBoxActionPerformed(evt);
            }
        });

        lblSelectSemester.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSelectSemester.setText("Select Semester");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Note:");

        lblNote.setText("Maximum Allowed Credit Hours: 8");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("*");

        txtSearchHere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchHereActionPerformed(evt);
            }
        });

        filterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterComboBoxActionPerformed(evt);
            }
        });

        lblFilter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFilter.setText("Filter");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        courseOfferingsJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Course ID", "Course Name", "Faculty Name", "Department", "Credits", "Schedule", "Capacity", "Status"
            }
        ));
        jScrollPane1.setViewportView(courseOfferingsJTable);

        btnEnroll.setText("Enroll");
        btnEnroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnrollActionPerformed(evt);
            }
        });

        btnDrop.setText("Drop");
        btnDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropActionPerformed(evt);
            }
        });

        currentEnrollmentJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Course ID", "Course Name", "Credits", "Faculty", "Enrollment Date"
            }
        ));
        jScrollPane2.setViewportView(currentEnrollmentJTable);

        jLabel7.setText("Total credits currently registered");

        TxtCreditsCurrentlyRegistered.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCreditsCurrentlyRegisteredActionPerformed(evt);
            }
        });

        lblSearchHere.setText("Search here");

        jLabel4.setText("Course Offerings Table");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(lblSelectSemester)
                        .addGap(13, 13, 13)
                        .addComponent(SemesterjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNote))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtCreditsCurrentlyRegistered, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                                .addComponent(jScrollPane2))
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(lblSearchHere)
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchHere, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReset)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblFilter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(btnEnroll)
                        .addGap(32, 32, 32)
                        .addComponent(btnDrop)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SemesterjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelectSemester)
                    .addComponent(jLabel3)
                    .addComponent(lblNote)
                    .addComponent(jLabel5))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchHere, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilter)
                    .addComponent(lblSearchHere))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtCreditsCurrentlyRegistered, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnroll)
                    .addComponent(btnDrop))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchHereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchHereActionPerformed
        // TODO add your handling code here:
        String keyword = txtSearchHere.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) courseOfferingsJTable.getModel();
        model.setRowCount(0);

        for (CourseOffering co : availableOfferings) {
            if (co.getCourse().getCourseID().toLowerCase().contains(keyword) ||
                co.getCourse().getName().toLowerCase().contains(keyword)) {
                Object[] row = new Object[8];
                row[0] = co.getCourse().getCourseID();
                row[1] = co.getCourse().getName();
                row[2] = (co.getFaculty() != null) ? co.getFaculty().getPerson().getName() : "TBA";
                row[3] = (co.getFaculty() != null) ? co.getFaculty().getDepartment().toString() : "-";
                row[4] = co.getCourse().getCredits();
                row[5] = co.getSchedule();
                row[6] = co.getEnrolledCount() + "/" + co.getCapacity();
                row[7] = (isAlreadyEnrolled(co)) ? "Enrolled" : "Available";
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_txtSearchHereActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        txtSearchHere.setText("");
        populateAvailableCoursesTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollActionPerformed
        // TODO add your handling code here:
        int selectedRow = courseOfferingsJTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course to enroll.");
            return;
        }

        CourseOffering selectedOffering = availableOfferings.get(selectedRow);

        if (isAlreadyEnrolled(selectedOffering)) {
            JOptionPane.showMessageDialog(this, "Already enrolled in this course.");
            return;
        }

        if (selectedOffering.getEnrolledCount() >= selectedOffering.getCapacity()) {
            JOptionPane.showMessageDialog(this, "Course is full. Cannot enroll.");
            return;
        }

        // Credit limit check
        int currentCredits = Integer.parseInt(TxtCreditsCurrentlyRegistered.getText());
        if (currentCredits + selectedOffering.getCourse().getCredits() > 8) {
            JOptionPane.showMessageDialog(this, "Cannot exceed 8 credit hours.");
            return;
        }

        // Proceed with enrollment
        Enrollment newEnrollment = new Enrollment(student, selectedOffering);
        enrollmentList.add(newEnrollment);
        selectedOffering.incrementEnrolledCount();

        JOptionPane.showMessageDialog(this, "Successfully enrolled in " +
                selectedOffering.getCourse().getName() + "!");
        populateAvailableCoursesTable();
        populateMyCoursesTable();
        updateCreditSummary();
        
    }//GEN-LAST:event_btnEnrollActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
        // TODO add your handling code here:
         int selectedRow = currentEnrollmentJTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a course to drop.");
            return;
        }

        String courseId = currentEnrollmentJTable.getValueAt(selectedRow, 0).toString();
        Enrollment found = null;

        for (Enrollment e : enrollmentList) {
            if (e.getStudent().equals(student)
                    && e.getCourseOffering().getCourse().getCourseID().equals(courseId)
                    && e.isActive()) {
                found = e;
                break;
            }
        }

        if (found != null) {
            found.setActive(false);
            found.getCourseOffering().decrementEnrolledCount();
            JOptionPane.showMessageDialog(this, "Dropped successfully.");
            populateAvailableCoursesTable();
            populateMyCoursesTable();
            updateCreditSummary();
        } else {
            JOptionPane.showMessageDialog(this, "Error: Enrollment not found.");
        }
        
    }//GEN-LAST:event_btnDropActionPerformed

    private void TxtCreditsCurrentlyRegisteredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCreditsCurrentlyRegisteredActionPerformed
        // TODO add your handling code here:
         updateCreditSummary();  
    }//GEN-LAST:event_TxtCreditsCurrentlyRegisteredActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
         String keyword = txtSearchHere.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) courseOfferingsJTable.getModel();
        model.setRowCount(0);

        for (CourseOffering co : availableOfferings) {
            if (co.getCourse().getCourseID().toLowerCase().contains(keyword) ||
                co.getCourse().getName().toLowerCase().contains(keyword)) {
                Object[] row = new Object[8];
                row[0] = co.getCourse().getCourseID();
                row[1] = co.getCourse().getName();
                row[2] = (co.getFaculty() != null) ? co.getFaculty().getPerson().getName() : "TBA";
                row[3] = (co.getFaculty() != null) ? co.getFaculty().getDepartment().toString() : "-";
                row[4] = co.getCourse().getCredits();
                row[5] = co.getSchedule();
                row[6] = co.getEnrolledCount() + "/" + co.getCapacity();
                row[7] = (isAlreadyEnrolled(co)) ? "Enrolled" : "Available";
                model.addRow(row);
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void filterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterComboBoxActionPerformed
        // TODO add your handling code here:
      String selectedFilter = (String) filterComboBox.getSelectedItem();

    // Clear the search box hint based on the selected filter
    if (selectedFilter != null) {
        switch (selectedFilter) {
            case "Course ID":
                txtSearchHere.setToolTipText("Enter Course ID to search...");
                break;
            case "Course Name":
                txtSearchHere.setToolTipText("Enter Course Name to search...");
                break;
            case "Faculty":
                txtSearchHere.setToolTipText("Enter Faculty Name to search...");
                break;
            case "Department":
                txtSearchHere.setToolTipText("Enter Department to search...");
                break;
            default:
                txtSearchHere.setToolTipText("Search here...");
        }
    }   
    }//GEN-LAST:event_filterComboBoxActionPerformed

    private void SemesterjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SemesterjComboBoxActionPerformed
        // TODO add your handling code here:
        populateAvailableCoursesTable();
        
    }//GEN-LAST:event_SemesterjComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SemesterjComboBox;
    private javax.swing.JTextField TxtCreditsCurrentlyRegistered;
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnEnroll;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JTable courseOfferingsJTable;
    private javax.swing.JTable currentEnrollmentJTable;
    private javax.swing.JComboBox<String> filterComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblSearchHere;
    private javax.swing.JLabel lblSelectSemester;
    private javax.swing.JTextField txtSearchHere;
    // End of variables declaration//GEN-END:variables
}
