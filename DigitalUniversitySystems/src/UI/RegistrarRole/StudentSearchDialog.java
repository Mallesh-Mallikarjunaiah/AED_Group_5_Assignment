/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI.RegistrarRole;

import Model.Student;
import Model.User.UserAccount;
import Model.User.UserAccountDirectory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jayan
 */
public class StudentSearchDialog extends JDialog {

    private final UserAccountDirectory accountDirectory;
    private Student selectedStudent = null;
    
    // UI Components
    private JTextField txtSearch;
    private JButton btnSearch, btnSelect;
    private JTable tblResults;
    private DefaultTableModel tableModel;

    public StudentSearchDialog(JFrame parent, boolean modal, UserAccountDirectory directory) {
        super(parent, "Search Student Records", modal);
        this.accountDirectory = directory;
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Setup table model
        tableModel = new DefaultTableModel(
            new Object[]{"UNID", "Name", "Email", "Credits Completed"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblResults = new JTable(tableModel);
        
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search by Name/ID");
        btnSelect = new JButton("Select Student");
        
        // Layout components
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Enter Name or ID:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSelect);

        // Main layout
        setLayout(new BorderLayout(10, 10));
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(tblResults), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Actions
        btnSearch.addActionListener(e -> performSearch(txtSearch.getText()));
        btnSelect.addActionListener(e -> selectAndClose());
        
        // Initially, the select button is disabled
        btnSelect.setEnabled(false);
        tblResults.getSelectionModel().addListSelectionListener(e -> {
            btnSelect.setEnabled(tblResults.getSelectedRow() != -1);
        });
    }
    
    private void performSearch(String query) {
    tableModel.setRowCount(0);
    String input = query.trim();
    if (input.isEmpty()) return;
    
    String lowerInput = input.toLowerCase();
    List<UserAccount> matchedAccounts = new ArrayList<>();
    
    // Flag to track if the input is a clean integer (UNID)
    boolean isNumeric = input.matches("\\d+"); 
    
    // 1. Iterate through ALL users in the directory
    for (UserAccount ua : accountDirectory.getUserAccountList()) {
        
        // Only process Student profiles
        if (!(ua.getProfile() instanceof Student student)) {
            continue;
        }

        String name = student.getPerson().getName();
        String unid = String.valueOf(student.getPerson().getUNID()); 

        boolean isMatch = false;
        
        // Match 1: Search by EXACT UNID (Highest Priority if numeric input is given)
        if (isNumeric && unid.equals(input)) {
            isMatch = true;
        }
        // Match 2: Search by Name (Case-Insensitive, CONTAINS match for full names or parts of names)
        else if (name != null && name.toLowerCase().contains(lowerInput)) {
            isMatch = true;
        }
        
        if (isMatch) {
            matchedAccounts.add(ua);
        }
    }
    
    if (matchedAccounts.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No student found matching criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // 2. Populate table with search results
    for (UserAccount ua : matchedAccounts) {
        Student student = (Student) ua.getProfile();
        
        tableModel.addRow(new Object[]{
            student.getPerson().getUNID(), 
            student.getPerson().getName(),
            student.getPerson().getEmail(),
            student.getCreditsCompleted()
        });
    }
}

    private void selectAndClose() {
        int selectedRow = tblResults.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Retrieve UNID from the selected row
        String unid = tblResults.getValueAt(selectedRow, 0).toString();
        
        // Find the full Student object from the directory
        UserAccount account = accountDirectory.findUserAccount(unid);
        if (account != null && account.getProfile() instanceof Student) {
            this.selectedStudent = (Student) account.getProfile();
        }
        
        dispose();
    }
    
    public Student getSelectedStudent() {
        return selectedStudent;
    }
}
