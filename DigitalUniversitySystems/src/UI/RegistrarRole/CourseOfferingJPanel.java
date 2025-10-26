/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.RegistrarRole;

import Model.Course;
import Model.CourseOffering;
import Model.Faculty;
import Model.accesscontrol.ConfigureJTable;
import Model.CourseService;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
/**
 *
 * @author jayan
 */
public class CourseOfferingJPanel extends javax.swing.JPanel {

    private CourseOffering selectedOffering;
    private final CourseService courseService = new CourseService(); // Initialize service here
    private List<Faculty> facultyList; 
    
    // NOTE: jComboBoxFacultyList is the correct variable name defined in initComponents

    public CourseOfferingJPanel() {
        initComponents();
        initializeData();
        setEditMode(false);
    }
    
    private void initializeData() {
        this.facultyList = getFacultyListFromDirectory();
        populateFacultyComboBox();
        populateSemesterDropdown();
    }
    
    private List<Faculty> getFacultyListFromDirectory() {
        return ConfigureJTable.directory.getUserAccountList().stream()
                .filter(ua -> ua.getProfile() instanceof Faculty)
                .map(ua -> (Faculty) ua.getProfile())
                .collect(Collectors.toList());
    }
    
    // --- FIX 1: Use jComboBoxFacultyList consistently ---
    private void populateFacultyComboBox() {
        // Clear old items and add faculty names
        jComboBoxFacultyList.removeAllItems();
        jComboBoxFacultyList.addItem("-- Select Faculty --");
        
        for (Faculty faculty : facultyList) {
            // FIX: Use jComboBoxFacultyList.addItem()
            jComboBoxFacultyList.addItem(faculty.getPerson().getName() + " (" + faculty.getPerson().getUNID() + ")");
        }
    }
    
    private void setEditMode(boolean enable) {
        // Fields for NEW COURSE CREATION
        txtCourseID.setEditable(enable); 
        txtCourseName.setEditable(enable); 
        
        // Fields for UPDATING OFFERING DETAILS (Registrar Responsibilities)
        jComboBoxFacultyList.setEnabled(enable); // Assign Faculty (FIX: Use jComboBoxFacultyList)
        txtEnrollmentCapacity.setEditable(enable); // Set Capacity
        txtRoomTime.setEditable(enable); // Update Schedule
        
        btnSave.setEnabled(enable);
        btnEdit.setEnabled(!enable); 
        btnDelete.setEnabled(selectedOffering != null); 
        btnNew.setEnabled(!enable); 
    }
    
    private void populateSemesterDropdown() {
        jComboBoxSemester.removeAllItems();
        jComboBoxSemester.addItem("Fall 2025");
        jComboBoxSemester.addItem("Spring 2026");
        
        if (jComboBoxSemester.getItemCount() > 0) {
             jComboBoxSemester.setSelectedIndex(0);
        }
    }

    private void populateCourseTable(String semester) {
        DefaultTableModel model = (DefaultTableModel) tblCourseOffering.getModel();
        model.setRowCount(0);

        List<CourseOffering> offerings = courseService.getOfferingsBySemester(semester);

        for (CourseOffering offer : offerings) {
            Object[] row = new Object[5];
            row[0] = offer.getCourse().getCourseID();
            row[1] = offer.getCourse().getName();     
            row[2] = offer.getFaculty().getPerson().getName(); // Faculty Name
            row[3] = offer.getCapacity();             
            row[4] = offer.getSchedule();             
            model.addRow(row);
        }
        selectedOffering = null;
        setEditMode(false);
        clearFields();
    }
    
    private void clearFields() {
        txtCourseID.setText("");
        txtCourseName.setText("");
        jComboBoxFacultyList.setSelectedIndex(0); // FIX: Use jComboBoxFacultyList
        txtEnrollmentCapacity.setText("");
        txtRoomTime.setText("");
        selectedOffering = null;
    }
    
