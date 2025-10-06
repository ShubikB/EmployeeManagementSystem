package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Employee;
import models.Intern;
import models.Manager;
import models.RegularEmployee;
import service.EmployeeService.SortCriteria;
import util.FileHandler;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private static final String TEST_DATA_FILE = "employee_data_test.csv";

    @BeforeEach
    public void setUp() {
        // Use a separate test file to avoid interfering with the main data
        employeeService = new EmployeeService(TEST_DATA_FILE);
        // Ensure the employee list is empty before each test
        employeeService.clearEmployees();
        // Ensure the test file is clean before each test
        new File(TEST_DATA_FILE).delete();
    }

    @Test
    public void testAddEmployee() {
        // Create a new employee
        Employee emp = new RegularEmployee("E001", "John Doe", "IT", 50000, 5000);
        
        // Add the employee
        employeeService.addEmployee(emp);
        
        // Retrieve the list of employees
        List<Employee> employees = employeeService.getEmployees();
        
        // Verify that the employee was added
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
    }

    @Test
    public void testFindEmployeeById() {
        // Add an employee first
        Employee emp = new RegularEmployee("E002", "Jane Smith", "HR", 60000, 7000);
        employeeService.addEmployee(emp);

        // Find the employee by ID and get it from the Optional
        Employee foundEmployee = employeeService.findEmployeeById("E002").orElse(null);

        // Verify that the correct employee was found
        assertNotNull(foundEmployee);
        assertEquals("Jane Smith", foundEmployee.getName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee emp = new RegularEmployee("E003", "Peter Jones", "Finance", 70000, 6000);
        employeeService.addEmployee(emp);
        
        boolean deleted = employeeService.deleteEmployee("E003");
        assertTrue(deleted);
        assertEquals(0, employeeService.getEmployees().size());
    }

    @Test
    public void testDeleteNonExistentEmployee() {
        boolean deleted = employeeService.deleteEmployee("E999");
        assertFalse(deleted);
    }

    @Test
    public void testSortEmployeesByName() {
        Employee emp1 = new RegularEmployee("E005", "Charlie Brown", "HR", 55000, 5000);
        Employee emp2 = new RegularEmployee("E004", "Alice Williams", "IT", 65000, 6000);
        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);

        employeeService.sortEmployees(SortCriteria.BY_NAME);

        List<Employee> sortedEmployees = employeeService.getEmployees();
        assertEquals("Alice Williams", sortedEmployees.get(0).getName());
        assertEquals("Charlie Brown", sortedEmployees.get(1).getName());
    }
    
    @Test
    public void testSortEmployeesBySalary() {
        Employee emp1 = new RegularEmployee("E005", "Charlie Brown", "HR", 55000, 5000);
        Employee emp2 = new RegularEmployee("E004", "Alice Williams", "IT", 65000, 6000);
        Manager manager = new Manager("M001", "Bob Johnson", "Management", 90000, 10);
        manager.addBonus(15000);
        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);
        employeeService.addEmployee(manager);

        employeeService.sortEmployees(SortCriteria.BY_SALARY);

        List<Employee> sortedEmployees = employeeService.getEmployees();
        assertEquals("Bob Johnson", sortedEmployees.get(0).getName()); // 90000 + 15000
        assertEquals("Alice Williams", sortedEmployees.get(1).getName()); // 65000 + 6000
        assertEquals("Charlie Brown", sortedEmployees.get(2).getName()); // 55000 + 5000
    }

    @Test
    public void testFindEmployeesByName() {
        Employee emp1 = new RegularEmployee("E006", "David Miller", "Finance", 72000, 6500);
        Employee emp2 = new RegularEmployee("E007", "David Garcia", "Finance", 73000, 6600);
        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);

        List<Employee> found = employeeService.findEmployeesByName("david");
        assertEquals(2, found.size());
    }

    @Test
    public void testFindEmployeesByDepartment() {
        Employee emp1 = new RegularEmployee("E008", "Eva Green", "Marketing", 68000, 6000);
        Employee emp2 = new RegularEmployee("E009", "Frank White", "IT", 78000, 7000);
        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);

        List<Employee> found = employeeService.findEmployeesByDepartment("IT");
        assertEquals(1, found.size());
        assertEquals("Frank White", found.get(0).getName());
    }

    @Test
    public void testSaveAndLoadEmployees() throws IOException {
        Employee emp = new Intern("I001", "Grace Hall", "IT", 25000);
        employeeService.addEmployee(emp);
        employeeService.saveChanges(); // Saves to TEST_DATA_FILE

        // New service to load from the file
        EmployeeService newService = new EmployeeService(TEST_DATA_FILE);
        List<Employee> loadedEmployees = newService.getEmployees();

        assertEquals(1, loadedEmployees.size());
        assertEquals("Grace Hall", loadedEmployees.get(0).getName());
    }

    @Test
    public void testFindInEmptyList() {
        Optional<Employee> found = employeeService.findEmployeeById("E999");
        assertFalse(found.isPresent());
    }

    @Test
    public void testSortEmptyList() {
        employeeService.sortEmployees(SortCriteria.BY_NAME);
        assertTrue(employeeService.getEmployees().isEmpty());
    }

    @AfterEach
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(TEST_DATA_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
