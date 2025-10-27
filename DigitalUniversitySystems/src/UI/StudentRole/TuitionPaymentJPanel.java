/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.StudentRole;


import Model.*;
import Model.User.UserAccount;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author MALLESH
 */
public class TuitionPaymentJPanel extends javax.swing.JPanel {
    private JPanel workArea;
    private UserAccount userAccount;
    private Student student;
    // Local list simulating the student's payment history records in the central store
    private ArrayList<FinancialRecord> paymentHistory; 

    /**
     * Constructor with mock data initialization
     */
    public TuitionPaymentJPanel(JPanel workArea, UserAccount account) {
        initComponents();
        this.workArea = workArea;
        this.userAccount = account;
        this.student = (Student) account.getProfile();
        this.paymentHistory = new ArrayList<>();
        
        // Make fields read-only except payment amount
        txtStudentName.setEditable(false);
        txtCurrentBalance.setEditable(false);
        txtOutstandingTuition.setEditable(false);
        
        // Initialize mock data
        initializeMockPaymentHistory();
        
        txtStudentName.setText(student.getPerson().getName());
        populatePaymentTable();
        refreshBalances();
    }
    
    /**
     * Initialize mock payment history
     */
    private void initializeMockPaymentHistory() {
        // Fall 2023 - Billed and Paid (6000 paid)
        FinancialRecord bill1 = new FinancialRecord("TXN-1001", student, 6000.0, "BILLED", "Fall 2023", "2023-09-01");
        FinancialRecord pay1 = new FinancialRecord("TXN-1002", student, 6000.0, "PAID", "Fall 2023", "2023-09-15");
        
        // Spring 2024 - Billed and Paid (6000 paid)
        FinancialRecord bill2 = new FinancialRecord("TXN-1003", student, 6000.0, "BILLED", "Spring 2024", "2024-01-15");
        FinancialRecord pay2 = new FinancialRecord("TXN-1004", student, 6000.0, "PAID", "Spring 2024", "2024-02-01");
        
        // Fall 2024 - Billed and Partially Paid (7500 billed, 3000 paid) -> Balance Due: 4500.00
        FinancialRecord bill3 = new FinancialRecord("TXN-1005", student, 7500.0, "BILLED", "Fall 2024", "2024-09-01");
        FinancialRecord pay3 = new FinancialRecord("TXN-1006", student, 3000.0, "PAID", "Fall 2024", "2024-09-20");
        
        paymentHistory.add(bill1);
        paymentHistory.add(pay1);
        paymentHistory.add(bill2);
        paymentHistory.add(pay2);
        paymentHistory.add(bill3);
        paymentHistory.add(pay3);
        
        // Calculate and set student's persistent tuition balance for other use cases (e.g., Transcript Gatekeeper)
        calculateBalances();
    }

    /**
     * Calculates current balance and outstanding tuition and sets student's persistent balance.
     */
    private void calculateBalances() {
        double billed = 0, paid = 0;
        
        for (FinancialRecord fr : paymentHistory) {
            if (fr.getType().equalsIgnoreCase("BILLED")) {
                billed += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("PAID")) {
                paid += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("REFUND")) {
                paid -= fr.getAmount(); // Refunds decrease net amount paid
            }
        }
        
        double outstandingBalance = billed - paid;
        student.setTuitionBalance(outstandingBalance);
    }

    /**
     * Refresh balance display fields
     */
    private void refreshBalances() {
        double totalPaid = 0;
        double totalBilled = 0;
        
        for (FinancialRecord fr : paymentHistory) {
            if (fr.getType().equalsIgnoreCase("PAID")) {
                totalPaid += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("BILLED")) {
                totalBilled += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("REFUND")) {
                totalPaid -= fr.getAmount();
            }
        }
        
        double outstanding = totalBilled - totalPaid;
        
        txtCurrentBalance.setText(String.format("$%.2f", totalPaid));
        txtOutstandingTuition.setText(String.format("$%.2f", outstanding));
    }

    /**
     * Populate payment history table
     */
    private void populatePaymentTable() {
        DefaultTableModel model = (DefaultTableModel) tblPaymentHistory.getModel();
        model.setRowCount(0);
        
        double runningBalance = 0;
        
        for (FinancialRecord fr : paymentHistory) {
            // Calculate running balance based on transaction type
            if (fr.getType().equalsIgnoreCase("BILLED")) {
                runningBalance += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("PAID")) {
                runningBalance -= fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("REFUND")) {
                runningBalance += fr.getAmount(); // A refund increases the amount owed/decreases net paid
            }
            
            Object[] row = new Object[6];
            row[0] = fr.getDate();
            row[1] = fr.getTransactionID();
            row[2] = fr.getType();
            row[3] = fr.getSemester();
            row[4] = String.format("$%.2f", fr.getAmount());
            row[5] = String.format("$%.2f", runningBalance); // Display Balance After Transaction
            model.addRow(row);
        }
    }

