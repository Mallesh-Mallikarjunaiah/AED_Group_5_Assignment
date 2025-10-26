/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author jayan
 */
public class FinancialRecord {
    private String transactionID;
    private Student student;
    private double amount;
    private String type; // E.g., "BILLED", "PAID", "REFUND"
    private String semester;
    private String date;
    
    public FinancialRecord() {}


    public FinancialRecord(String transactionID, Student student, double amount, String type, String semester, String date) {
        this.transactionID = transactionID;
        this.student = student;
        this.amount = amount;
        this.type = type;
        this.semester = semester;
        this.date = date;
    }
    public String getTransactionID() {
        return transactionID;
    }

    public String getDate() {
        return date;
    }
     public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getters (essential for FinancialReconciliationJPanel's JTable and summaries)
    public Student getStudent() { return student; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getSemester() { return semester; }
    public int getStudentId() { return student.getPerson().getUNID(); }
    public String getStudentName() { return student.getPerson().getName(); }
}