    private void displaySelectedCourseDetails() {
        int selectedRow = tblCourseOffering.getSelectedRow();
        if (selectedRow < 0) return;

        DefaultTableModel model = (DefaultTableModel) tblCourseOffering.getModel();
        
        String courseID = model.getValueAt(selectedRow, 0).toString();
        String semester = (String) jComboBoxSemester.getSelectedItem();
        
        selectedOffering = courseService.findCourseOffering(courseID, semester);
        
        if (selectedOffering != null) {
            txtCourseID.setText(selectedOffering.getCourse().getCourseID());
            txtCourseName.setText(selectedOffering.getCourse().getName());
            
            // Set Faculty ComboBox value based on the current offering's faculty
            String facultyName = selectedOffering.getFaculty().getPerson().getName() + 
                               " (" + selectedOffering.getFaculty().getPerson().getUNID() + ")";
            jComboBoxFacultyList.setSelectedItem(facultyName); // FIX: Use jComboBoxFacultyList
            
            txtEnrollmentCapacity.setText(String.valueOf(selectedOffering.getCapacity()));
            txtRoomTime.setText(selectedOffering.getSchedule());
        }
        setEditMode(false); 
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxSemester = new javax.swing.JComboBox<>();
        lblSemester = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCourseOffering = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        lblCourseID = new javax.swing.JLabel();
        lblCourseName = new javax.swing.JLabel();
        lblFaculty = new javax.swing.JLabel();
        txtCourseID = new javax.swing.JTextField();
        txtCourseName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        lblEnrollmentCapacity = new javax.swing.JLabel();
        txtEnrollmentCapacity = new javax.swing.JTextField();
        lblRoomTime = new javax.swing.JLabel();
        txtRoomTime = new javax.swing.JTextField();
        jComboBoxFacultyList = new javax.swing.JComboBox<>();
        btnNew = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setPreferredSize(new java.awt.Dimension(600, 465));

        jComboBoxSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSemesterActionPerformed(evt);
            }
        });

        lblSemester.setText("Semester");

        tblCourseOffering.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course ID", "Course Name", "Faculty", "Enrollment Capacity", "Room/Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCourseOffering);

        btnDelete.setBackground(new java.awt.Color(255, 204, 204));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblCourseID.setText("Course ID");

        lblCourseName.setText("Course Name");

        lblFaculty.setText("Faculty");

        txtCourseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCourseNameActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(255, 204, 204));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 204, 204));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        lblEnrollmentCapacity.setText("Enrollment Capacity");

        txtEnrollmentCapacity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnrollmentCapacityActionPerformed(evt);
            }
        });

        lblRoomTime.setText("Room/Time");

        jComboBoxFacultyList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnNew.setBackground(new java.awt.Color(255, 204, 204));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(lblSemester)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRoomTime)
                    .addComponent(lblEnrollmentCapacity)
                    .addComponent(lblFaculty)
                    .addComponent(lblCourseID)
                    .addComponent(lblCourseName)
                    .addComponent(btnEdit))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNew)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCourseName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addComponent(txtEnrollmentCapacity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addComponent(txtRoomTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addComponent(jComboBoxFacultyList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(40, 40, 40)
                        .addComponent(btnSave)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSemester))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCourseID)
                            .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCourseName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFaculty)
                    .addComponent(jComboBoxFacultyList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnrollmentCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEnrollmentCapacity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRoomTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRoomTime)
                    .addComponent(btnSave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnNew))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxSemesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSemesterActionPerformed
        // TODO add your handling code here:
        String selectedSemester = (String) jComboBoxSemester.getSelectedItem();
        if (selectedSemester != null) {
            populateCourseTable(selectedSemester);
        }
    }//GEN-LAST:event_jComboBoxSemesterActionPerformed

    private void txtCourseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCourseNameActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtCourseNameActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String semester = (String) jComboBoxSemester.getSelectedItem();
        String courseID = txtCourseID.getText();
        String capacityStr = txtEnrollmentCapacity.getText();
        String roomTime = txtRoomTime.getText();
        String facultySelected = (String) jComboBoxFacultyList.getSelectedItem(); // FIX: Use jComboBoxFacultyList
        
        // 1. Validation (CRUCIAL)
        if (courseID.isEmpty() || capacityStr.isEmpty() || roomTime.isEmpty() || facultySelected.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Validation Failed: All fields must be selected/filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int capacity;
        try { capacity = Integer.parseInt(capacityStr); if (capacity <= 0) throw new NumberFormatException(); } 
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Capacity must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE); return; }

        // Find the selected Faculty object by ID
        String facultyId = facultySelected.substring(facultySelected.lastIndexOf('(') + 1, facultySelected.lastIndexOf(')'));
        
        // NOTE: findFacultyByUNID does not exist in CourseService; we assume it exists or use the directory
        // For now, let's use the directory lookup:
        Faculty newFaculty = (Faculty) ConfigureJTable.directory.findUserAccount(facultyId).getProfile();
        
        if (newFaculty == null) {
            JOptionPane.showMessageDialog(this, "Faculty lookup failed. Cannot assign course.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- 2. CREATE or UPDATE Logic ---
        if (selectedOffering == null) {
            // A) CREATE NEW COURSE OFFERING
            Course baseCourse = courseService.getCourseById(courseID);
            if (baseCourse == null) {
                JOptionPane.showMessageDialog(this, "Error: Base Course ID not found. Cannot create offering.", "Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            CourseOffering newOffer = new CourseOffering(baseCourse, semester, newFaculty, capacity, roomTime);
            ConfigureJTable.courseOfferingList.add(newOffer);
            JOptionPane.showMessageDialog(this, "New Course Offering created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            // B) UPDATE EXISTING COURSE OFFERING
            boolean success = courseService.updateCourseOffering(courseID, semester, newFaculty, capacity, roomTime);
            if (success) {
                 JOptionPane.showMessageDialog(this, "Course Offering updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // 3. Finalize
        setEditMode(false);
        populateCourseTable(semester);
        clearFields();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtEnrollmentCapacityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnrollmentCapacityActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtEnrollmentCapacityActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (selectedOffering == null) {
            JOptionPane.showMessageDialog(this, "Please select a course offering to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        setEditMode(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (selectedOffering == null) {
            JOptionPane.showMessageDialog(this, "Please select a course offering to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this course offering?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (selectedOffering.getEnrolledCount() > 0) {
                 JOptionPane.showMessageDialog(this, "Deletion Failed: Cannot delete offering with active enrollments.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            boolean success = courseService.deleteOffering(selectedOffering.getCourse().getCourseID(), selectedOffering.getSemester());
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Course offering deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            populateCourseTable((String) jComboBoxSemester.getSelectedItem());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clearFields();
        selectedOffering = null; 
        setEditMode(true);
        txtCourseID.requestFocus();
        btnDelete.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> jComboBoxFacultyList;
    private javax.swing.JComboBox<String> jComboBoxSemester;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCourseID;
    private javax.swing.JLabel lblCourseName;
    private javax.swing.JLabel lblEnrollmentCapacity;
    private javax.swing.JLabel lblFaculty;
    private javax.swing.JLabel lblRoomTime;
    private javax.swing.JLabel lblSemester;
    private javax.swing.JTable tblCourseOffering;
    private javax.swing.JTextField txtCourseID;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtEnrollmentCapacity;
    private javax.swing.JTextField txtRoomTime;
    // End of variables declaration//GEN-END:variables
}
