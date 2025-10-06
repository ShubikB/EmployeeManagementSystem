package models;

/**
 * A concrete class representing a Regular Employee, which inherits from Employee.
 * It demonstrates inheritance and a specific implementation of the calculateSalary method.
 */
public class RegularEmployee extends Employee {
    private static final long serialVersionUID = 1L;

    public RegularEmployee(String id, String name, String department, double baseSalary, double performanceBonus) {
        super(id, name, department, baseSalary);
        addBonus(performanceBonus);
    }

    public RegularEmployee(String id, String name, String department, double baseSalary) {
        super(id, name, department, baseSalary);
    }

    @Override
    public double calculateSalary() {
        // Regular employee's yearly salary is their base salary plus performance bonus, minus fines.
        return getBaseSalary() + getBonus() - getFine();
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Type: Regular Employee";
    }
}