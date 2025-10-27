/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.AdminRole;

import Model.Registrar;
import Model.User.UserAccount;
import Model.User.UserAccountDirectory;
import Model.PersonService; // Assuming PersonService is available for consistency
import Model.accesscontrol.DataValidator; // For validation
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gagan
 */
public class RegistrarSearchJPanel extends javax.swing.JPanel {

    private UserAccountDirectory accountDirectory;
    private UserAccount selectedAccount;

    /**
     * Creates new form RegistrarSearchJPanel
     */
    public RegistrarSearchJPanel() {
        initComponents();
    }

    public RegistrarSearchJPanel(UserAccountDirectory accountDirectory) {
        initComponents();
        this.accountDirectory = accountDirectory;
        initializeComponents();
        populateTable();
    }

    private void initializeComponents() {
        setFieldsEditable(false);
        btnSave.setEnabled(false);
        btnEdit.setEnabled(false);
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblSearch.getModel();
        model.setRowCount(0);

        for(UserAccount account : accountDirectory.getUserAccountList()) {
            if (account.getProfile() instanceof Registrar) {
                Object[] row = new Object[5];
                row[0] = account.getProfile().getPerson().getUNID();
                row[1] = account.getProfile().getPerson().getName();
                row[2] = account.getProfile().getPerson().getEmail();
                row[3] = account.getProfile().getPerson().getContactNumber();
                row[4] = account.getProfile().isActive() ? "YES" : "NO";
                model.addRow(row);
            }
        }
    }

    private void setFieldsEditable(boolean editable) {
        txtName.setEditable(editable);
        txtEmail.setEditable(editable);
        txtContactNumber.setEditable(editable);
        txtAcademicStatus.setEditable(editable);
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
        txtAcademicStatus.setText("");
    }

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblSearch.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to view");
            return;
        }

        int unid = Integer.parseInt(tblSearch.getValueAt(selectedRow, 0).toString());
        for (UserAccount account : accountDirectory.getUserAccountList()) {
            if (account.getProfile().getPerson().getUNID() == unid) {
                selectedAccount = account;
                break;
            }
        }

        if (selectedAccount != null) {
            txtName.setText(selectedAccount.getProfile().getPerson().getName());
            txtEmail.setText(selectedAccount.getProfile().getPerson().getEmail());
            txtContactNumber.setText(selectedAccount.getProfile().getPerson().getContactNumber());
            txtAcademicStatus.setText(selectedAccount.getProfile().isActive() ? "YES" : "NO");

            setFieldsEditable(false);
            btnSave.setEnabled(false);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(false);
            btnView.setEnabled(false);
        }
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(this, "Please view a record first");
            return;
        }
        setFieldsEditable(true);
        btnSave.setEnabled(true);
        btnView.setEnabled(false);
        btnDelete.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblSearch.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this registrar?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int unid = Integer.parseInt(tblSearch.getValueAt(selectedRow, 0).toString());

            UserAccount accountToRemove = null;
            for (UserAccount account : accountDirectory.getUserAccountList()) {
                if (account.getProfile().getPerson().getUNID() == unid) {
                    accountToRemove = account;
                    break;
                }
            }

            if (accountToRemove != null) {
                accountDirectory.getUserAccountList().remove(accountToRemove);
                populateTable();
                clearFields();
                selectedAccount = null;
                JOptionPane.showMessageDialog(this, "Registrar deleted successfully!");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (selectedAccount == null) {
            return;
        }

        // Update the account information
        selectedAccount.getProfile().getPerson().setName(txtName.getText());
        selectedAccount.getProfile().getPerson().setEmail(txtEmail.getText());
        selectedAccount.getProfile().getPerson().setContactNumber(txtContactNumber.getText());
        selectedAccount.getProfile().setActive(txtAcademicStatus.getText().equalsIgnoreCase("YES"));

        // Refresh table and reset UI
        populateTable();
        clearFields();
        setFieldsEditable(false);
        btnSave.setEnabled(false);
        btnView.setEnabled(true);
        btnDelete.setEnabled(true);
        selectedAccount = null;

        JOptionPane.showMessageDialog(this, "Record updated successfully!");
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEdit = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblContactNumber = new javax.swing.JLabel();
        txtContactNumber = new javax.swing.JTextField();
        txtAcademicStatus = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSearch = new javax.swing.JTable();
        lblAcademicStatus = new javax.swing.JLabel();
        btnView = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        lblEmail.setText("E-Mail");

        lblContactNumber.setText("Contact Number");

        tblSearch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "UNID", "Name", "E-Mail", "Contact #"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSearch);

        lblAcademicStatus.setText("Academic Status");

        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblName.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblContactNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAcademicStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail)
                            .addComponent(txtContactNumber)
                            .addComponent(txtAcademicStatus)
                            .addComponent(txtName)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnView)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEdit)
                                .addGap(88, 88, 88)
                                .addComponent(btnDelete)))))
                .addGap(130, 130, 130))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContactNumber)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAcademicStatus)
                    .addComponent(txtAcademicStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave)
                .addContainerGap(86, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAcademicStatus;
    private javax.swing.JLabel lblContactNumber;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblSearch;
    private javax.swing.JTextField txtAcademicStatus;
    private javax.swing.JTextField txtContactNumber;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
