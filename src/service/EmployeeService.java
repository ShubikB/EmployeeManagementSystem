package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import models.Employee;
import util.FileHandler;

public class EmployeeService {
    private List<Employee> employees;
    private String dataFilePath;

    public enum SortCriteria {
        BY_ID,
        BY_NAME,
        BY_DEPARTMENT,
        BY_SALARY,
        BY_PERFORMANCE_RATING
    }

    public EmployeeService() {
        this.employees = new ArrayList<>();
        this.dataFilePath = "employee_data.csv"; // Default file path
    }

    public EmployeeService(String dataFilePath) {
        this.employees = new ArrayList<>();
        this.dataFilePath = dataFilePath;
        loadEmployeesFromFile(dataFilePath);
    }

    public void clearEmployees() {
        this.employees.clear();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void loadEmployeesFromFile(String filePath) {
        try {
            this.employees = FileHandler.readEmployees(filePath);
        } catch (IOException e) {
            System.err.println("Error loading employees: " + e.getMessage());
            this.employees = new ArrayList<>();
        }
    }

    public void saveEmployeesToFile(String filePath) {
        FileHandler.writeEmployees(employees, filePath);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveChanges();
    }

    public boolean deleteEmployee(String id) {
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        if (removed) {
            saveChanges();
        }
        return removed;
    }

    public void sortEmployees(SortCriteria criteria) {
        Comparator<Employee> comparator;
        switch (criteria) {
            case BY_ID:
                comparator = Comparator.comparing(employee -> 
                    Integer.parseInt(employee.getId().replaceAll("\\D", "")));
                break;
            case BY_NAME:
                comparator = Comparator.comparing(Employee::getName);
                break;
            case BY_DEPARTMENT:
                comparator = Comparator.comparing(Employee::getDepartment);
                break;
            case BY_SALARY:
                // Sort by highest salary first
                comparator = Comparator.comparingDouble(Employee::calculateSalary).reversed();
                break;
            case BY_PERFORMANCE_RATING:
                comparator = Comparator.comparing(Employee::getPerformanceRating);
                break;
            default:
                return; // No sorting
        }
        this.employees.sort(comparator);
    }

    public void saveChanges() {
        saveEmployeesToFile(this.dataFilePath);
    }

    public Optional<Employee> findEmployeeById(String id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public List<Employee> findEmployeesByName(String name) {
        return employees.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployeesByDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
}
