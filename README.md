# JAVA-PROJECT-VITYARTHI
# Campus Course & Records Manager (CCRM)

Campus Course & Records Manager (CCRM) is a console-based Java application for managing students, courses, enrollments, grades, backups, and reports for an educational institute. It provides a simple CLI interface to perform common academic management tasks.

## Features
- Student Management : Add, list, search, update, and delete student records.
- Course Management : Add, list, search, update, and delete courses.
- Enrollment & Grades :
  - Enroll or unenroll students in courses.
  - Record grades for enrolled courses.
  - Print transcripts for students.
- Data Persistence : - All data is saved to CSV files and automatically loaded on startup.
                     - Enrollment data is maintained separately for integrity.
- Backup : Easily create timestamped backups of all data files.
- Reporting : Generate reports, such as GPA distribution among students.

## Technologies Used
- Java (JDK 17+)
- File I/O (CSV)
- Collections Framework

## Installation & Setup

1. Clone the repository:
https://github.com/YOUR-USERNAME/JAVA-PROJECT-VITYARTHI.git
cd JAVA-PROJECT-VITYARTHI

2. Compile:
javac src/CCRM.java

3. Run:
java -cp src CCRM

## Testing Instructions
- On first run, the `data/` folder is auto-created.
- Add a student → check `data/students.csv`
- Enroll a student → check `data/enrollment.csv`
- Create backup → verify `data/backup_YYYYMMDD_HHmmss/`
- Exit (option 6) → data is saved automatically.

## Data Model Overview
Student: ID, Registration No., Name, Email, Enrolled Courses
Course: Code, Title, Credits, Instructor, Semester
Enrollment: Links Student and Course, includes grade
Instructor: ID, Name, Email (sample instructor provided)

## Extensibility
The design uses interfaces (Persistable, Searchable) and custom exceptions for easier future expansion.
Data is separated by logical entities and backed in CSV for portability.

## Notes
- Requires JDK 17 or higher.
- Pure command-line application — no GUI needed.
