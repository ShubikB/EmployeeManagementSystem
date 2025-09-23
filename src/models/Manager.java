package models;

import java.io.Serializable;

/**
 * A concrete class representing a Manager, which inherits from Employee.
 * It demonstrates inheritance and a specific implementation of the calculateSalary method.
 */
public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int subordinatesManaged;
    private static final double MANAGER_BONUS_PER_SUBORDINATE = 500.0;

    public Manager(String name, String id, String department, double baseSalary, int subordinatesManaged) {
        super(name, id, department, baseSalary);
        this.subordinatesManaged = subordinatesManaged;
    }

    @Override
    public double calculateSalary() {
        // Manager salary is base salary plus bonus per subordinate, plus performance bonus, minus fines
        return getBaseSalary() + 
               (subordinatesManaged * MANAGER_BONUS_PER_SUBORDINATE) + 
               getBonus() - 
               getFine();
    }

    public int getSubordinatesManaged() {
        return subordinatesManaged;
    }

    public void setSubordinatesManaged(int subordinatesManaged) {
        if (subordinatesManaged < 0) {
            throw new IllegalArgumentException("Number of subordinates cannot be negative");
        }
        this.subordinatesManaged = subordinatesManaged;
    }
    
    @Override
    public String toString() {
        return super.toString() + String.format(", Type: Manager, Subordinates: %d", subordinatesManaged);
    }
}