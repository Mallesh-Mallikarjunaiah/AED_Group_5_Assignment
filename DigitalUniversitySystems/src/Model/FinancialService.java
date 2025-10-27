/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.User.UserAccount;
import Model.accesscontrol.ConfigureJTable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author jayan
 */
public class FinancialService {
    
    /**
     * Retrieves all financial records for a given semester.
     */
    public List<FinancialRecord> getRecordsBySemester(String semester) {
        return ConfigureJTable.financialRecordList.stream()
                .filter(fr -> fr.getSemester().equals(semester))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total tuition collected and billed for a semester.
     * @param semester The semester to filter by.
     * @return A map containing "COLLECTED" and "BILLED" sums.
     */
    public Map<String, Double> calculateTotalRevenue(String semester) {
        double collected = 0.0;
        double billed = 0.0;
        
        for (FinancialRecord fr : getRecordsBySemester(semester)) {
            if (fr.getType().equalsIgnoreCase("PAID")) {
                collected += fr.getAmount();
            } else if (fr.getType().equalsIgnoreCase("BILLED")) {
                billed += fr.getAmount();
            }
        }
        
        Map<String, Double> summary = new HashMap<>();
        summary.put("COLLECTED", collected);
        summary.put("BILLED", billed);
        summary.put("UNPAID", billed - collected);
        return summary;
    }

    /**
     * Calculates revenue breakdown by department.
     * Maps Dept.toString() to total PAID amount.
     */
    public Map<String, Double> calculateDepartmentRevenue(String semester) {
        Map<String, Double> deptRevenue = new HashMap<>();
        
        for (FinancialRecord fr : getRecordsBySemester(semester)) {
            if (fr.getType().equalsIgnoreCase("PAID")) {
                
                // --- CRITICAL FIX START: Get Student Object and Department ---
                // 1. Find the UserAccount/Student based on the UNID in the record
                UserAccount ua = ConfigureJTable.directory.findUserAccount(String.valueOf(fr.getStudent().getPerson().getUNID()));
                
                if (ua != null && ua.getProfile() instanceof Student student) {
                    // 2. Safely get the Department name
                    String deptName = student.getDepartment().toString(); 
                    
                    // 3. Aggregate revenue
                    deptRevenue.put(deptName, deptRevenue.getOrDefault(deptName, 0.0) + fr.getAmount());
                }
                // --- CRITICAL FIX END ---
            }
        }
        return deptRevenue;
    }
}



