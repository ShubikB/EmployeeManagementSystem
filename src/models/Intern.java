package models;

/**
 * A concrete class representing an Intern, which inherits from Employee.
 * It demonstrates inheritance and a specific implementation of the calculateSalary method.
 */
public class Intern extends Employee {
    private static final long serialVersionUID = 1L;

    public Intern(String id, String name, String department, double baseSalary) {
        super(id, name, department, baseSalary);
    }

    @Override
    public double calculateSalary() {
        // Intern's yearly salary is their base salary plus any bonus, minus fines.
        return getBaseSalary() + getBonus() - getFine();
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Type: Intern";
    }
}