package ui;

import java.util.List;
import java.util.Scanner;

import models.Employee;
import models.Intern;
import models.Manager;
import models.RegularEmployee;
import service.EmployeeService;

public class TextUI {
    private EmployeeService employeeService;
    private Scanner scanner;

    public TextUI() {
        this.employeeService = new EmployeeService();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            displayMenu();
            try {
                int choice = getValidIntInput("Choose an option: ", 1, 8);
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
                        // Performance Management - To be implemented
                        System.out.println("Performance Management is not yet implemented.");
                        break;
                    case 7:
                        sortEmployeesMenu();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        return;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n>===== Main Menu =====<");
        System.out.println("1. Load employee data from file");
        System.out.println("2. Add a new employee");
        System.out.println("3. Update an employee's details");
        System.out.println("4. Delete an employee");
        System.out.println("5. View/Query employee details");
        System.out.println("6. Performance Management");
        System.out.println("7. Sort Employees");
        System.out.println("8. Exit");
    }

    private void sortEmployeesMenu() {
        System.out.println("\n--- Sort Employees ---");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by Department");
        System.out.println("4. Sort by Salary (Highest First)");
        System.out.println("5. Sort by Performance Rating");
        System.out.println("6. Back to Main Menu");

        int choice = getValidIntInput("Choose a sorting option: ", 1, 6);
        switch (choice) {
            case 1:
                employeeService.sortEmployees(EmployeeService.SortCriteria.BY_ID);
                System.out.println("Employees sorted by ID.");
                break;
            case 2:
                employeeService.sortEmployees(EmployeeService.SortCriteria.BY_NAME);
                System.out.println("Employees sorted by Name.");
                break;
            case 3:
                employeeService.sortEmployees(EmployeeService.SortCriteria.BY_DEPARTMENT);
                System.out.println("Employees sorted by Department.");
                break;
            case 4:
                employeeService.sortEmployees(EmployeeService.SortCriteria.BY_SALARY);
                System.out.println("Employees sorted by Salary.");
                break;
            case 5:
                employeeService.sortEmployees(EmployeeService.SortCriteria.BY_PERFORMANCE_RATING);
                System.out.println("Employees sorted by Performance Rating.");
                break;
            case 6:
                return; // Return to main menu
        }
        displayEmployees(employeeService.getEmployees());
    }

    private void loadEmployeeData() {
        employeeService.loadEmployeesFromFile("employee_data.csv");
        System.out.println("Employee data loaded successfully.");
    }

    private void addEmployee() {
        System.out.println("Enter employee type (1. Manager, 2. Regular, 3. Intern):");
        int type = getValidIntInput("Choose a type: ", 1, 3);
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        double baseSalary = getValidDoubleInput("Enter Annual Base Salary: ");

        Employee emp = null;
        switch (type) {
            case 1:
                double bonus = getValidDoubleInput("Enter Bonus: ");
                emp = new Manager(id, name, department, baseSalary, bonus);
                break;
            case 2:
                double performanceBonus = getValidDoubleInput("Enter Performance Bonus: ");
                emp = new RegularEmployee(id, name, department, baseSalary, performanceBonus);
                break;
            case 3:
                emp = new Intern(id, name, department, baseSalary);
                break;
        }
        if (emp != null) {
            employeeService.addEmployee(emp);
            System.out.println("Employee added successfully.");
        }
    }

    private void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        String id = scanner.nextLine();
        employeeService.findEmployeeById(id).ifPresentOrElse(employee -> {
            System.out.print("Enter new name (current: " + employee.getName() + "): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                employee.setName(newName);
            }
            System.out.print("Enter new department (current: " + employee.getDepartment() + "): ");
            String newDept = scanner.nextLine();
            if (!newDept.isEmpty()) {
                employee.setDepartment(newDept);
            }
            employeeService.saveChanges(); // Persist changes
            System.out.println("Employee updated successfully.");
        }, () -> System.out.println("Employee not found."));
    }

    private void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine();
        if (employeeService.deleteEmployee(id)) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void viewEmployeeDetails() {
        System.out.println("\n--- View Employee Details ---");
        System.out.println("1. View all employees");
        System.out.println("2. Search by ID");
        System.out.println("3. Search by name");
        System.out.println("4. Search by department");

        int choice = getValidIntInput("Choose an option: ", 1, 4);
        switch (choice) {
            case 1:
                displayEmployees(employeeService.getEmployees());
                break;
            case 2:
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                employeeService.findEmployeeById(id)
                        .ifPresentOrElse(e -> displayEmployees(List.of(e)), () -> System.out.println("No employee found."));
                break;
            case 3:
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                displayEmployees(employeeService.findEmployeesByName(name));
                break;
            case 4:
                System.out.print("Enter Department: ");
                String dept = scanner.nextLine();
                displayEmployees(employeeService.findEmployeesByDepartment(dept));
                break;
        }
    }

    private void displayEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }
        employees.forEach(System.out::println);
    }

    private int getValidIntInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private double getValidDoubleInput(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
