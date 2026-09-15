import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CCRM {
    static final String DATA_DIR = "data/";
    static List<Student> students = new ArrayList<>();
    static List<Course> courses = new ArrayList<>();
    static List<Enrollment> enrollments = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new File(DATA_DIR).mkdirs();
        loadData();
        System.out.println("=== Campus Course & Records Manager ===");
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollment & Grades");
            System.out.println("4. Backup Data");
            System.out.println("5. Reports");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = readInt();
            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageCourses(); break;
                case 3: manageEnrollments(); break;
                case 4: backupData(); break;
                case 5: reports(); break;
                case 6: saveData(); System.out.println("Bye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // ---------- STUDENT MANAGEMENT ----------
    static void manageStudents() {
        while (true) {
            System.out.println("\n-- Student Menu --");
            System.out.println("1. Add  2. List  3. Search  4. Delete  5. Back");
            System.out.print("Choice: ");
            int c = readInt();
            if (c == 1) {
                System.out.print("ID: "); String id = sc.nextLine();
                System.out.print("Reg No: "); String reg = sc.nextLine();
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                students.add(new Student(id, reg, name, email));
                System.out.println("Added.");
            } else if (c == 2) {
                if (students.isEmpty()) System.out.println("No students.");
                for (Student s : students) System.out.println(s);
            } else if (c == 3) {
                System.out.print("Search name: "); String q = sc.nextLine().toLowerCase();
                for (Student s : students)
                    if (s.name.toLowerCase().contains(q)) System.out.println(s);
            } else if (c == 4) {
                System.out.print("ID to delete: "); String id = sc.nextLine();
                students.removeIf(s -> s.id.equals(id));
                System.out.println("Deleted.");
            } else break;
        }
    }

    // ---------- COURSE MANAGEMENT ----------
    static void manageCourses() {
        while (true) {
            System.out.println("\n-- Course Menu --");
            System.out.println("1. Add  2. List  3. Delete  4. Back");
            System.out.print("Choice: ");
            int c = readInt();
            if (c == 1) {
                System.out.print("Code: "); String code = sc.nextLine();
                System.out.print("Title: "); String title = sc.nextLine();
                System.out.print("Credits: "); int cr = readInt();
                System.out.print("Instructor: "); String ins = sc.nextLine();
                courses.add(new Course(code, title, cr, ins));
                System.out.println("Added.");
            } else if (c == 2) {
                if (courses.isEmpty()) System.out.println("No courses.");
                for (Course c2 : courses) System.out.println(c2);
            } else if (c == 3) {
                System.out.print("Code to delete: "); String code = sc.nextLine();
                courses.removeIf(c2 -> c2.code.equals(code));
                System.out.println("Deleted.");
            } else break;
        }
    }

    // ---------- ENROLLMENT ----------
    static void manageEnrollments() {
        while (true) {
            System.out.println("\n-- Enrollment Menu --");
            System.out.println("1. Enroll  2. Add Grade  3. Transcript  4. Back");
            System.out.print("Choice: ");
            int c = readInt();
            if (c == 1) {
                System.out.print("Student ID: "); String sid = sc.nextLine();
                System.out.print("Course Code: "); String cc = sc.nextLine();
                enrollments.add(new Enrollment(sid, cc, -1));
                System.out.println("Enrolled.");
            } else if (c == 2) {
                System.out.print("Student ID: "); String sid = sc.nextLine();
                System.out.print("Course Code: "); String cc = sc.nextLine();
                for (Enrollment e : enrollments)
                    if (e.studentId.equals(sid) && e.courseCode.equals(cc)) {
                        System.out.print("Grade: "); e.grade = Double.parseDouble(sc.nextLine());
                        System.out.println("Grade set.");
                    }
            } else if (c == 3) {
                System.out.print("Student ID: "); String sid = sc.nextLine();
                for (Enrollment e : enrollments)
                    if (e.studentId.equals(sid))
                        System.out.println(e.courseCode + " -> " + e.grade);
            } else break;
        }
    }

    // ---------- BACKUP ----------
    static void backupData() {
        saveData();
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File bk = new File(DATA_DIR + "backup_" + ts);
        bk.mkdirs();
        File[] files = new File(DATA_DIR).listFiles();
        if (files != null) for (File f : files) {
            if (f.isFile()) {
                try {
                    java.nio.file.Files.copy(f.toPath(),
                        new File(bk, f.getName()).toPath());
                } catch (IOException ex) { System.out.println("Copy failed: " + f); }
            }
        }
        System.out.println("Backup created: " + bk.getPath());
    }

    // ---------- REPORTS ----------
    static void reports() {
        System.out.println("\n-- GPA Distribution --");
        Map<String, Integer> dist = new TreeMap<>();
        for (Student s : students) {
            double total = 0; int count = 0;
            for (Enrollment e : enrollments)
                if (e.studentId.equals(s.id) && e.grade >= 0) { total += e.grade; count++; }
            double avg = count == 0 ? 0 : total / count;
            String bucket = avg >= 90 ? "A" : avg >= 80 ? "B" : avg >= 70 ? "C" : avg >= 60 ? "D" : "F";
            dist.merge(bucket, 1, Integer::sum);
        }
        dist.forEach((k, v) -> System.out.println(k + ": " + v + " students"));
    }

    // ---------- FILE I/O ----------
    static void saveData() {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "students.csv")) {
            for (Student s : students) pw.println(s.id + "," + s.regNo + "," + s.name + "," + s.email);
        } catch (IOException e) { System.out.println("Save failed."); }
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "courses.csv")) {
            for (Course c : courses) pw.println(c.code + "," + c.title + "," + c.credits + "," + c.instructor);
        } catch (IOException e) { System.out.println("Save failed."); }
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "enrollment.csv")) {
            for (Enrollment e : enrollments) pw.println(e.studentId + "," + e.courseCode + "," + e.grade);
        } catch (IOException e) { System.out.println("Save failed."); }
    }

    static void loadData() {
        File f = new File(DATA_DIR + "students.csv");
        if (f.exists()) try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 4) students.add(new Student(p[0], p[1], p[2], p[3]));
            }
        } catch (IOException e) { }
        f = new File(DATA_DIR + "courses.csv");
        if (f.exists()) try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 4) courses.add(new Course(p[0], p[1], Integer.parseInt(p[2]), p[3]));
            }
        } catch (IOException e) { }
        f = new File(DATA_DIR + "enrollment.csv");
        if (f.exists()) try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 3) enrollments.add(new Enrollment(p[0], p[1], Double.parseDouble(p[2])));
            }
        } catch (IOException e) { }
    }

    static int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { return -1; }
    }

    // ---------- MODEL CLASSES ----------
    static class Student {
        String id, regNo, name, email;
        Student(String i, String r, String n, String e) { id=i; regNo=r; name=n; email=e; }
        public String toString() { return id + " | " + name + " | " + email; }
    }
    static class Course {
        String code, title, instructor; int credits;
        Course(String c, String t, int cr, String i) { code=c; title=t; credits=cr; instructor=i; }
        public String toString() { return code + " | " + title + " | " + credits + "cr"; }
    }
    static class Enrollment {
        String studentId, courseCode; double grade;
        Enrollment(String s, String c, double g) { studentId=s; courseCode=c; grade=g; }
    }
}