    /**
     * Get current date as string
     */
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblStudentName = new javax.swing.JLabel();
        txtStudentName = new javax.swing.JTextField();
        txtCurrentBalance = new javax.swing.JTextField();
        lblCurrentBalance = new javax.swing.JLabel();
        lblOutstandingBalance = new javax.swing.JLabel();
        txtOutstandingTuition = new javax.swing.JTextField();
        btnPayBill = new javax.swing.JButton();
        lblPaymentAmount = new javax.swing.JLabel();
        txtPaymentAmount = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPaymentHistory = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 255, 204));
        setMaximumSize(new java.awt.Dimension(600, 465));
        setMinimumSize(new java.awt.Dimension(600, 465));

        jLabel1.setText("Tuition Payment Portal");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Account Summary:");

        lblStudentName.setText("Student Name:");

        txtStudentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStudentNameActionPerformed(evt);
            }
        });

        txtCurrentBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentBalanceActionPerformed(evt);
            }
        });

        lblCurrentBalance.setText("Current Balance:");

        lblOutstandingBalance.setText("Outstanding Tuition:");

        txtOutstandingTuition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOutstandingTuitionActionPerformed(evt);
            }
        });

        btnPayBill.setText("Pay Bill");
        btnPayBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayBillActionPerformed(evt);
            }
        });

        lblPaymentAmount.setText("Payment Amount:");

        txtPaymentAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaymentAmountActionPerformed(evt);
            }
        });

        tblPaymentHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Transaction ID", "Transaction Type", "Course ID", "Amount", "Balance After Transaction"
            }
        ));
        jScrollPane1.setViewportView(tblPaymentHistory);

        jLabel8.setText("Payment History");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblOutstandingBalance)
                                        .addComponent(lblStudentName, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(lblCurrentBalance))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCurrentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtOutstandingTuition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblPaymentAmount)
                                .addGap(18, 18, 18)
                                .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPayBill))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(229, 229, 229)
                                .addComponent(jLabel8)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCurrentBalance, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCurrentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOutstandingBalance)
                            .addComponent(txtOutstandingTuition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStudentName)))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaymentAmount)
                    .addComponent(txtPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPayBill))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPaymentAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaymentAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaymentAmountActionPerformed

    private void txtStudentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStudentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentNameActionPerformed

    private void txtCurrentBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrentBalanceActionPerformed

    private void txtOutstandingTuitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOutstandingTuitionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOutstandingTuitionActionPerformed

    private void btnPayBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayBillActionPerformed
        // TODO add your handling code here:
        try {
            String paymentText = txtPaymentAmount.getText().trim();
            
            if (paymentText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a payment amount.", "Empty Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double paymentAmount = Double.parseDouble(paymentText);
            
            if (paymentAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a valid payment amount greater than $0.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double currentOutstanding = student.getTuitionBalance();
            
            // CRITICAL CHECK 1: If balance is already zero or negative (Assignment Requirement)
            if (currentOutstanding <= 0) {
                JOptionPane.showMessageDialog(this, "No balance due. Your account is up to date!", "No Balance", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // CRITICAL CHECK 2: Check if payment exceeds outstanding balance (Assignment Requirement)
            if (paymentAmount > currentOutstanding) {
                JOptionPane.showMessageDialog(this, 
                    "Payment amount ($" + String.format("%.2f", paymentAmount) + 
                    ") exceeds outstanding tuition ($" + String.format("%.2f", currentOutstanding) + ").\n\n" +
                    "Please enter an amount equal to or less than your outstanding balance.", 
                    "Payment Exceeds Balance", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- Process payment (Update persistence) ---
            String transactionId = "TXN-" + System.currentTimeMillis();
            FinancialRecord newPayment = new FinancialRecord(
                    transactionId,
                    student,
                    paymentAmount,
                    "PAID",
                    "Fall 2024", 
                    getCurrentDate()
            );

            // Add new payment record to the history list
            paymentHistory.add(newPayment);
            
            // Update student's tuition balance (Persistence update)
            student.setTuitionBalance(currentOutstanding - paymentAmount);
            
            double newBalance = student.getTuitionBalance();
            
            // Confirmation Dialog
            JOptionPane.showMessageDialog(this, 
                "Payment Successful!\n\n" +
                "Amount Paid: $" + String.format("%.2f", paymentAmount) + "\n" +
                "Remaining Balance: $" + String.format("%.2f", newBalance), 
                "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh display
            populatePaymentTable();
            refreshBalances();
            txtPaymentAmount.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPayBillActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPayBill;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCurrentBalance;
    private javax.swing.JLabel lblOutstandingBalance;
    private javax.swing.JLabel lblPaymentAmount;
    private javax.swing.JLabel lblStudentName;
    private javax.swing.JTable tblPaymentHistory;
    private javax.swing.JTextField txtCurrentBalance;
    private javax.swing.JTextField txtOutstandingTuition;
    private javax.swing.JTextField txtPaymentAmount;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration//GEN-END:variables
}
