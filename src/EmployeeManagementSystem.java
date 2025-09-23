import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main application class for the Employee Management System.
 * It manages the collection of Employee objects, handles file I/O,
 * and provides the text-based user interface.
 * It demonstrates the use of Java Collections, file handling, and exception handling.
 */
public class EmployeeManagementSystem {

    // Using ArrayList for the collection of employees.
    // Justification: ArrayList provides fast random access (get, set) by index,
    // which is efficient for querying employees by ID or name (after a search).
    // It is also memory-efficient for a medium-sized list of employees.
    private List<Employee> employees = new ArrayList<>();
    private static final String FILE_NAME = "employee_data.csv";
    private Scanner scanner = new Scanner(System.in);
    private Performance performance = new Performance();

    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.run();
    }

    /**
     * Runs the main application loop, displaying the menu and handling user input.
     */
    public void run() {
        System.out.println("===== Welcome to the Employee Management System =====");
        
        while (true) {
            displayMenu();
            try {
                System.out.print("Please choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        loadDataFromFile();
                        break;
                    case 2:
                        addEmployee();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        viewEmployeeDetails();
                        break;
                    case 6:
                        managePerformance();
                        break;
                    case 7:
                        saveDataToFile();
                        System.out.println("Exiting the system. Goodbye!");
                        return; // Exit the program
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IOException e) {
                System.out.println("An error occurred during file operation: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the main menu for the user.
     */
    private void displayMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Load employee data from file");
        System.out.println("2. Add new employee");
        System.out.println("3. Update employee information");
        System.out.println("4. Delete employee");
        System.out.println("5. View/Query employee details");
        System.out.println("6. Performance Management");
        System.out.println("7. Exit");
    }

    /**
     * Loads employee data from the specified CSV file.
     * Demonstrates file reading and exception handling.
     */
    private void loadDataFromFile() throws IOException {
        System.out.println("\nLoading data from " + FILE_NAME + "...");
        employees.clear(); // Clear existing data to load fresh data
        
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File not found. Creating a new file and adding default employees.");
            createDefaultFile();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // Ensure at least basic fields are present
                    String type = parts[0].trim();
                    String name = parts[1].trim();
                    String id = parts[2].trim();
                    String department = parts[3].trim();
                    double baseSalary = Double.parseDouble(parts[4].trim());
                    String rating = (parts.length > 5) ? parts[5].trim() : "Not Rated";

                    switch (type) {
                        case "Manager":
                            if (parts.length >= 7) {
                                int subordinates = Integer.parseInt(parts[6].trim());
                                Manager manager = new Manager(name, id, department, baseSalary, subordinates);
                                manager.setPerformanceRating(rating);
                                employees.add(manager);
                            }
                            break;
                        case "RegularEmployee":
                            RegularEmployee regular = new RegularEmployee(name, id, department, baseSalary);
                            regular.setPerformanceRating(rating);
                            employees.add(regular);
                            break;
                        case "Intern":
                            Intern intern = new Intern(name, id, department);
                            intern.setPerformanceRating(rating);
                            employees.add(intern);
                            break;
                    }
                }
            }
            System.out.println("Data loaded successfully. Total employees: " + employees.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Make sure " + FILE_NAME + " exists in the project directory.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid data format in the file. Check for correct salary or subordinate values.");
        }
    }

    /**
     * Creates a default file with initial employee records, including group members.
     */
    private void createDefaultFile() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            // Updated to include group members' names and performance ratings
            bw.write("RegularEmployee,Emily Clark,E001,Finance,62000.0,Good\n");
            bw.write("Manager,John Doe,E002,IT,85000.0,Outstanding,5\n");
            bw.write("Intern,Peter Jones,E003,Finance,1500.0,Not Rated\n");
            bw.write("RegularEmployee,Mary Evans,E004,Marketing,55000.0,Average\n");
            bw.write("Manager,Jane Smith,E005,HR,90000.0,Good,8\n");
            bw.write("Intern,Michael Adams,E006,Operations,1500.0,Not Rated\n");
            bw.write("RegularEmployee,Robert White,E007,Sales,70000.0,Outstanding\n");
            bw.write("Manager,Linda Green,E008,IT,92000.0,Good,6\n");
            bw.write("RegularEmployee,David Lee,E009,IT,65000.0,Average\n");
            bw.write("Intern,Susan Brown,E010,Finance,1500.0,Poor\n");
            
            System.out.println("Default employee data file created successfully.");
        }
    }

    /**
     * Saves the current employee data to the CSV file.
     * Demonstrates file writing and exception handling.
     */
    private void saveDataToFile() {
        System.out.println("\nSaving data to " + FILE_NAME + "...");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                String type = emp.getClass().getSimpleName();
                bw.write(type + "," + emp.getName() + "," + emp.getId() + "," + emp.getDepartment() + "," + emp.getBaseSalary() + "," + emp.getPerformanceRating());
                if (emp instanceof Manager) {
                    bw.write("," + ((Manager) emp).getSubordinatesManaged());
                }
                bw.newLine();
            }
            System.out.println("Data saved successfully. Total employees: " + employees.size());
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    /**
     * Adds a new employee to the system based on user input.
     */
    private void addEmployee() {
        System.out.println("\n--- Add New Employee ---");
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        System.out.print("Is this a Manager (M), Regular Employee (R), or Intern (I)? ");
        String type = scanner.nextLine().toUpperCase();

        Employee newEmployee = null;
        try {
            switch (type) {
                case "M":
                    System.out.print("Enter base salary: ");
                    double managerSalary = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter number of subordinates: ");
                    int subordinates = Integer.parseInt(scanner.nextLine());
                    newEmployee = new Manager(name, id, department, managerSalary, subordinates);
                    break;
                case "R":
                    System.out.print("Enter base salary: ");
                    double regularSalary = Double.parseDouble(scanner.nextLine());
                    newEmployee = new RegularEmployee(name, id, department, regularSalary);
                    break;
                case "I":
                    newEmployee = new Intern(name, id, department);
                    break;
                default:
                    System.out.println("Invalid employee type. Employee not added.");
                    return;
            }
            employees.add(newEmployee);
            System.out.println("Employee added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for salary or subordinates.");
        }
        saveDataToFile(); // Save immediately after adding
    }

    /**
     * Updates an existing employee's information.
     */
    private void updateEmployee() {
        System.out.println("\n--- Update Employee Information ---");
        System.out.print("Enter employee ID to update: ");
        String id = scanner.nextLine();
        
        Employee employeeToUpdate = findEmployeeById(id);
        
        if (employeeToUpdate != null) {
            System.out.println("Employee found. Current details: " + employeeToUpdate);
            System.out.print("Enter new performance rating (e.g., 'Outstanding', 'Good', 'Average', 'Poor'): ");
            String newRating = scanner.nextLine();
            employeeToUpdate.setPerformanceRating(newRating);
            System.out.println("Employee information updated successfully.");
            saveDataToFile();
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    /**
     * Deletes an employee record from the system.
     */
    private void deleteEmployee() {
        System.out.println("\n--- Delete Employee ---");
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine();
        
        Employee employeeToDelete = findEmployeeById(id);
        
        if (employeeToDelete != null) {
            employees.remove(employeeToDelete);
            System.out.println("Employee with ID " + id + " deleted successfully.");
            saveDataToFile();
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    /**
     * Provides options to view or query employee details.
     */
    private void viewEmployeeDetails() {
        System.out.println("\n--- View/Query Employee Details ---");
        System.out.println("1. View all employees");
        System.out.println("2. Query employee by ID");
        System.out.println("3. Query employees by Name");
        System.out.println("4. Query employees by Performance Rating");
        System.out.print("Choose an option: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    viewAllEmployees();
                    break;
                case 2:
                    queryEmployeeById();
                    break;
                case 3:
                    queryEmployeesByName();
                    break;
                case 4:
                    queryEmployeesByRating();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Provides a submenu for performance management actions.
     */
    private void managePerformance() {
        System.out.println("\n--- Performance Management ---");
        System.out.println("1. Issue Appreciation Letter");
        System.out.println("2. Issue Warning Letter");
        System.out.println("3. Award Bonus");
        System.out.println("4. Apply Fine");
        System.out.print("Choose an option: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter employee ID: ");
            String id = scanner.nextLine();
            Employee employee = findEmployeeById(id);
            
            if (employee == null) {
                System.out.println("Error: Employee with ID " + id + " not found.");
                return;
            }
            
            switch (choice) {
                case 1:
                    performance.issueAppreciationLetter(employee);
                    break;
                case 2:
                    performance.issueWarningLetter(employee);
                    break;
                case 3:
                    System.out.print("Enter bonus amount: $");
                    double bonusAmount = Double.parseDouble(scanner.nextLine());
                    performance.awardBonus(employee, bonusAmount);
                    break;
                case 4:
                    System.out.print("Enter fine amount: $");
                    double fineAmount = Double.parseDouble(scanner.nextLine());
                    performance.applyFine(employee, fineAmount);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            saveDataToFile();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for your choice or the amount.");
        }
    }

    /**
     * Finds an employee by their ID.
     * @param id The ID to search for.
     * @return The Employee object if found, otherwise null.
     */
    private Employee findEmployeeById(String id) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    /**
     * Views all employees in the system.
     */
    private void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees in the system. Please load data first.");
            return;
        }
        System.out.println("\n--- All Employees ---");
        for (Employee emp : employees) {
            System.out.println(emp);
            System.out.println("   Calculated Monthly Salary: $" + String.format("%.2f", emp.calculateSalary()));
        }
    }

    /**
     * Queries for a single employee by ID.
     */
    private void queryEmployeeById() {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        Employee emp = findEmployeeById(id);
        if (emp != null) {
            System.out.println("Employee found: " + emp);
            System.out.println("   Calculated Monthly Salary: $" + String.format("%.2f", emp.calculateSalary()));
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    /**
     * Queries for employees by name (case-insensitive search).
     */
    private void queryEmployeesByName() {
        System.out.print("Enter employee name or part of a name: ");
        String nameQuery = scanner.nextLine().toLowerCase();
        System.out.println("Matching employees:");
        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(nameQuery)) {
                System.out.println(emp);
                System.out.println("   Calculated Monthly Salary: $" + String.format("%.2f", emp.calculateSalary()));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No employees found with that name.");
        }
    }

    /**
     * Queries for employees by performance rating.
     */
    private void queryEmployeesByRating() {
        System.out.print("Enter performance rating to query (e.g., 'Outstanding', 'Good'): ");
        String ratingQuery = scanner.nextLine();
        System.out.println("Employees with rating '" + ratingQuery + "':");
        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getPerformanceRating().equalsIgnoreCase(ratingQuery)) {
                System.out.println(emp);
                System.out.println("   Calculated Monthly Salary: $" + String.format("%.2f", emp.calculateSalary()));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No employees found with that rating.");
        }
    }
}
