import java.io.*;
import java.util.*;

class Student {
    int id;
    String name;
    int age;

    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age;
    }

    String toFileLine() {
        return id + "," + name + "," + age;
    }

    static Student fromFileLine(String line) {
        String[] parts = line.split(",");
        return new Student(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadFromFile();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear newline

            if (choice == 1) {
                System.out.print("Enter ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                boolean exists = false;

                for (Student s : students) {
                    if (s.id == id) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Student with this ID already exists!");
                } else {
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    students.add(new Student(id, name, age));
                    saveToFile();
                    System.out.println("Student added successfully!");
                }

            } else if (choice == 2) {
                if (students.isEmpty()) {
                    System.out.println("No students found!");
                } else {
                    System.out.println("--- Student List ---");
                    for (Student s : students) {
                        System.out.println(s);
                    }
                }

            } else if (choice == 3) {
                System.out.print("Enter ID or Name to search: ");
                String input = sc.nextLine();
                boolean found = false;

                for (Student s : students) {
                    if (String.valueOf(s.id).equals(input) || s.name.equalsIgnoreCase(input)) {
                        System.out.println(s);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("Student not found.");
                }

            } else if (choice == 4) {
                System.out.print("Enter ID to delete: ");
                int id = sc.nextInt();
                sc.nextLine();
                boolean removed = false;

                for (int i = 0; i < students.size(); i++) {
                    if (students.get(i).id == id) {
                        students.remove(i);
                        removed = true;
                        break;
                    }
                }

                if (removed) {
                    saveToFile();
                    System.out.println("Student deleted.");
                } else {
                    System.out.println("Student not found.");
                }

            } else if (choice == 5) {
                System.out.println("Exiting program...");
                break;

            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                students.add(Student.fromFileLine(line));
            }
        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }

    static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Student s : students) {
                writer.println(s.toFileLine());
            }
        } catch (Exception e) {
            System.out.println("Error saving to file.");
        }
    }
}
