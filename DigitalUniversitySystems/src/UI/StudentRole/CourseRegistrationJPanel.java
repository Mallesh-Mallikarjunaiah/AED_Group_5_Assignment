/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.StudentRole;


import Model.Student;
import Model.CourseOffering;
import Model.Enrollment;
import Model.EnrollmentService;
import Model.CourseService;
import Model.accesscontrol.ConfigureJTable;
import Model.User.UserAccount;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
/**
 *
 * @author MALLESH
 */
public class CourseRegistrationJPanel extends javax.swing.JPanel {

    private Student loggedInStudent;
    private EnrollmentService enrollmentService;
    private CourseService courseService;

    // Lists for holding current displayed data
    private List<CourseOffering> availableOfferings = new ArrayList<>();
    private List<Enrollment> currentEnrollments = new ArrayList<>(); 
    
    // Max credit hour limit (Assignment Requirement)
    private static final int MAX_CREDITS = 8; 

    public CourseRegistrationJPanel(JPanel workArea, UserAccount userAccount) {
        initComponents();
        this.loggedInStudent = (Student) userAccount.getProfile();
        this.enrollmentService = new EnrollmentService();
        this.courseService = new CourseService();
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        populateSemesterDropdown();
        populateFilterDropdown();
        
        // Initial table load (shows empty or all for the default semester)
        SemesterjComboBoxActionPerformed(null); 
        updateCurrentCreditsDisplay();
    }
    
    private void populateSemesterDropdown() {
        SemesterjComboBox.removeAllItems();
        SemesterjComboBox.addItem("-- Select Semester --");
        // Pull unique semesters from CourseOfferings (central store)
        ConfigureJTable.courseOfferingList.stream()
            .map(CourseOffering::getSemester)
            .distinct()
            .forEach(SemesterjComboBox::addItem);
    }
    
    private void populateFilterDropdown() {
        filterComboBox.removeAllItems();
        filterComboBox.addItem("Course ID");
        filterComboBox.addItem("Course Name");
        filterComboBox.addItem("Teacher Name"); // Required 3 search methods
    }
    
    private void updateCurrentCreditsDisplay() {
        String semester = (String) SemesterjComboBox.getSelectedItem();
        if (semester == null || semester.startsWith("--")) {
            TxtCreditsCurrentlyRegistered.setText("0.0 / " + MAX_CREDITS);
            return;
        }
        
        double currentCredits = enrollmentService.getCurrentActiveCredits(loggedInStudent, semester);
        TxtCreditsCurrentlyRegistered.setText(currentCredits + " / " + MAX_CREDITS);
        
        if (currentCredits >= MAX_CREDITS) {
             // Visually warn student about maximum capacity
             TxtCreditsCurrentlyRegistered.setForeground(java.awt.Color.RED);
        } else {
             TxtCreditsCurrentlyRegistered.setForeground(java.awt.Color.BLACK);
        }
    }
    
    /**
     * Populates the TOP table with available courses based on the selected semester/search.
     */
    private void populateAvailableCoursesTable() {
        String semester = (String) SemesterjComboBox.getSelectedItem();
        DefaultTableModel model = (DefaultTableModel) courseOfferingsJTable.getModel();
        model.setRowCount(0);
        availableOfferings.clear();

        if (semester == null || semester.startsWith("--")) return;

        // 1. Get student's current active enrollment IDs for comparison
        List<String> enrolledIDs = enrollmentService.getEnrollmentsByStudent(loggedInStudent).stream()
            .filter(Enrollment::isActive)
            .map(e -> e.getCourseOffering().getCourse().getCourseID())
            .collect(Collectors.toList());

        // 2. Get all offerings for the semester
        List<CourseOffering> allOfferings = courseService.getOfferingsBySemester(semester);

        // 3. Populate table with Availability Status
        for (CourseOffering offer : allOfferings) {
            String courseID = offer.getCourse().getCourseID();
            String status;

            if (enrolledIDs.contains(courseID)) {
                status = "ENROLLED";
            } else if (offer.getEnrolledCount() >= offer.getCapacity()) {
                status = "FULL";
            } else {
                status = "AVAILABLE";
                availableOfferings.add(offer); // Store only available ones locally for enrollment logic
            }

            model.addRow(new Object[]{
                courseID, 
                offer.getCourse().getName(),
                offer.getFaculty().getPerson().getName(),
                offer.getFaculty().getDepartment().toString(),
                offer.getCourse().getCredits(),
                offer.getSchedule(),
                offer.getCapacity(),
                status
            });
        }
        
        // Populate the bottom table with current enrollments
        populateCurrentEnrollmentTable();
    }
    
