package models;

/**
 * A concrete class representing a Regular Employee, which inherits from Employee.
 * It demonstrates inheritance and a specific implementation of the calculateSalary method.
 */
public class RegularEmployee extends Employee {
    private static final long serialVersionUID = 1L;

    public RegularEmployee(String name, String id, String department, double baseSalary) {
        super(name, id, department, baseSalary);
    }

    @Override
    public double calculateSalary() {
        // Regular employee salary is base salary plus performance bonus, minus fines
        return getBaseSalary() + getBonus() - getFine();
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Type: Regular Employee";
    }
}