/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.RegistrarRole;

import Model.Department; 
import Model.accesscontrol.ConfigureJTable; 
import Model.CourseOffering;
import Model.Enrollment;
import Model.Student;
import Model.accesscontrol.GradeCalculator; 
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
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
        reportButtonGroup = new ButtonGroup();
        reportButtonGroup.add(radioEnrollmentByDepartment);
        reportButtonGroup.add(radioGPADistribution);
        
        radioEnrollmentByDepartment.setSelected(true);
        populateFilterDropdown();
        updateTableStructure(true); 
    }
    
    private void populateFilterDropdown() {
        jComboBoxFilter.removeAllItems();
        
        if (radioEnrollmentByDepartment.isSelected()) {
            jComboBoxFilter.addItem("All Departments");
            for (Department dept : Department.values()) {
                jComboBoxFilter.addItem(dept.toString());
            }
        } 
        else if (radioGPADistribution.isSelected()) {
            jComboBoxFilter.addItem("All Programs");
            jComboBoxFilter.addItem("MSIS"); // Mock Program
            jComboBoxFilter.addItem("MSCS"); // Mock Program
        }
    }
    
    private void updateTableStructure(boolean isEnrollmentReport) {
        DefaultTableModel model = new DefaultTableModel();
        
        if (isEnrollmentReport) {
            model.setColumnIdentifiers(new Object[]{
                "Course ID", "Course Name", "Department", "Total Enrolled", "Capacity", "Capacity Used (%)"
            });
        } else {
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
        String filterDept = filter.equals("All Departments") ? null : filter;
        
        // --- LOGIC PULLING FROM CENTRAL COURSE OFFERING LIST ---
        for (CourseOffering offer : ConfigureJTable.courseOfferingList) {
            String deptName = offer.getFaculty().getDepartment().toString();
            
            // Apply Department Filter
            if (filterDept != null && !filterDept.equals(deptName)) {
                continue;
            }
            
            double capacityUsed = (double) offer.getEnrolledCount() / offer.getCapacity() * 100;
            
            model.addRow(new Object[]{
                offer.getCourse().getCourseID(),
                offer.getCourse().getName(),
                deptName,
                offer.getEnrolledCount(),
                offer.getCapacity(),
                String.format("%.1f%%", capacityUsed)
            });
        }
        
        if (model.getRowCount() == 0) {
             JOptionPane.showMessageDialog(this, "No enrollment data found for the selected filter.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void generateGPADistributionReport() {
        updateTableStructure(false);
        DefaultTableModel model = (DefaultTableModel) tblInstitutionalReport.getModel();
        model.setRowCount(0);

        String filter = (String) jComboBoxFilter.getSelectedItem();
        
        // --- LOGIC PULLING FROM CENTRAL STUDENT LIST ---
        
        // 1. Group all students by their Department (mocking Program)
        Map<String, List<Student>> studentsByDept = ConfigureJTable.directory.getUserAccountList().stream()
                .filter(ua -> ua.getProfile() instanceof Student)
                .map(ua -> (Student) ua.getProfile())
                .filter(s -> s.getDepartment() != null)
                .collect(Collectors.groupingBy(s -> s.getDepartment().toString()));

        // 2. Map IS/AI/DS to MSIS and CS to MSCS for reporting clarity
        Map<String, List<Student>> programMap = new HashMap<>();
        programMap.put("MSIS", new ArrayList<>());
        programMap.put("MSCS", new ArrayList<>());
        
        studentsByDept.forEach((dept, students) -> {
            if (dept.equals("CS")) {
                programMap.get("MSCS").addAll(students);
            } else { // IS, AI, DS
                programMap.get("MSIS").addAll(students);
            }
        });

        // 3. Process the programs and calculate metrics
        for (Map.Entry<String, List<Student>> entry : programMap.entrySet()) {
            String programName = entry.getKey();
            List<Student> students = entry.getValue();
            
            // Apply Program Filter
            if (filter.equals("All Programs") || filter.equals(programName)) {
                
                // Collect GPAs and compute statistics
                double totalGPA = students.stream().mapToDouble(Student::getOverallGPA).sum();
                long countHighGPA = students.stream().filter(s -> s.getOverallGPA() >= 3.5).count();
                long countLowGPA = students.stream().filter(s -> s.getOverallGPA() < 3.0).count();
                double avgGPA = students.isEmpty() ? 0.0 : totalGPA / students.size();
                
                model.addRow(new Object[]{
                    programName + " Program", 
                    students.size(), 
                    String.format("%.2f", avgGPA), 
                    countHighGPA, 
                    countLowGPA
                });
            }
        }
        
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