    /**
     * Populates the BOTTOM table with the student's CURRENT enrollment status.
     */
    private void populateCurrentEnrollmentTable() {
        DefaultTableModel model = (DefaultTableModel) currentEnrollmentJTable.getModel();
        model.setRowCount(0);
        
        this.currentEnrollments = enrollmentService.getEnrollmentsByStudent(loggedInStudent);
        
        for (Enrollment enrollment : currentEnrollments) {
            CourseOffering offer = enrollment.getCourseOffering();
            if (enrollment.isActive()) { // Only show active/dropped courses
                model.addRow(new Object[]{
                    offer.getCourse().getCourseID(),
                    offer.getCourse().getName(),
                    offer.getCourse().getCredits(),
                    offer.getFaculty().getPerson().getName(),
                    "N/A" // Enrollment Date (Simplify for now)
                });
            }
        }
    }
        
    
    private void displaySearchResults(List<CourseOffering> results) {
         DefaultTableModel model = (DefaultTableModel) courseOfferingsJTable.getModel();
         model.setRowCount(0);
         
         if (results.isEmpty()) {
             JOptionPane.showMessageDialog(this, "No courses found matching your criteria.");
             return;
         }
         
         // Repopulate the top table with search results only
         for (CourseOffering offer : results) {
             String status = availableOfferings.contains(offer) ? "AVAILABLE" : "FULL/ENROLLED";
             
             model.addRow(new Object[]{
                offer.getCourse().getCourseID(), 
                offer.getCourse().getName(),
                offer.getFaculty().getPerson().getName(),
                offer.getFaculty().getDepartment().toString(),
                offer.getCourse().getCredits(),
                offer.getSchedule(),
                offer.getCapacity(),
                status
            });
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
        
    }//GEN-LAST:event_txtSearchHereActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        txtSearchHere.setText("");
        populateAvailableCoursesTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollActionPerformed
        // TODO add your handling code here:
        int selectedRow = courseOfferingsJTable.getSelectedRow();
        String semester = (String) SemesterjComboBox.getSelectedItem();
        
        if (selectedRow < 0 || semester.startsWith("--")) {
             JOptionPane.showMessageDialog(this, "Please select a course and semester.");
             return;
        }
        
        String status = (String) courseOfferingsJTable.getValueAt(selectedRow, 7);
        if (!status.equals("AVAILABLE")) {
             JOptionPane.showMessageDialog(this, "Cannot enroll: Course is already " + status + ".");
             return;
        }
        
        // 1. Get the Course Offering
        String courseID = (String) courseOfferingsJTable.getValueAt(selectedRow, 0);
        CourseOffering offeringToEnroll = courseService.findCourseOffering(courseID, semester);
        
        // 2. Check 8-Credit Hour Limit (Assignment Requirement)
        double courseCredits = (double) courseOfferingsJTable.getValueAt(selectedRow, 4);
        double currentCredits = enrollmentService.getCurrentActiveCredits(loggedInStudent, semester);
        
        if (currentCredits + courseCredits > MAX_CREDITS) {
             JOptionPane.showMessageDialog(this, "Enrollment Failed: Exceeds maximum " + MAX_CREDITS + " credit hour limit.", "Load Limit Exceeded", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        // 3. Perform Enrollment
        boolean success = enrollmentService.enrollStudent(loggedInStudent, offeringToEnroll, false); // Not admin override
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Enrollment successful for " + courseID, "Success", JOptionPane.INFORMATION_MESSAGE);
            // Refresh both tables
            populateAvailableCoursesTable();
            updateCurrentCreditsDisplay();
        } else {
             JOptionPane.showMessageDialog(this, "Enrollment Failed (Course may be full).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEnrollActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
        // TODO add your handling code here:
        int selectedRow = currentEnrollmentJTable.getSelectedRow();
        if (selectedRow < 0) {
             JOptionPane.showMessageDialog(this, "Please select an enrolled course from the bottom table to drop.");
             return;
        }
        
        // Get the enrollment object to drop (from the persistent list)
        Enrollment enrollmentToDrop = (Enrollment) currentEnrollmentJTable.getValueAt(selectedRow, 0); // Need to store object in table or list
        
        // NOTE: Since the JTable stores strings, we must manually find the object in the currentEnrollments list:
        String courseID = (String) currentEnrollmentJTable.getValueAt(selectedRow, 0);
        Enrollment toDrop = currentEnrollments.stream()
            .filter(e -> e.getCourseOffering().getCourseID().equals(courseID) && e.isActive())
            .findFirst().orElse(null);
        
        if (toDrop != null) {
            // Perform Drop (Tuition refund handled inside dropStudent method, per assignment)
            boolean success = enrollmentService.dropStudent(toDrop);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Course dropped successfully. Refund initiated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                populateAvailableCoursesTable();
                updateCurrentCreditsDisplay();
            } else {
                 JOptionPane.showMessageDialog(this, "Drop failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDropActionPerformed

    private void TxtCreditsCurrentlyRegisteredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCreditsCurrentlyRegisteredActionPerformed
        // TODO add your handling code here:  
    }//GEN-LAST:event_TxtCreditsCurrentlyRegisteredActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String filterType = (String) filterComboBox.getSelectedItem();
        String query = txtSearchHere.getText().trim();
        
        if (query.isEmpty() || filterType == null) {
            JOptionPane.showMessageDialog(this, "Please enter a search query and select a filter type.");
            return;
        }

        List<CourseOffering> results = new ArrayList<>();
        List<CourseOffering> offeringsToSearch = courseService.getOfferingsBySemester((String) SemesterjComboBox.getSelectedItem());
        
        switch (filterType) {
            case "Course ID":
                results = offeringsToSearch.stream()
                    .filter(o -> o.getCourse().getCourseID().equalsIgnoreCase(query))
                    .collect(Collectors.toList());
                break;
            case "Course Name":
                 results = offeringsToSearch.stream()
                    .filter(o -> o.getCourse().getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
                break;
            case "Teacher Name":
                results = offeringsToSearch.stream()
                    .filter(o -> o.getFaculty().getPerson().getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
                break;
        }
        
        // Display search results
        displaySearchResults(results);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void filterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterComboBoxActionPerformed
        // TODO add your handling code here:
         String selectedFilter = (String) filterComboBox.getSelectedItem();

        if (selectedFilter != null) {
            switch (selectedFilter) {
                case "Course ID":
                    txtSearchHere.setToolTipText("Enter Course ID to search (e.g., INFO5100)");
                    break;
                case "Course Name":
                    txtSearchHere.setToolTipText("Enter Course Name to search");
                    break;
                case "Faculty Name":
                    txtSearchHere.setToolTipText("Enter Faculty Name to search");
                    break;
                case "Department":
                    txtSearchHere.setToolTipText("Enter Department to search (CS, IS, DS, AI)");
                    break;
                default:
                    txtSearchHere.setToolTipText("Search here...");
            }
        }
    }//GEN-LAST:event_filterComboBoxActionPerformed

    private void SemesterjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SemesterjComboBoxActionPerformed
        // TODO add your handling code here:
        populateAvailableCoursesTable();
        updateCurrentCreditsDisplay();
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
