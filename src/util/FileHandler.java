package util;

import models.*;
import java.io.*;
import java.util.*;

/**
 * Utility class to handle all file operations for the Employee Management System.
 */
public class FileHandler {
    private static final String DEFAULT_FILE_NAME = "employee_data.csv";
    private static final String HEADER = "Type,Name,ID,Department,BaseSalary,PerformanceRating,Bonus,Fine";
    private static String CSV_FILE_PATH = "employee_data.csv";

    public static void setCsvFilePath(String path) {
        CSV_FILE_PATH = path;
    }

    /**
     * Loads employee data from a CSV file.
     * @param fileName The name of the file to load from
     * @return List of employees
     * @throws IOException If there's an error reading the file
     */
    public static List<Employee> readEmployees(String fileName) throws IOException {
        List<Employee> employees = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            createDefaultFile(fileName);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        Employee emp = createEmployeeFromCSV(parts);
                        if (emp != null) {
                            employees.add(emp);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing number in line: " + line);
                }
            }
        }
        return employees;
    }

    /**
     * Saves employee data to a CSV file.
     * @param employees List of employees to save
     * @param filePath The name of the file to save to
     * @throws IOException If there's an error writing to the file
     */
    public static void writeEmployees(List<Employee> employees, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(HEADER);
            bw.newLine();
            
            for (Employee emp : employees) {
                bw.write(convertEmployeeToCSV(emp));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultFile(String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(HEADER);
            bw.newLine();
            
            // Add your group member names here as employees
            bw.write("Manager,John Smith,E001,IT,85000.0,Outstanding,1000.0,0.0");
            bw.newLine();
            bw.write("RegularEmployee,Jane Doe,E002,HR,60000.0,Good,500.0,0.0");
            bw.newLine();
            bw.write("Intern,Peter Parker,E003,Marketing,1500.0,Good,0.0,0.0");
            bw.newLine();
            // Add more default employees...
        }
    }

    private static Employee createEmployeeFromCSV(String[] parts) {
        String type = parts[0].trim();
        String name = parts[1].trim();
        String id = parts[2].trim();
        String department = parts[3].trim();
        double baseSalary = Double.parseDouble(parts[4].trim());
        
        Employee emp = null;
        switch (type) {
            case "Manager":
                emp = new Manager(id, name, department, baseSalary, 
                    parts.length > 8 ? Integer.parseInt(parts[8].trim()) : 0);
                break;
            case "RegularEmployee":
                emp = new RegularEmployee(id, name, department, baseSalary);
                break;
            case "Intern":
                emp = new Intern(id, name, department, baseSalary);
                break;
        }
        
        if (emp != null && parts.length > 5) {
            emp.setPerformanceRating(parts[5].trim());
            if (parts.length > 6) emp.addBonus(Double.parseDouble(parts[6].trim()));
            if (parts.length > 7) emp.addFine(Double.parseDouble(parts[7].trim()));
        }
        
        return emp;
    }

    private static String convertEmployeeToCSV(Employee emp) {
        StringBuilder sb = new StringBuilder();
        sb.append(emp.getClass().getSimpleName()).append(",");
        sb.append(emp.getName()).append(",");
        sb.append(emp.getId()).append(",");
        sb.append(emp.getDepartment()).append(",");
        sb.append(emp.getBaseSalary()).append(",");
        sb.append(emp.getPerformanceRating()).append(",");
        sb.append(emp.getBonus()).append(",");
        sb.append(emp.getFine());
        
        if (emp instanceof Manager) {
            sb.append(",").append(((Manager) emp).getSubordinatesManaged());
        }
        
        return sb.toString();
    }
}