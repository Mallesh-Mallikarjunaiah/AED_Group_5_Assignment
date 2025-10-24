/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.RegistrarRole;

import Model.Department; // Import your Department enum
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector; // Used for JTable structure management
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author jayan
 */
public class InstitutionalReportsJPanel extends javax.swing.JPanel {

    private ButtonGroup reportButtonGroup;
    
    public InstitutionalReportsJPanel() {
        initComponents();
        setupReportControls();
    }

    private void setupReportControls() {
        // 1. Group the Radio Buttons
        reportButtonGroup = new ButtonGroup();
        reportButtonGroup.add(radioEnrollmentByDepartment);
        reportButtonGroup.add(radioGPADistribution);
        
        // Default selection and table setup
        radioEnrollmentByDepartment.setSelected(true);
        populateFilterDropdown();
        updateTableStructure(true); // Default to Enrollment Report structure
    }
    
    private void populateFilterDropdown() {
        jComboBoxFilter.removeAllItems();
        
        // If Enrollment Report is selected, populate with Departments
        if (radioEnrollmentByDepartment.isSelected()) {
            jComboBoxFilter.addItem("All Departments");
            for (Department dept : Department.values()) {
                jComboBoxFilter.addItem(dept.toString());
            }
        } 
        // If GPA Distribution is selected, populate with Programs/Semesters
        else if (radioGPADistribution.isSelected()) {
            jComboBoxFilter.addItem("All Programs");
            jComboBoxFilter.addItem("MSIS");
            jComboBoxFilter.addItem("MSCS");
        }
    }
    
    // Dynamically update JTable columns based on the selected report type
    private void updateTableStructure(boolean isEnrollmentReport) {
        DefaultTableModel model = new DefaultTableModel();
        
        if (isEnrollmentReport) {
            model.setColumnIdentifiers(new Object[]{
                "Course ID", "Course Name", "Department", "Total Enrolled", "Capacity", "Capacity Used (%)"
            });
        } else {
            // GPA Distribution by Program/Course
            model.setColumnIdentifiers(new Object[]{
                "Program/Course", "Students (N)", "Average GPA", "Students > 3.5", "Students < 3.0"
            });
        }
        
        tblInstitutionalReport.setModel(model);
    }
    
    private void generateReportData() {
        if (radioEnrollmentByDepartment.isSelected()) {
            generateEnrollmentReport();
        } else if (radioGPADistribution.isSelected()) {
            generateGPADistributionReport();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a Report Type.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateEnrollmentReport() {
        updateTableStructure(true);
        DefaultTableModel model = (DefaultTableModel) tblInstitutionalReport.getModel();
        model.setRowCount(0);

        String filter = (String) jComboBoxFilter.getSelectedItem();
        
        // --- MOCK LOGIC for Enrollment by Department/Course ---
        // This logic needs to pull data from CourseOffering and Enrollment records
        
        // Report 1: Total Enrollment (example)
        if (filter.equals("All Departments") || filter.equals("IS")) {
            model.addRow(new Object[]{"INFO 5100", "App Engineering", "IS", 35, 40, "87.5%"});
            model.addRow(new Object[]{"INFO 6205", "Data Mining", "IS", 15, 30, "50.0%"});
        }
        if (filter.equals("All Departments") || filter.equals("CS")) {
            model.addRow(new Object[]{"CS 5010", "Algorithms", "CS", 28, 30, "93.3%"});
        }
        // --- END MOCK LOGIC ---
        
        if (model.getRowCount() == 0) {
             JOptionPane.showMessageDialog(this, "No enrollment data found for the selected filter.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void generateGPADistributionReport() {
        updateTableStructure(false);
        DefaultTableModel model = (DefaultTableModel) tblInstitutionalReport.getModel();
        model.setRowCount(0);

        String filter = (String) jComboBoxFilter.getSelectedItem();

        // --- MOCK LOGIC for GPA Distribution by Program ---
        // This logic needs to query Student records and aggregate GPA data
        
        // Report 2: GPA Distribution (example)
        if (filter.equals("All Programs") || filter.equals("MSIS")) {
            model.addRow(new Object[]{"MSIS Program", 150, 3.45, 65, 10});
        }
        if (filter.equals("All Programs") || filter.equals("MSCS")) {
            model.addRow(new Object[]{"MSCS Program", 200, 3.20, 40, 25});
        }
        // --- END MOCK LOGIC ---
        
        if (model.getRowCount() == 0) {
             JOptionPane.showMessageDialog(this, "No GPA distribution data found for the selected filter.", "Info", JOptionPane.INFORMATION_MESSAGE);
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

        lblReportType = new javax.swing.JLabel();
        jComboBoxFilter = new javax.swing.JComboBox<>();
        radioEnrollmentByDepartment = new javax.swing.JRadioButton();
        radioGPADistribution = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInstitutionalReport = new javax.swing.JTable();
        btnGenerateReport = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 465));

        lblReportType.setText("Report Type");

        jComboBoxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFilterActionPerformed(evt);
            }
        });

        radioEnrollmentByDepartment.setText("Enrollment by Department");

        radioGPADistribution.setText("GPA Distribution by Program");
        radioGPADistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioGPADistributionActionPerformed(evt);
            }
        });

        tblInstitutionalReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course ID", "Course Name", "Department", "Total Enrolled Students", "Capacity", "Capacity Used"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInstitutionalReport);

        btnGenerateReport.setBackground(new java.awt.Color(255, 204, 204));
        btnGenerateReport.setText("Generate Report");
        btnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblReportType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(radioGPADistribution)
                                    .addComponent(radioEnrollmentByDepartment)))
                            .addComponent(btnGenerateReport)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReportType)
                    .addComponent(radioEnrollmentByDepartment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioGPADistribution)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGenerateReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radioGPADistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGPADistributionActionPerformed
        // TODO add your handling code here:
        // When a radio button is clicked, update the filter dropdown options
        populateFilterDropdown();
        
        // Also update the JTable columns instantly
        boolean isEnrollment = radioEnrollmentByDepartment.isSelected();
        updateTableStructure(isEnrollment);
    }//GEN-LAST:event_radioGPADistributionActionPerformed

    private void jComboBoxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFilterActionPerformed
        // TODO add your handling code here:
        // Optional: Can trigger an automatic report update when the filter changes
        // For simplicity, we only trigger on the "Generate Report" button.
    }//GEN-LAST:event_jComboBoxFilterActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        // TODO add your handling code here:
        generateReportData();
    }//GEN-LAST:event_btnGenerateReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateReport;
    private javax.swing.JComboBox<String> jComboBoxFilter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblReportType;
    private javax.swing.JRadioButton radioEnrollmentByDepartment;
    private javax.swing.JRadioButton radioGPADistribution;
    private javax.swing.JTable tblInstitutionalReport;
    // End of variables declaration//GEN-END:variables
}
