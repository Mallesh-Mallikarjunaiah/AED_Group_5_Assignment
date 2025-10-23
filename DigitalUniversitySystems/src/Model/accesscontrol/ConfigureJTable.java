/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.accesscontrol;

/**
 *
 * @author talha
 */
public class ConfigureJTable {
        // Global lists for pre-populated data
    public static List<Person> personList = new ArrayList<>();
    public static List<Course> courseList = new ArrayList<>();
    public static List<CourseOffering> offeringList = new ArrayList<>();
    public static List<Enrollment> enrollmentList = new ArrayList<>();
    public static List<FinancialRecord> financialRecords = new ArrayList<>();
    
    public static void initialize() {
                // Create Persons
        Admin admin = new Admin("John Smith");
        Faculty faculty1 = new Faculty("Gina Montana");
        Faculty faculty2 = new Faculty("Daniel Carter");
        Student student1 = new Student("Adam Rollen");
        Student student2 = new Student("Sophia Lee");
        Registrar registrar = new Registrar("Jim Dellon");

        // Add them to person list
        Collections.addAll(personList, admin, faculty1, faculty2, student1, student2, registrar);

        // Create Courses
        Course c1 = new Course("INFO 5100", "Application Engineering & Development", 4);
        Course c2 = new Course("INFO 6105", "Data Science Engineering Methods", 4);
        Course c3 = new Course("INFO 6205", "Program Structures & Algorithms", 4);
        Collections.addAll(courseList, c1, c2, c3);

        // Create Course Offerings
        CourseOffering o1 = new CourseOffering("Fall 2025", c1, faculty1, 30);
        CourseOffering o2 = new CourseOffering("Fall 2025", c2, faculty2, 25);
        offeringList.addAll(Arrays.asList(o1, o2));

        // Enroll Students
        Enrollment e1 = new Enrollment(student1, o1);
        Enrollment e2 = new Enrollment(student2, o1);
        Enrollment e3 = new Enrollment(student1, o2);
        enrollmentList.addAll(Arrays.asList(e1, e2, e3));

        // Assign Grades
        e1.setFinalGrade("A");
        e2.setFinalGrade("B+");
        e3.setFinalGrade("A-");

        // Financial Records
        FinancialRecord f1 = new FinancialRecord(student1, o1, 2000.00, true);
        FinancialRecord f2 = new FinancialRecord(student2, o1, 2000.00, false);
        financialRecords.addAll(Arrays.asList(f1, f2));

        System.out.println("ConfigureJTable initialized successfully with sample data.");
    }

}

