# JAVA-PROJECT-VITYARTHI
# Campus Course & Records Manager (CCRM)

A console-based Java application for managing students, courses,
enrollments, grades, backups, and reports for an educational institute.

## Features
- Student Management (Add, List, Search, Delete)
- Course Management (Add, List, Delete)
- Enrollment & Grade Tracking
- Transcript printing
- CSV Data Persistence
- Timestamped Backups
- GPA Distribution Reports

## Technologies Used
- Java (JDK 17+)
- File I/O (CSV)
- Collections Framework

## Installation & Setup

1. Clone the repository: https://github.com/YOUR-USERNAME/JAVA-PROJECT-VITYARTHI.git
cd JAVA-PROJECT-VITYARTHI

2. Compile: javac src/CCRM.java

3. Run: java -cp src CCRM

## Testing Instructions
- On first run, the `data/` folder is auto-created.
- Add a student → check `data/students.csv`
- Enroll a student → check `data/enrollment.csv`
- Create backup → verify `data/backup_YYYYMMDD_HHmmss/`
- Exit (option 6) → data is saved automatically.

## Notes
- Requires JDK 17 or higher.
- Pure command-line application — no GUI needed.