/**
 * An abstract class representing a generic employee.
 * This class serves as the base for different types of employees (Manager, RegularEmployee, Intern).
 * It demonstrates abstraction by defining common attributes and an abstract method.
 */
public abstract class Employee {

    // Encapsulation: using private fields to protect data.
    private String name;
    private String id;
    private String department;
    private double baseSalary;
    private String performanceRating;
    protected double bonus;
    protected double fine;

    /**
     * Constructor for the Employee class.
     * @param name The employee's name.
     * @param id The employee's ID.
     * @param department The employee's department.
     * @param baseSalary The employee's base salary.
     */
    public Employee(String name, String id, String department, double baseSalary) {
        this.name = name;
        this.id = id;
        this.department = department;
        this.baseSalary = baseSalary;
        this.performanceRating = "Not Rated";
        this.bonus = 0.0;
        this.fine = 0.0;
    }

    // Getters and Setters for encapsulated fields.
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }
    
    /**
     * Awards a bonus to the employee.
     * @param amount The bonus amount.
     */
    public void awardBonus(double amount) {
        this.bonus += amount;
    }
    
    /**
     * Applies a fine to the employee.
     * @param amount The fine amount.
     */
    public void applyFine(double amount) {
        this.fine += amount;
    }

    /**
     * An abstract method to calculate the salary of an employee.
     * This method is implemented by subclasses (polymorphism).
     * @return The calculated monthly salary.
     */
    public abstract double calculateSalary();

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", baseSalary=" + baseSalary +
                ", performanceRating='" + performanceRating + '\'' +
                ", bonus=" + bonus +
                ", fine=" + fine +
                '}';
    }
}
