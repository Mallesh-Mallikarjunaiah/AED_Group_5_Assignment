# University Management System (UMS)
________________________________________
## 2. Team Information
Name	NUID	Responsibilities
[Gagana Ananda]	[002561357]	Admin Module, Database Design, System Architecture
[Syed Hameed Uddin]	[002569695]	Faculty Usecase, Testing & Documentation
[Mallesh Mallikarjunaiah]	[002593065]	Student Module, Payment Integration
[Jayanth Muthaluri]	[002593185]	Registrar Usecase, UI/UX Design 
		
________________________________________
## 3. Project Overview
### Purpose
The University Management System (UMS) is a comprehensive Java Swing-based application designed to streamline academic and administrative operations for a university. The system facilitates course management, student enrollment, grading, financial transactions, and institutional reporting.
### Objectives
•	Provide role-based access control for Admin, Faculty, Student, and Registrar users
•	Automate course registration and enrollment processes
•	Enable grade management and transcript generation
•	Track tuition payments and financial records
•	Generate institutional reports and analytics
### Key Features
•	Authentication & Authorization: Secure login system with role-based access control
•	Admin Module: User management, person registration, analytics dashboard
•	Faculty Module: Course management, student grading, performance reporting, tuition insights
•	Student Module: Course registration (8-credit limit), transcript review, graduation audit, tuition payment
•	Registrar Module: Course offering management, student registration oversight, financial reconciliation
•	Grade Calculator: Automatic GPA computation with academic standing determination
•	Payment System: Tuition billing, payment processing, refund handling
________________________________________
## 4. Installation & Setup Instructions
### Prerequisites
•	Java Development Kit (JDK): Version 8 or higher
•	IDE: NetBeans 12.0+ (recommended) 
•	Operating System: Windows, macOS, or Linux
•	Libraries: Java Swing (built-in), javax.swing.table (built-in)
### Setup Instructions
1.	Clone the Repository
2.	git clone https://github.com/your-repo/university-management-system.git
3.	cd university-management-system
4.	Open in NetBeans
o	Launch NetBeans IDE
o	File → Open Project
o	Navigate to the cloned directory
o	Select the project folder
5.	Build the Project
o	Right-click on the project name
o	Select "Clean and Build"
o	Wait for dependencies to resolve
6.	Run the Application
o	Right-click on MainJFrame.java
o	Select "Run File"
o	The login screen will appear
### Default Login Credentials
Role	Username	Password
Admin	admin	pass
Faculty	faculty	pass
Student	student	pass
Registrar	registrar	pass
________________________________________
## 5. Authentication & Access Control
### Authentication Process
1.	Login
o	User enters username and password
o	System validates credentials against UserAccountDirectory
o	Upon successful authentication, user is redirected to role-specific dashboard
o	Failed login displays error message
2.	Authorization
o	Each user account is associated with a Profile (Admin, Faculty, Student, Registrar)
o	System checks user role before granting access to features
o	CardLayout switches between role-specific panels
3.	Logout
o	User clicks "Logout" button
o	Session is cleared
o	System returns to login screen
### Access Control Rules
Feature	Admin	Faculty	Student	Registrar
User Account Management	✓	✗	✗	✗
Course Management	✗	✓	✗	✓
Student Grading	✗	✓	✗	✗
Course Registration	✗	✗	✓	✓
Transcript View	✗	✓	✓*	✓
Tuition Payment	✗	✗	✓	✗
Financial Reconciliation	✓	✗	✗	✓
Analytics Dashboard	✓	✗	✗	✗
*Student can view transcript only if tuition balance is $0
________________________________________
## 6. Features Implemented
### Admin Module
Responsible Team Member: [Gagana Ananda]
•	User Account Management 
o	Create, view, edit, and deactivate user accounts
o	Assign roles (Admin, Faculty, Student, Registrar)
•	Person Registration 
o	Register new individuals (students, faculty, staff)
o	Capture demographic information
•	Analytics Dashboard 
o	View enrollment statistics by department
o	Track financial metrics
o	Generate institutional reports
### Faculty Module
Responsible Team Member: [Syed Hameed Uddin]
•	Course Management
o	View assigned courses
o	Update course details (title, description, schedule, capacity)
o	Upload course syllabus
o	Open/close enrollment
•	Student Grading
o	View enrolled students per course
o	Enter assignment, midterm, and final scores
o	Compute final letter grades automatically (30% assignments, 30% midterm, 40% final)
o	Rank students by performance
o	Calculate class average GPA
•	Performance Reporting
o	Generate course-level reports
o	View average grade, grade distribution, enrollment count
o	Filter reports by semester
•	Tuition Insights
o	View total tuition collected from enrolled students
o	Track enrollment numbers by course
### Student Module
Responsible Team Member: [Mallesh Mallikarjunaiah]
•	Course Registration
o	Search courses by 3 methods: Course ID, Faculty Name, Department
o	View course offerings with schedule and capacity
o	Enroll in courses (max 8 credits per semester)
o	Drop courses with automatic refund
•	Transcript Review
o	View academic history by semester
o	Display grades, term GPA, overall GPA
o	Show academic standing (Good Standing, Academic Warning, Academic Probation)
o	Access blocked if tuition balance > $0
•	Graduation Audit
o	Track progress toward 32-credit MSIS degree requirement
o	Verify completion of mandatory INFO 5100 core course
o	Display graduation eligibility status
•	Tuition Payment
o	View outstanding balance
o	Make payments
o	View payment history
o	Automatic refunds for dropped courses
### Registrar Module
Responsible Team Member: [Jayanth  Muthaluri]
•	Course Offering Management
o	Create course offerings for each semester
o	Assign faculty to courses
o	Set enrollment capacity and schedules
•	Student Registration
o	View student enrollments
o	Process enrollment/drop requests
o	Override enrollment restrictions when necessary
•	Financial Reconciliation
o	Track tuition billing and payments
o	Generate financial reports by semester
o	View outstanding balances
•	Institutional Reports
o	Generate enrollment reports by department
o	View course capacity utilization
o	Export data for analysis
________________________________________
## 7. Usage Instructions
### Admin Workflow
1.	Login as Admin
o	Username: admin, Password: pass
2.	Create a New Faculty Member
o	Navigate to "User Account Management"
o	Click "Create New Account"
o	Enter: Name, Email, Contact, Department
o	Select Role: "Faculty"
o	Click "Save"
3.	View Analytics
o	Navigate to "Analytics Dashboard"
o	Select department filter
o	View enrollment statistics and financial metrics
### Faculty Workflow
1.	Login as Faculty
o	Username: faculty, Password: pass
2.	Enter Student Grades
o	Navigate to "Student Management"
o	Select course from dropdown
o	Enter scores in table (Assignment, Midterm, Final)
o	Click "Compute Final Grade"
o	Review calculated grades
o	Click "Save Grades"
3.	Generate Performance Report
o	Navigate to "Performance and Reporting"
o	Select semester from dropdown
o	Select course
o	View average grade, grade distribution, enrollment count
### Student Workflow
1.	Login as Student
o	Username: student, Password: pass
2.	Register for Courses
o	Navigate to "Course Registration"
o	Select semester
o	Search for courses using filter (e.g., "CS" in Department filter)
o	Click "Search"
o	Select course from table
o	Click "Enroll"
o	Confirm tuition charge
3.	View Transcript
o	Pay outstanding tuition balance first (if any)
o	Navigate to "Transcript"
o	Select semester filter (or "All Semesters")
o	Click "Filter"
o	View grades, term GPA, overall GPA, academic standing
4.	Check Graduation Status
o	Navigate to "Graduation Audit"
o	Review total credits earned (32 required)
o	Verify INFO 5100 completion status
o	Check eligibility message
### Registrar Workflow
1.	Login as Registrar
o	Username: registrar, Password: pass
2.	Create Course Offering
o	Navigate to "Course Offering"
o	Click "New Offering"
o	Select course, semester, faculty
o	Set capacity and schedule
o	Click "Save"
3.	Process Student Registration
o	Navigate to "Student Registration"
o	Select student and course
o	Click "Enroll" or "Drop"
o	System updates enrollment records
________________________________________
## 8. Testing Guide
### Unit Testing
Test Case 1: User Authentication
Objective: Verify login functionality
•	Steps: 
1.	Launch application
2.	Enter username: "admin", password: "pass"
3.	Click "Login"
•	Expected Result: Redirected to Admin Dashboard
•	Status: ✓ Pass
Test Case 2: 8-Credit Enrollment Limit
Objective: Ensure students cannot exceed 8 credits per semester
•	Steps: 
1.	Login as student
2.	Enroll in two 4-credit courses (total: 8 credits)
3.	Attempt to enroll in another 4-credit course
•	Expected Result: Error message "Cannot exceed 8 credit hours"
•	Status: ✓ Pass
Test Case 3: GPA Calculation
Objective: Verify GPA is calculated correctly
•	Steps: 
1.	Login as faculty
2.	Enter grades: Assignment=90, Midterm=85, Final=88
3.	Click "Compute Final Grade"
•	Expected Result: 
o	Total = (90×0.3) + (85×0.3) + (88×0.4) = 87.7
o	Grade = B+
•	Status: ✓ Pass
Test Case 4: Graduation Eligibility
Objective: Check graduation audit logic
•	Steps: 
1.	Login as student with 32 credits and INFO 5100 completed
2.	Navigate to "Graduation Audit"
•	Expected Result: "Congratulations! You are eligible to graduate!"
•	Status: ✓ Pass
Test Case 5: Tuition Refund on Drop
Objective: Verify refund when dropping a course
•	Steps: 
1.	Login as student
2.	Note current balance: $6,000
3.	Drop a 4-credit course
•	Expected Result: Balance reduced by $6,000 (4 × $1,500)
•	Status: ✓ Pass
### Authorization Testing
Test Case 6: Role-Based Access Control
Objective: Ensure students cannot access admin features
•	Steps: 
1.	Login as student
2.	Attempt to navigate to admin panel (via URL or code manipulation)
•	Expected Result: Access denied or panel not visible
•	Status: ✓ Pass
### Integration Testing
Test Case 7: End-to-End Enrollment Flow
Objective: Test complete enrollment process
•	Steps: 
1.	Registrar creates course offering
2.	Student enrolls in course
3.	Faculty enters grades
4.	Student views transcript
•	Expected Result: All steps complete successfully with data consistency
•	Status: ✓ Pass
________________________________________
## 9. Challenges & Solutions
Challenge 1: Managing Mock Data Across Panels
Problem: Multiple panels needed access to shared enrollment and course data, but no centralized database existed.
Solution: Implemented mock data initialization within each panel's constructor. For production, this would be replaced with a centralized DataStore/Service layer that maintains singleton instances of data collections.
Challenge 2: 8-Credit Hour Limit Enforcement
Problem: Students could potentially enroll in multiple courses across different semesters, making credit limit tracking complex.
Solution: Implemented semester-specific credit calculation that only counts active enrollments for the selected semester. The updateCreditSummary() method filters enrollments by semester before calculating total credits.
Challenge 3: GPA Calculation Logic
Problem: Academic standing determination required tracking both term GPA and overall GPA with complex conditional logic.
Solution: Created helper methods calculateGPAForGrade() and determineAcademicStanding() that encapsulate grade-to-GPA conversion and standing determination. Rules clearly documented:
•	Academic Probation: Overall GPA < 3.0
•	Academic Warning: Term GPA < 3.0 (even if overall ≥ 3.0)
•	Good Standing: Both Term and Overall GPA ≥ 3.0
Challenge 4: Transcript Access Control
Problem: Students should not view transcripts if they have outstanding tuition balance.
Solution: Added tuition balance check in btnTranscriptActionPerformed() method that blocks access and displays balance information with option to navigate to payment page.
Challenge 5: Search Functionality with Multiple Filters
Problem: Implementing 3 different search methods (Course ID, Faculty Name, Department) in one interface.
Solution: Used a dropdown filter selector combined with a single search text field. The performSearch() method uses a switch statement to apply the appropriate search logic based on selected filter type.
Challenge 6: Refund Handling for Dropped Courses
Problem: When students drop courses, the system needs to automatically refund tuition and update multiple records.
Solution: Implemented atomic drop operation that:
1.	Sets enrollment status to inactive
2.	Decrements course capacity
3.	Calculates and applies refund
4.	Updates all relevant tables and displays
________________________________________
## 10. Future Enhancements
### Short-Term Enhancements
1.	Database Integration
o	Replace mock data with MySQL/PostgreSQL database
o	Implement DAO (Data Access Object) pattern
o	Add connection pooling for performance
2.	Email Notifications
o	Send enrollment confirmations
o	Notify students of grade postings
o	Alert for payment due dates
3.	Report Export Functionality
o	Export transcripts to PDF
o	Generate Excel reports for analytics
o	Print course rosters
4.	Advanced Search
o	Add combined filters (e.g., Department AND semester)
o	Implement fuzzy search for names
o	Add sorting capabilities to all tables
### Long-Term Enhancements
5.	Online Payment Gateway Integration
o	Integrate Stripe/PayPal APIs
o	Support multiple payment methods
o	Generate digital receipts
6.	Course Prerequisites System
o	Define course dependencies
o	Prevent enrollment if prerequisites not met
o	Show prerequisite tree
7.	Waitlist Management
o	Allow students to join waitlist for full courses
o	Auto-enroll when spots open
o	Send notifications to waitlisted students
8.	Mobile Application
o	Develop iOS/Android companion app
o	Push notifications for important events
o	Mobile-friendly course registration
9.	Advanced Analytics
o	Predictive analytics for enrollment trends
o	Student success prediction models
o	Faculty performance dashboards
10.	Calendar Integration
o	Sync course schedules with Google Calendar/Outlook
o	Show academic calendar with deadlines
o	Set reminders for assignments and exams
11.	Document Management System
o	Store and retrieve syllabi, assignments, grades
o	Version control for documents
o	Student submission portal
12.	Accessibility Improvements
o	Screen reader compatibility
o	High contrast themes
o	Keyboard navigation shortcuts
________________________________________
## 11. Contribution Breakdown
[Gagana Ananda] - Admin Module & System Architecture
•	Implemented complete Admin Dashboard
•	Designed and implemented User Account Management system
•	Created Person Registration functionality
•	Developed Analytics Dashboard with department-wise reporting
•	Implemented UserAccount, UserAccountDirectory classes
•	Set up project structure and package organization
•	Wrote project overview and objectives
•	Created system architecture diagrams
•	Documented authentication & authorization flow
________________________________________
[Syed Hameed Uddin] - Faculty Module & Documentation
•	Implemented complete Faculty Dashboard
•	Created Course Management panel with syllabus upload
•	Developed Student Grading system with automatic grade calculation
•	Built Performance Report generation with semester filtering
•	Implemented Tuition Insights visualization
•	Documented faculty features and workflows
•	Created user guide for faculty members
•	Wrote grading system documentation
•	Created comprehensive README file
________________________________________
[Mallesh Muthaluri] - Student Module & Payment System
•	Implemented Course Registration with 3 search methods
•	Created Transcript panel with GPA calculations
•	Developed Graduation Audit with requirement checking
•	Built Tuition Payment system with transaction history
•	Implemented 8-credit hour limit enforcement
•	Wrote student module documentation
•	Created payment system workflow documentation
•	Documented graduation requirements
•	Tested course registration flow
•	Verified GPA calculations
•	Tested payment processing and refunds
________________________________________
[Jayanth Muthaluri] - Registrar Module & UI/UX Design 
•	Implemented Registrar Dashboard
•	Created Course Offering Management
•	Developed Student Registration oversight
•	Built Financial Reconciliation reports
•	Implemented Institutional Reports
•	Wrote usage instructions for all roles
•	Created testing guide with test cases
•	Designed consistent color scheme and layout
•	Created intuitive navigation flow
•	Ensured responsive panel layouts
### Shared Responsibilities (All Team Members)
Model Classes:
•	Collaborative design of Person, Profile, Student, Faculty, Admin, Registrar
•	Implementation of Course, CourseOffering, Enrollment, FinancialRecord
•	Created Department and ProfileEnum enums
Helper Classes:
•	GradeCalculator - Grade computation utilities
•	DataValidator - Input validation methods
•	ProfileManagementDialog - Profile editing functionality
Bug Fixes & Code Review:
•	Each member reviewed others' code
•	Collaborative debugging sessions
•	Pair programming for complex features
•	Performed integration testing across all modules
•	Conducted user acceptance testing
