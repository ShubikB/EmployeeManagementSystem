package models;

/**
 * A concrete class representing an Intern, which inherits from Employee.
 * It demonstrates inheritance and a specific implementation of the calculateSalary method.
 */
public class Intern extends Employee {
    private static final long serialVersionUID = 1L;
    private static final double INTERN_STIPEND = 1500.0;

    public Intern(String name, String id, String department) {
        super(name, id, department, INTERN_STIPEND);
    }

    @Override
    public double calculateSalary() {
        // Intern's salary is a fixed stipend plus any bonus, minus fines
        return INTERN_STIPEND + getBonus() - getFine();
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Type: Intern";
    }
}