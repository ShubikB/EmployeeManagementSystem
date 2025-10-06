package ui;

import javax.swing.*;
import java.awt.*;
import models.*;
import service.EmployeeService;

public class AddEmployeeDialog extends JDialog {
    private final EmployeeService employeeService;

    private JComboBox<String> typeComboBox;
    private JTextField idField, nameField, departmentField, baseSalaryField;
    private JTextField bonusField, subordinatesField;
    private JLabel bonusLabel, subordinatesLabel;
    private JPanel specificFieldsPanel;

    public AddEmployeeDialog(Frame owner, EmployeeService employeeService) {
        super(owner, "Add New Employee", true);
        this.employeeService = employeeService;

        setLayout(new BorderLayout());
        setSize(400, 350);
        setLocationRelativeTo(owner);

        // Panel for common fields
        JPanel commonFieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        commonFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        typeComboBox = new JComboBox<>(new String[]{"Manager", "RegularEmployee", "Intern"});
        idField = new JTextField();
        nameField = new JTextField();
        departmentField = new JTextField();
        baseSalaryField = new JTextField();

        commonFieldsPanel.add(new JLabel("Employee Type:"));
        commonFieldsPanel.add(typeComboBox);
        commonFieldsPanel.add(new JLabel("ID:"));
        commonFieldsPanel.add(idField);
        commonFieldsPanel.add(new JLabel("Name:"));
        commonFieldsPanel.add(nameField);
        commonFieldsPanel.add(new JLabel("Department:"));
        commonFieldsPanel.add(departmentField);
        commonFieldsPanel.add(new JLabel("Annual Base Salary:"));
        commonFieldsPanel.add(baseSalaryField);

        // Panel for type-specific fields
        specificFieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        specificFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bonusField = new JTextField("0");
        subordinatesField = new JTextField("0");

        bonusLabel = new JLabel("Bonus:");
        subordinatesLabel = new JLabel("Subordinates Managed:");

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(commonFieldsPanel, BorderLayout.NORTH);
        add(specificFieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        typeComboBox.addActionListener(e -> updateSpecificFields());
        saveButton.addActionListener(e -> saveEmployee());
        cancelButton.addActionListener(e -> setVisible(false));

        updateSpecificFields(); // Initial setup
    }

    private void updateSpecificFields() {
        specificFieldsPanel.removeAll();
        String selectedType = (String) typeComboBox.getSelectedItem();

        if (selectedType == null) return;

        switch (selectedType) {
            case "Manager":
                specificFieldsPanel.add(bonusLabel);
                specificFieldsPanel.add(bonusField);
                specificFieldsPanel.add(subordinatesLabel);
                specificFieldsPanel.add(subordinatesField);
                break;
            case "RegularEmployee":
                specificFieldsPanel.add(bonusLabel);
                specificFieldsPanel.add(bonusField);
                break;
            case "Intern":
                baseSalaryField.setEnabled(true);
                break;
        }
        if (!"Intern".equals(selectedType)) {
            baseSalaryField.setEnabled(true);
        }

        specificFieldsPanel.revalidate();
        specificFieldsPanel.repaint();
        pack();
    }

    private void saveEmployee() {
        try {
            String type = (String) typeComboBox.getSelectedItem();
            String id = idField.getText();
            String name = nameField.getText();
            String department = departmentField.getText();
            double baseSalary = Double.parseDouble(baseSalaryField.getText());

            Employee emp = null;
            switch (type) {
                case "Manager":
                    double bonus = Double.parseDouble(bonusField.getText());
                    int subs = Integer.parseInt(subordinatesField.getText());
                    emp = new Manager(id, name, department, baseSalary, subs);
                    emp.addBonus(bonus);
                    break;
                case "RegularEmployee":
                    double perfBonus = Double.parseDouble(bonusField.getText());
                    emp = new RegularEmployee(id, name, department, baseSalary);
                    emp.addBonus(perfBonus);
                    break;
                case "Intern":
                    emp = new Intern(id, name, department, baseSalary);
                    break;
            }

            if (emp != null) {
                employeeService.addEmployee(emp);
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format in one of the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
