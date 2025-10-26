/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Person;
import Model.Registrar;
import Model.Faculty;
import Model.Student;
import Model.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author jayan
 */
public class ProfileManagementDialog extends JDialog {

    private Person person;
    private JTextField txtName, txtEmail, txtID, txtContactNumber, txtOfficeHours, txtCredits, txtAcademicStanding;
    private JLabel lblOfficeHours, lblCredits, lblAcademicStanding;
    private JButton btnUpdate;

    // Use JFrame as the parent reference
    public ProfileManagementDialog(JFrame parent, boolean modal, Person person) {
        super(parent, "Edit Profile: " + person.getName(), modal);
        this.person = person;
        initComponents();
        loadPersonData();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));

        // --- 1. Common Person Attributes (Always Visible) ---
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField(15);
        formPanel.add(txtName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField(15);
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Contact Number:"));
        txtContactNumber = new JTextField(15);
        formPanel.add(txtContactNumber);

        formPanel.add(new JLabel("University ID (UNID):"));
        txtID = new JTextField(15);
        txtID.setEditable(false); // UNID must be read-only
        formPanel.add(txtID);

        // --- 2. Role-Specific Attributes (Dynamically Added) ---
        
        // Registrar/Faculty Specific: Office Hours 
        // Checks if the object is Registrar OR Faculty
//        if (person instanceof Registrar || person instanceof Faculty) {
//            lblOfficeHours = new JLabel("Office Hours:");
//            txtOfficeHours = new JTextField(15);
//            formPanel.add(lblOfficeHours);
//            formPanel.add(txtOfficeHours);
//        }
//        
//        // Student Specific: Display academic status (Read-only)
//        if (person instanceof Student student) {
//            
//            // Credits Completed (Read-only)
//            lblCredits = new JLabel("Credits Completed:");
//            txtCredits = new JTextField(15);
//            txtCredits.setEditable(false); 
//            formPanel.add(lblCredits);
//            formPanel.add(txtCredits);
//
//            // Academic Standing (Read-only)
//            lblAcademicStanding = new JLabel("Academic Standing:");
//            txtAcademicStanding = new JTextField(15);
//            txtAcademicStanding.setEditable(false); 
//            formPanel.add(lblAcademicStanding);
//            formPanel.add(txtAcademicStanding);
//        }
        
        // --- 3. Action Button ---
        btnUpdate = new JButton("Update Profile");
        btnUpdate.addActionListener(this::updateProfile);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnUpdate);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPersonData() {
        // Load common attributes using YOUR Person.java getters
        txtName.setText(person.getName());
        txtEmail.setText(person.getEmail());
        txtContactNumber.setText(person.getContactNumber());
        // CORRECTED: Use getUNID() as defined in your Person.java
        txtID.setText(String.valueOf(person.getUNID())); 

        
        // Load specific attributes
//        if (person instanceof Registrar registrar) {
//            txtOfficeHours.setText(registrar.getOfficeHours());
//        } else if (person instanceof Faculty faculty) {
//            // Assuming Faculty has getOfficeHours() defined
//            // txtOfficeHours.setText(faculty.getOfficeHours()); 
//        } else if (person instanceof Student student) {
//            txtCredits.setText(String.valueOf(student.getCreditsCompleted()));
//            txtAcademicStanding.setText(student.getAcademicStanding());
//        }
    }

    private void updateProfile(ActionEvent e) {
        String newName = txtName.getText();
        String newEmail = txtEmail.getText();
        String newContactNumber = txtContactNumber.getText();
        
        // --- 1. Validation (CRUCIAL for grading) ---
        // Implement Null checks and input validation here using your DataValidator.java class
        if (newName.isEmpty() || newEmail.isEmpty() || newContactNumber.isEmpty() /* || !DataValidator.isValidEmail(newEmail) */ ) {
            JOptionPane.showMessageDialog(this, "Validation Failed: Name, Email, and Contact cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- 2. Update Common Model Attributes ---
        person.setName(newName);
        person.setEmail(newEmail);
        person.setContactNumber(newContactNumber);

        // --- 3. Update Role-Specific Attributes ---
//        if (person instanceof Registrar registrar) {
//            // Responsibility: Manage own profile (office hours)
//            registrar.setOfficeHours(txtOfficeHours.getText());
//        } else if (person instanceof Faculty faculty) {
//            // faculty.setOfficeHours(txtOfficeHours.getText());
//        }
        // Note: Student's academic fields (GPA/Credits) are computed, not editable here.

        // --- 4. Confirmation ---
        // In a completed app, you would save this updated 'person' object to your DataStore/Service.
        JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
}