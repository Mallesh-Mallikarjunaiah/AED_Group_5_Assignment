/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.AdminRole;


import Model.User.UserAccountDirectory;
import Model.User.UserAccount;
import Model.Profile;
import Model.ProfileEnum;
import Model.CourseOffering;
import Model.Enrollment;
import Model.FinancialRecord;
import Model.accesscontrol.ConfigureJTable; // Required to pull live data
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author gagan
 */
public class AnalyticsDashboardJPanel extends javax.swing.JPanel {

    private UserAccountDirectory accountDirectory; // injected from parent
    
    // NOTE: The panel will now PULL data from ConfigureJTable when needed, 
    // instead of relying on external setters/injected lists.

    /**
     * Creates new form AnalyticsDashboardJPanel
     */
    public AnalyticsDashboardJPanel() {
        initComponents();
        populateMetricCombo();
        // Initial call relies on ConfigureJTable being set by LoginPanel
        populateAnalyticsTable(); 
    }

    public AnalyticsDashboardJPanel(UserAccountDirectory accountDirectory) {
        initComponents();
        this.accountDirectory = accountDirectory;
        populateMetricCombo();
        // Initial call relies on ConfigureJTable being set by LoginPanel
        populateAnalyticsTable(); 
    }

    // --- Data Retrieval Methods (PULLING FROM CONFIGUREJTABLE) ---
    // These methods now directly access the static lists.

    private Map<String, Integer> getActiveUsersByRole() {
        if (this.accountDirectory == null) return null;
        Map<String, Integer> map = new HashMap<>();
        
        // Initialize map with all roles having a count of 0
        for (ProfileEnum pe : ProfileEnum.values()) map.put(pe.toString(), 0);
        
        for (UserAccount ua : accountDirectory.getUserAccountList()) {
            Profile p = ua.getProfile();
            if (p != null) {
                String role = p.getRole();
                map.put(role, map.getOrDefault(role, 0) + 1);
            }
        }
        return map;
    }

    private Map<String, Integer> getCoursesOfferedPerSemester() {
        // Data comes from the central list
        List<CourseOffering> offerings = ConfigureJTable.courseOfferingList;
        Map<String, Integer> map = new HashMap<>();
        for (CourseOffering o : offerings) {
            map.put(o.getSemester(), map.getOrDefault(o.getSemester(), 0) + 1);
        }
        return map;
    }

    private Map<String, Integer> getEnrolledStudentsPerCourse() {
        // Data comes from the central list
        List<Enrollment> enrollments = ConfigureJTable.enrollmentList;
        Map<String, Integer> map = new HashMap<>();
        for (Enrollment e : enrollments) {
            // Only count active enrollments
            if (e.isActive()) { 
                String courseKey = e.getCourseOffering().getCourse().getCourseID() + " - " + e.getCourseOffering().getCourse().getName();
                map.put(courseKey, map.getOrDefault(courseKey, 0) + 1);
            }
        }
        return map;
    }

    private double getTuitionRevenueSummary() {
        // Data comes from the central list
        List<FinancialRecord> records = ConfigureJTable.financialRecordList;
        double sum = 0.0;
        for (FinancialRecord fr : records) {
            // Only sum PAID records
            if (fr.getType() != null && fr.getType().equalsIgnoreCase("PAID")) {
                sum += fr.getAmount();
            }
        }
        return sum;
    }

    private Map<String, Double> getTuitionRevenuePerSemester() {
        // Data comes from the central list
        List<FinancialRecord> records = ConfigureJTable.financialRecordList;
        Map<String, Double> map = new HashMap<>();
        for (FinancialRecord fr : records) {
            if (fr.getType() != null && fr.getType().equalsIgnoreCase("PAID")) {
                map.put(fr.getSemester(), map.getOrDefault(fr.getSemester(), 0.0) + fr.getAmount());
            }
        }
        return map;
    }
    // --- End Data Retrieval Methods ---


    private void populateMetricCombo() {
        comboxMetric.removeAllItems();
        comboxMetric.addItem("Active Users by Role");
        comboxMetric.addItem("Courses Offered per Semester");
        comboxMetric.addItem("Enrolled Students per Course");
        comboxMetric.addItem("Tuition Revenue");
        comboxMetric.addActionListener(evt -> populateAnalyticsTable());
    }

    private void populateAnalyticsTable() {
        String metric = (String) comboxMetric.getSelectedItem();
        if (metric == null) metric = "Active Users by Role";

        switch (metric) {
            case "Active Users by Role": {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"Role", "Active Users"});
                Map<String, Integer> activeByRole = getActiveUsersByRole();
                if (activeByRole != null) {
                    for (Map.Entry<String, Integer> e : activeByRole.entrySet()) {
                        model.addRow(new Object[]{e.getKey(), e.getValue()});
                    }
                }
                tblActiveUsers.setModel(model);
                break;
            }
            case "Courses Offered per Semester": {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"Semester", "Courses Offered"});
                Map<String, Integer> coursesPerSemester = getCoursesOfferedPerSemester();
                for (Map.Entry<String, Integer> e : coursesPerSemester.entrySet()) {
                    model.addRow(new Object[]{e.getKey(), e.getValue()});
                }
                tblActiveUsers.setModel(model);
                break;
            }
            case "Enrolled Students per Course": {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"Course", "Enrolled Students"});
                Map<String, Integer> enrolledPerCourse = getEnrolledStudentsPerCourse();
                for (Map.Entry<String, Integer> e : enrolledPerCourse.entrySet()) {
                    model.addRow(new Object[]{e.getKey(), e.getValue()});
                }
                tblActiveUsers.setModel(model);
                break;
            }
            case "Tuition Revenue": {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"Semester", "Revenue (PAID)"});
                Map<String, Double> revenuePerSemester = getTuitionRevenuePerSemester();
                for (Map.Entry<String, Double> e : revenuePerSemester.entrySet()) {
                    model.addRow(new Object[]{e.getKey(), String.format("$%.2f", e.getValue())});
                }
                if (revenuePerSemester.isEmpty()) {
                    double total = getTuitionRevenueSummary();
                    model.addRow(new Object[]{"Total", String.format("$%.2f", total)});
                }
                tblActiveUsers.setModel(model);
                break;
            }
            default:
                // Default case, should not be hit
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblActiveUsers = new javax.swing.JTable();
        lblActiveUsers = new javax.swing.JLabel();
        comboxMetric = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        tblActiveUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Metric", "Value"
            }
        ));
        jScrollPane1.setViewportView(tblActiveUsers);

        lblActiveUsers.setBackground(new java.awt.Color(255, 255, 255));
        lblActiveUsers.setText("Select metric and view analytics");

        comboxMetric.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active Users by Role", "Courses Offered per Semester", "Enrolled Students per Course", "Tuition Revenue" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboxMetric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                         .addGap(42, 42, 42)
                         .addComponent(lblActiveUsers)))
                 .addContainerGap(26, Short.MAX_VALUE))
         );
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(layout.createSequentialGroup()
                 .addGap(14, 14, 14)
                 .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboxMetric, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(lblActiveUsers))
                 .addContainerGap(338, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboxMetric;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblActiveUsers;
    private javax.swing.JTable tblActiveUsers;
    // End of variables declaration//GEN-END:variables
}
