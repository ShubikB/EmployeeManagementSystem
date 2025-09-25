package service;

import models.*;
import util.FileHandler;
import java.io.*;
import java.util.*;

/**
 * The main application class for the Employee Management System.
 * It manages the collection of Employee objects and provides the text-based user interface.
 */
public class EmployeeManagementSystem {
    private List<Employee> employees;
    private static final String FILE_NAME = "employee_data.csv";
    private Scanner scanner;

    public EmployeeManagementSystem() {
        this.employees = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.run();
    }

    public void run() {
        System.out.println(">===== Welcome to the Employee Management System =====<");
        
        while (true) {
            try {
                displayMenu();
                int choice = getValidIntInput("Please choose an option: ", 1, 7);
                
                switch (choice) {
                    case 1:
                        loadEmployeeData();
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
                        saveAndExit();
                        return;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n>===== Main Menu =====<");
        System.out.println("1. Load employee data from file");
        System.out.println("2. Add new employee");
        System.out.println("3. Update employee information");
        System.out.println("4. Delete employee");
        System.out.println("5. View/Query employee details");
        System.out.println("6. Performance Management");
        System.out.println("7. Exit");
    }

    private void loadEmployeeData() {
        try {
            employees = FileHandler.loadEmployees(FILE_NAME);
            System.out.println("Data loaded successfully. Total employees: " + employees.size());
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private void addEmployee() {
        try {
            System.out.println("\n--- Add New Employee ---");
            String name = getValidStringInput("Enter employee name: ");
            String id = getValidStringInput("Enter employee ID: ");
            
            // Check if ID already exists
            if (findEmployeeById(id) != null) {
                throw new IllegalArgumentException("Employee ID already exists");
            }

            String department = getValidStringInput("Enter department: ");
            String type = getValidStringInput("Enter employee type (M/R/I for Manager/Regular/Intern): ").toUpperCase();

            Employee newEmployee = null;
            switch (type) {
                case "M":
                    double managerSalary = getValidDoubleInput("Enter base salary: ", 0, Double.MAX_VALUE);
                    int subordinates = getValidIntInput("Enter number of subordinates: ", 0, Integer.MAX_VALUE);
                    newEmployee = new Manager(name, id, department, managerSalary, subordinates);
                    break;
                case "R":
                    double regularSalary = getValidDoubleInput("Enter base salary: ", 0, Double.MAX_VALUE);
                    newEmployee = new RegularEmployee(name, id, department, regularSalary);
                    break;
                case "I":
                    newEmployee = new Intern(name, id, department);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid employee type");
            }

            employees.add(newEmployee);
            System.out.println("Employee added successfully.");
            FileHandler.saveEmployees(employees, FILE_NAME);
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private void updateEmployee() {
        try {
            System.out.println("\n--- Update Employee Information ---");
            String id = getValidStringInput("Enter employee ID to update: ");
            
            Employee emp = findEmployeeById(id);
            if (emp == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            System.out.println("Current details: " + emp);
            System.out.println("\nUpdate Options:");
            System.out.println("1. Update base salary");
            System.out.println("2. Update department");
            System.out.println("3. Update performance rating");
            if (emp instanceof Manager) {
                System.out.println("4. Update number of subordinates");
            }

            int choice = getValidIntInput("Choose what to update: ", 1, emp instanceof Manager ? 4 : 3);
            
            switch (choice) {
                case 1:
                    double newSalary = getValidDoubleInput("Enter new base salary: ", 0, Double.MAX_VALUE);
                    emp.setBaseSalary(newSalary);
                    break;
                case 2:
                    String newDept = getValidStringInput("Enter new department: ");
                    // Assuming we add setDepartment method to Employee class
                    break;
                case 3:
                    String newRating = getValidStringInput("Enter new performance rating (Outstanding/Good/Average/Poor): ");
                    emp.setPerformanceRating(newRating);
                    break;
                case 4:
                    if (emp instanceof Manager) {
                        int newSubordinates = getValidIntInput("Enter new number of subordinates: ", 0, Integer.MAX_VALUE);
                        ((Manager) emp).setSubordinatesManaged(newSubordinates);
                    }
                    break;
            }

            System.out.println("Employee updated successfully.");
            FileHandler.saveEmployees(employees, FILE_NAME);
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    private void deleteEmployee() {
        try {
            System.out.println("\n--- Delete Employee ---");
            String id = getValidStringInput("Enter employee ID to delete: ");
            
            Employee emp = findEmployeeById(id);
            if (emp == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            System.out.println("Found employee: " + emp);
            String confirm = getValidStringInput("Are you sure you want to delete this employee? (yes/no): ");
            
            if (confirm.equalsIgnoreCase("yes")) {
                employees.remove(emp);
                System.out.println("Employee deleted successfully.");
                FileHandler.saveEmployees(employees, FILE_NAME);
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    private void viewEmployeeDetails() {
        if (employees.isEmpty()) {
            System.out.println("No employees in the system. Please load data first.");
            return;
        }

        System.out.println("\n--- View/Query Employee Details ---");
        System.out.println("1. View all employees");
        System.out.println("2. Search by ID");
        System.out.println("3. Search by name");
        System.out.println("4. Search by performance rating");
        System.out.println("5. View performance history");

        try {
            int choice = getValidIntInput("Choose an option: ", 1, 5);
            
            switch (choice) {
                case 1:
                    displayAllEmployees();
                    break;
                case 2:
                    searchById();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    searchByPerformance();
                    break;
                case 5:
                    viewPerformanceHistory();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error viewing employee details: " + e.getMessage());
        }
    }

    private void managePerformance() {
        try {
            System.out.println("\n--- Performance Management ---");
            String id = getValidStringInput("Enter employee ID: ");
            
            Employee emp = findEmployeeById(id);
            if (emp == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            System.out.println("Current employee details: " + emp);
            System.out.println("\nPerformance Management Options:");
            System.out.println("1. Issue Warning Letter");
            System.out.println("2. Issue Appreciation Letter");
            System.out.println("3. Award Bonus");
            System.out.println("4. Apply Fine");
            System.out.println("5. Update Performance Rating");

            int choice = getValidIntInput("Choose an option: ", 1, 5);
            
            Performance performance = new Performance(id, emp.getPerformanceRating());
            
            switch (choice) {
                case 1:
                    String warningReason = getValidStringInput("Enter warning reason: ");
                    performance.issueWarningLetter(warningReason);
                    break;
                case 2:
                    String appreciationReason = getValidStringInput("Enter appreciation reason: ");
                    performance.issueAppreciationLetter(appreciationReason);
                    break;
                case 3:
                    double bonusAmount = getValidDoubleInput("Enter bonus amount: ", 0, Double.MAX_VALUE);
                    emp.addBonus(bonusAmount);
                    performance.awardBonus();
                    break;
                case 4:
                    double fineAmount = getValidDoubleInput("Enter fine amount: ", 0, Double.MAX_VALUE);
                    String fineReason = getValidStringInput("Enter reason for fine: ");
                    emp.addFine(fineAmount);
                    performance.applyFine(fineReason);
                    break;
                case 5:
                    String newRating = getValidStringInput("Enter new performance rating (Outstanding/Good/Average/Poor): ");
                    emp.setPerformanceRating(newRating);
                    performance.setMonthlyRating(newRating);
                    break;
            }

            emp.addPerformanceRecord(performance);
            System.out.println("Performance record updated successfully.");
            FileHandler.saveEmployees(employees, FILE_NAME);
        } catch (Exception e) {
            System.out.println("Error managing performance: " + e.getMessage());
        }
    }

    private void displayAllEmployees() {
        System.out.println("\n=== All Employees ===");
        for (Employee emp : employees) {
            System.out.println("\n" + emp);
            System.out.printf("Salary per Annum: $%.2f%n", emp.calculateSalary());
        }
    }

    private void searchById() {
        String id = getValidStringInput("Enter employee ID: ");
        Employee emp = findEmployeeById(id);
        if (emp != null) {
            System.out.println("\nFound employee: " + emp);
            System.out.printf("Salary per Annum: $%.2f%n", emp.calculateSalary());
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    }

    private void searchByName() {
        String name = getValidStringInput("Enter employee name or part of name: ");
        boolean found = false;
        
        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(name.toLowerCase())) {
                if (!found) {
                    System.out.println("\nMatching employees:");
                    found = true;
                }
                System.out.println("\n" + emp);
                System.out.printf("Salary per Annum: $%.2f%n", emp.calculateSalary());
            }
        }
        
        if (!found) {
            System.out.println("No employees found matching: " + name);
        }
    }

    private void searchByPerformance() {
        String rating = getValidStringInput("Enter performance rating to search (Outstanding/Good/Average/Poor): ");
        boolean found = false;
        
        for (Employee emp : employees) {
            if (emp.getPerformanceRating().equalsIgnoreCase(rating)) {
                if (!found) {
                    System.out.println("\nEmployees with " + rating + " rating:");
                    found = true;
                }
                System.out.println("\n" + emp);
                System.out.printf("Salary per Annum: $%.2f%n", emp.calculateSalary());
            }
        }
        
        if (!found) {
            System.out.println("No employees found with rating: " + rating);
        }
    }

    private void viewPerformanceHistory() {
        String id = getValidStringInput("Enter employee ID: ");
        Employee emp = findEmployeeById(id);
        
        if (emp != null) {
            System.out.println("\nPerformance History for " + emp.getName() + ":");
            List<Performance> history = emp.getPerformanceHistory();
            
            if (history.isEmpty()) {
                System.out.println("No performance records found.");
            } else {
                for (Performance perf : history) {
                    System.out.println(perf);
                }
            }
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    }

    private void saveAndExit() {
        try {
            FileHandler.saveEmployees(employees, FILE_NAME);
            System.out.println("Data saved successfully.");
            System.out.println("Thank you for using the Employee Management System. Goodbye!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private Employee findEmployeeById(String id) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Input validation methods
    private String getValidStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        return input;
    }

    private int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input < min || input > max) {
                    System.out.printf("Please enter a number between %d and %d%n", min, max);
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    private double getValidDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = Double.parseDouble(scanner.nextLine().trim());
                if (input < min || input > max) {
                    System.out.printf("Please enter a number between %.2f and %.2f%n", min, max);
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}