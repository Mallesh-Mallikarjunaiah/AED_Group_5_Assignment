package Model;

import Model.User.UserAccount;
import Model.User.UserAccountDirectory;
import Model.accesscontrol.DataValidator; // Assume this is available
import java.util.List;
import java.util.stream.Collectors;

/**
 * PersonService: Manages CRUD operations and validation for all Person Profiles 
 * (Student/Faculty/Registrar) using the central UserAccountDirectory.
 * This class ensures data consistency by operating on ConfigureJTable's static lists.
 */
public class PersonService {
    
    private final UserAccountDirectory accountDirectory;

    public PersonService(UserAccountDirectory accountDirectory) {
        this.accountDirectory = accountDirectory;
    }

    /**
     * Finds accounts matching the given role (Student or Faculty) for display.
     */
    public List<UserAccount> getStudentFacultyAccounts() {
        return accountDirectory.getUserAccountList().stream()
                .filter(ua -> ua.getProfile() instanceof Student || ua.getProfile() instanceof Faculty)
                .collect(Collectors.toList());
    }

    /**
     * Registers a new person and checks for duplicates (Admin Responsibility).
     * @return The newly created UserAccount, or null if validation fails.
     */
    public UserAccount registerNewPerson(String name, String contactNum, String un, String pw, ProfileEnum profile, Department department, String email) {
        
        // Check duplicates (Simplified: Assume DataValidator.isValidEmail exists)
        boolean isDuplicate = accountDirectory.getUserAccountList().stream()
                .anyMatch(ua -> ua.getUsername().equalsIgnoreCase(un) || ua.getProfile().getPerson().getEmail().equalsIgnoreCase(email));

        if (isDuplicate) {
            return null;
        }
        
        // Create and persist the new account (UNID auto-gen in Person)
        return accountDirectory.newUserAccount(name, contactNum, un, pw, profile, department, email);
    }
    
    /**
     * Updates an existing Person's non-credential profile information.
     * CRITICAL: This ONLY updates Person/Profile data, leaving Username/Password untouched.
     */
    public boolean updatePersonProfile(UserAccount account, String email, String contact, Department dept) {
        if (account == null) return false;
        
        Person p = account.getProfile().getPerson();
        p.setEmail(email);
        p.setContactNumber(contact);
        
        // Update role-specific fields
        if (account.getProfile() instanceof Faculty faculty) {
            faculty.setDepartment(dept);
        } else if (account.getProfile() instanceof Student student) {
            student.setDepartment(dept);
            // Assuming academic status is updated via a separate service/panel
        }
        
        // NOTE: Username and Password ARE NOT modified here. This fixes the issue.
        return true;
    }
    
    // You would need to add similar find methods as needed by your Admin/Faculty search panels
}