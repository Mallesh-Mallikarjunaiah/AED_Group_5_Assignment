package Model.accesscontrol;

import Model.*;
import Model.User.UserAccount;
import Model.User.UserAccountDirectory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigureJTable {

    // --- CENTRAL REPOSITORIES (Public Static Fields) ---
    public static UserAccountDirectory directory; 
    public static List<Course> courseList = new ArrayList<>();
    public static List<CourseOffering> courseOfferingList = new ArrayList<>();
    public static List<Enrollment> enrollmentList = new ArrayList<>();
    public static List<FinancialRecord> financialRecordList = new ArrayList<>();
    
    /**
     * Initializes all required model data and populates the UserAccountDirectory.
     */
    public static void initializeData(UserAccountDirectory userDirectory) {
        
        ConfigureJTable.directory = userDirectory;
        courseList.clear();
        courseOfferingList.clear();
        enrollmentList.clear();
        financialRecordList.clear();
        
        // --- 2. Required Departments (from Model.Department enum) ---
        Department deptIS = Department.IS;
        Department deptCS = Department.CS;
        Department deptAI = Department.AI;
        Department deptDS = Department.DS; // Using all departments for distribution

        // --- 3. Create Core Persons and User Accounts (ADMIN, REGISTRAR, FACULTY) ---
        
        // 1 Admin, 1 Registrar (Total: 2)
        Person pAdmin = new Person("Chris Evans", "c.evans@uni.edu", "800-1111");
        Person pRegistrar = new Person("Jennifer Lee", "j.lee@uni.edu", "800-2222");

        directory.newUserAccount("admin", "pass", "admin", "pass", ProfileEnum.ADMIN, null, pAdmin.getEmail());
        directory.newUserAccount("registrar", "pass", "registrar", "pass", ProfileEnum.REGISTRAR, null, pRegistrar.getEmail());
        
        // Faculty (10 minimum required)
        
        // Core linking faculty (F1-F3)
        Person pF1 = new Person("Dr. A. Smith", "smith@uni.edu", "111-1001");
        Person pF2 = new Person("Dr. B. Jones", "jones@uni.edu", "111-1002");
        Person pF3 = new Person("Dr. C. Garcia", "garcia@uni.edu", "111-1003");
        
        // Additional mock faculty for 10 minimum (F4-F10)
        Person pF4 = new Person("Dr. D. Wang", "wang@uni.edu", "111-1004");
        Person pF5 = new Person("Dr. E. Khan", "khan@uni.edu", "111-1005");
        Person pF6 = new Person("Dr. F. Chen", "fchen@uni.edu", "111-1006");
        Person pF7 = new Person("Dr. G. Patel", "gpatel@uni.edu", "111-1007");
        Person pF8 = new Person("Dr. H. Lopez", "hlopez@uni.edu", "111-1008");
        Person pF9 = new Person("Dr. I. Rossi", "irossi@uni.edu", "111-1009");
        Person pF10 = new Person("Dr. J. Kim", "jkim@uni.edu", "111-1010");

        // Create accounts for active Faculty (Total: 10)
        Faculty f1 = (Faculty) directory.newUserAccount("asmith", "pass", "asmith", "pass", ProfileEnum.FACULTY, deptIS, pF1.getEmail()).getProfile();
        Faculty f2 = (Faculty) directory.newUserAccount("bjones", "pass", "bjones", "pass", ProfileEnum.FACULTY, deptCS, pF2.getEmail()).getProfile();
        Faculty f3 = (Faculty) directory.newUserAccount("cgarcia", "pass", "cgarcia", "pass", ProfileEnum.FACULTY, deptAI, pF3.getEmail()).getProfile();
        directory.newUserAccount("dwang", "pass", "dwang", "pass", ProfileEnum.FACULTY, deptDS, pF4.getEmail());
        directory.newUserAccount("ekhan", "pass", "ekhan", "pass", ProfileEnum.FACULTY, deptIS, pF5.getEmail());
        directory.newUserAccount("fchen", "pass", "fchen", "pass", ProfileEnum.FACULTY, deptCS, pF6.getEmail());
        directory.newUserAccount("gpatel", "pass", "gpatel", "pass", ProfileEnum.FACULTY, deptAI, pF7.getEmail());
        directory.newUserAccount("hlopez", "pass", "hlopez", "pass", ProfileEnum.FACULTY, deptDS, pF8.getEmail());
        directory.newUserAccount("irossi", "pass", "irossi", "pass", ProfileEnum.FACULTY, deptIS, pF9.getEmail());
        directory.newUserAccount("jkim", "pass", "jkim", "pass", ProfileEnum.FACULTY, deptCS, pF10.getEmail());
        
        // --- 4. Create Students (10 minimum required) ---
        
        // Core Students for linking (S1-S5)
        Person pS1 = new Person("Amelia Rivas", "arivas@uni.edu", "222-1001");
        Person pS2 = new Person("Ben Carter", "bcarte@uni.edu", "222-1002");
        Person pS3 = new Person("David Wilson", "dwilso@uni.edu", "222-1003");
        Person pS4 = new Person("Eve Li", "eli@uni.edu", "222-1004");
        Person pS5 = new Person("Frank Brown", "fbrown@uni.edu", "222-1005");
        
        // Additional mock students (S6-S10, plus S11-S18 for 30+ total)
        Person pS6 = new Person("Grace Hall", "ghall@uni.edu", "222-1006");
        Person pS7 = new Person("Henry King", "hking@uni.edu", "222-1007");
        Person pS8 = new Person("Ivy Chen", "ichen@uni.edu", "222-1008");
        Person pS9 = new Person("Jake Evans", "jevans@uni.edu", "222-1009");
        Person pS10 = new Person("Kelly White", "kwhite@uni.edu", "222-1010");
        Person pS11 = new Person("Leo Miller", "lmiller@uni.edu", "222-1011");
        Person pS12 = new Person("Mia Perez", "mperez@uni.edu", "222-1012");
        Person pS13 = new Person("Nate Reed", "nreed@uni.edu", "222-1013");
        Person pS14 = new Person("Omar Cruz", "ocruz@uni.edu", "222-1014");
        Person pS15 = new Person("Pamela G", "pg@uni.edu", "222-1015");
        Person pS16 = new Person("Quentin Z", "qz@uni.edu", "222-1016");
        Person pS17 = new Person("Renee Y.", "ry@uni.edu", "222-1017");
        Person pS18 = new Person("Sam F.", "sf@uni.edu", "222-1018");

        // Create accounts for active Students (Total: 18 students)
        Student s1 = (Student) directory.newUserAccount("amelia", "pass", "amelia", "pass", ProfileEnum.STUDENT, deptIS, pS1.getEmail()).getProfile();
        Student s2 = (Student) directory.newUserAccount("ben", "pass", "ben", "pass", ProfileEnum.STUDENT, deptCS, pS2.getEmail()).getProfile();
        Student s3 = (Student) directory.newUserAccount("david", "pass", "david", "pass", ProfileEnum.STUDENT, deptAI, pS3.getEmail()).getProfile();
        Student s4 = (Student) directory.newUserAccount("eve", "pass", "eve", "pass", ProfileEnum.STUDENT, deptIS, pS4.getEmail()).getProfile();
        Student s5 = (Student) directory.newUserAccount("frank", "pass", "frank", "pass", ProfileEnum.STUDENT, deptCS, pS5.getEmail()).getProfile();
        Student s6 = (Student) directory.newUserAccount("grace", "pass", "grace", "pass", ProfileEnum.STUDENT, deptDS, pS6.getEmail()).getProfile();
        Student s7 = (Student) directory.newUserAccount("henry", "pass", "henry", "pass", ProfileEnum.STUDENT, deptAI, pS7.getEmail()).getProfile();
        Student s8 = (Student) directory.newUserAccount("ivy", "pass", "ivy", "pass", ProfileEnum.STUDENT, deptCS, pS8.getEmail()).getProfile();
        Student s9 = (Student) directory.newUserAccount("jake", "pass", "jake", "pass", ProfileEnum.STUDENT, deptIS, pS9.getEmail()).getProfile();
        Student s10 = (Student) directory.newUserAccount("kelly", "pass", "kelly", "pass", ProfileEnum.STUDENT, deptDS, pS10.getEmail()).getProfile();
        Student s11 = (Student) directory.newUserAccount("leo", "pass", "leo", "pass", ProfileEnum.STUDENT, deptAI, pS11.getEmail()).getProfile();
        Student s12 = (Student) directory.newUserAccount("mia", "pass", "mia", "pass", ProfileEnum.STUDENT, deptCS, pS12.getEmail()).getProfile();
        directory.newUserAccount("nate", "pass", "nate", "pass", ProfileEnum.STUDENT, deptIS, pS13.getEmail());
        directory.newUserAccount("omar", "pass", "omar", "pass", ProfileEnum.STUDENT, deptDS, pS14.getEmail());
        directory.newUserAccount("pamela", "pass", "pamela", "pass", ProfileEnum.STUDENT, deptAI, pS15.getEmail());
        directory.newUserAccount("quentin", "pass", "quentin", "pass", ProfileEnum.STUDENT, deptCS, pS16.getEmail());
        directory.newUserAccount("renee", "pass", "renee", "pass", ProfileEnum.STUDENT, deptIS, pS17.getEmail());
        directory.newUserAccount("sam", "pass", "sam", "pass", ProfileEnum.STUDENT, deptAI, pS18.getEmail());

        
        // --- 5. Create Courses (5 minimum) and Course Offerings (Semester link) ---
        
        Course c1 = new Course("INFO 5100", "Application Engineering", 4); 
        Course c2 = new Course("CS 5010", "Advanced Algorithms", 4);       
        Course c3 = new Course("AI 6100", "Machine Learning", 4);         
        Course c4 = new Course("INFO 6205", "Data Mining", 3);            
        Course c5 = new Course("CS 6500", "Network Security", 4);         
        Collections.addAll(courseList, c1, c2, c3, c4, c5);
        
        // Course Offerings (Fall 2025 Semester link)
        CourseOffering o1 = new CourseOffering(c1, "Fall 2025", f1, 40, "MW 2:00 PM"); // Smith, INFO 5100
        CourseOffering o2 = new CourseOffering(c2, "Fall 2025", f2, 30, "TTh 10:00 AM"); // Jones, CS 5010
        CourseOffering o3 = new CourseOffering(c3, "Fall 2025", f3, 20, "WF 4:00 PM"); // Garcia, AI 6100
        CourseOffering o4 = new CourseOffering(c4, "Spring 2026", f1, 35, "TTh 2:00 PM"); // Smith, INFO 6205
        CourseOffering o5 = new CourseOffering(c5, "Spring 2026", f2, 25, "MW 6:00 PM"); // Jones, CS 6500
        Collections.addAll(courseOfferingList, o1, o2, o3, o4, o5);
        
        // --- 6. Create Enrollments (Interlinking data) ---
        
        // s1 (Amelia): Full enrollment (8 credits), paid
        Enrollment e1_o1 = new Enrollment(s1, o1); e1_o1.setGrade("A"); 
        Enrollment e2_o2 = new Enrollment(s1, o2); 
        s1.setCreditsCompleted(s1.getCreditsCompleted() + 4); 
        o1.incrementEnrolledCount();
        o2.incrementEnrolledCount();
        
        // s2 (Ben): Partial enrollment (4 credits), paid in full
        Enrollment e3_o1 = new Enrollment(s2, o1); 
        o1.incrementEnrolledCount();
        
        // s3 (David): Dropped CS 5010 (Inactive)
        Enrollment e4_o2 = new Enrollment(s3, o2); e4_o2.setActive(false);
        
        Collections.addAll(enrollmentList, e1_o1, e2_o2, e3_o1, e4_o2);
        
        // --- 7. Create Financial Records (For Reconciliation/Analytics) ---
        
        // s1 (Amelia): Billed 4000, Paid 3000 -> Balance 1000
        financialRecordList.add(new FinancialRecord("TRX100", s1, 4000.00, "BILLED", "Fall 2025", "2025-08-01"));
        financialRecordList.add(new FinancialRecord("TRX101", s1, 3000.00, "PAID", "Fall 2025", "2025-08-15"));
        s1.setTuitionBalance(1000.00); 

        // s2 (Ben): Billed 2000, Paid 2000 -> Balance 0
        financialRecordList.add(new FinancialRecord("TRX102", s2, 2000.00, "BILLED", "Fall 2025", "2025-08-01"));
        financialRecordList.add(new FinancialRecord("TRX103", s2, 2000.00, "PAID", "Fall 2025", "2025-08-20"));
        s2.setTuitionBalance(0.00); 

        System.out.println("ConfigureJTable initialized successfully with linked data.");
    }
}