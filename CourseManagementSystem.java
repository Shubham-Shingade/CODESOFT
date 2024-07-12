import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean addStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        } else {
            return false;
        }
    }

    public void removeStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }
}

class Student {
    private String studentId;
    private String name;
    private Set<String> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new HashSet<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

public class CourseManagementSystem {
    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();
        boolean running = true;

        while (running) {
            System.out.println("\nCourse Management System Menu:");
            System.out.println("1. List Courses");
            System.out.println("2. Register Student");
            System.out.println("3. Register for Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    registerForCourse();
                    break;
                case 4:
                    dropCourse();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of Computer Science", 30, "MWF 10-11AM"));
        courses.put("MATH101", new Course("MATH101", "Calculus I", "Introduction to Calculus", 25, "TTh 9-10:30AM"));
        courses.put("ENG101", new Course("ENG101", "English Literature", "Study of English Literature", 20, "MWF 2-3PM"));
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Available Slots: " + course.getAvailableSlots());
            System.out.println();
        }
    }

    private static void registerStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        if (!students.containsKey(studentId)) {
            students.put(studentId, new Student(studentId, name));
            System.out.println("Student registered successfully.");
        } else {
            System.out.println("Student ID already exists.");
        }
    }

    private static void registerForCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (course.getAvailableSlots() > 0) {
                if (!student.getRegisteredCourses().contains(courseCode)) {
                    student.registerCourse(courseCode);
                    course.addStudent();
                    System.out.println("Registered for the course successfully.");
                } else {
                    System.out.println("You are already registered for this course.");
                }
            } else {
                System.out.println("Course is full.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    private static void dropCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        Student student = students.get(studentId);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (student.getRegisteredCourses().contains(courseCode)) {
                student.dropCourse(courseCode);
                course.removeStudent();
                System.out.println("Dropped the course successfully.");
            } else {
                System.out.println("You are not registered for this course.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }
}
