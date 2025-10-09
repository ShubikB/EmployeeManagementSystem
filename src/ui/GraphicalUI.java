package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import models.Employee;
import models.Manager;
import models.Intern;
import service.EmployeeService;

public class GraphicalUI extends JFrame {

    private final EmployeeService employeeService;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private static final String WELCOME_PANEL = "WelcomePanel";
    private static final String MAIN_PANEL = "MainPanel";


    public GraphicalUI() {
        this.employeeService = new EmployeeService();
        
        setTitle("Employee Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        // Show welcome panel first
        cardLayout.show(cardPanel, WELCOME_PANEL);
    }

    private void initComponents() {
        // Main container with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // --- Welcome Panel ---
        JPanel welcomePanel = new JPanel(new BorderLayout(10, 10));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        
        JLabel welcomeMessage = new JLabel("<html><div style='text-align: center;'>Welcome! <br>Click the button below to manage your employees.</div></html>", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));

        RoundedButton enterButton = new RoundedButton("Manage Employees", 20);
        enterButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        enterButton.addActionListener(e -> {
            cardLayout.show(cardPanel, MAIN_PANEL);
            // loadEmployeeData(); // Load data when switching to the main panel
        });

        JLabel creatorDetails = new JLabel("<html><div style='text-align: center;'>Coded By: <b>Shubik Bhatt</b><br>20035225.</div></html>", SwingConstants.CENTER);
        creatorDetails.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel centerWelcomePanel = new JPanel(new GridLayout(4, 1, 20, 20));
        centerWelcomePanel.add(titleLabel);
        centerWelcomePanel.add(welcomeMessage);
        centerWelcomePanel.add(creatorDetails);
        
        JPanel buttonWelcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWelcomePanel.add(enterButton);
        centerWelcomePanel.add(buttonWelcomePanel);

        welcomePanel.add(centerWelcomePanel, BorderLayout.CENTER);

        //
        //
        //
        // --- Main Management Panel ---
        //
        //
        //

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250)); // Light gray background

        // Table setup
        String[] columnNames = {"ID", "Name", "Department", "Base Salary", "Performance", "Bonus", "Fine", "Total Salary", "Type", "Special Info"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        
        // Set column widths for better display
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Department
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(90);  // Base Salary
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(90);  // Performance
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Bonus
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(60);  // Fine
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(90);  // Total Salary
        employeeTable.getColumnModel().getColumn(8).setPreferredWidth(80);  // Type
        employeeTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Special Info
        
        // Style the table for modern appearance
        employeeTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        employeeTable.setRowHeight(28);
        employeeTable.setShowGrid(true);
        employeeTable.setGridColor(new Color(230, 230, 230));
        employeeTable.setSelectionBackground(new Color(184, 207, 229));
        employeeTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        employeeTable.getTableHeader().setBackground(new Color(240, 240, 240));
        employeeTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(new Color(248, 249, 250));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        RoundedButton loadButton = new RoundedButton("Load");
        RoundedButton addButton = new RoundedButton("Add");
        RoundedButton updateButton = new RoundedButton("Update");
        RoundedButton deleteButton = new RoundedButton("Delete");
        
        controlPanel.add(loadButton);
        controlPanel.add(addButton);
        controlPanel.add(updateButton);
        controlPanel.add(deleteButton);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        searchPanel.setBackground(new Color(248, 249, 250));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        RoundedTextField searchField = new RoundedTextField(20);
        RoundedButton searchButton = new RoundedButton("Search by Name");
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Sorting panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        sortPanel.setBackground(new Color(248, 249, 250));
        sortPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sortPanel.add(sortLabel);
        
        String[] sortOptions = {"Default", "ID", "Name", "Department", "Salary (Desc)", "Performance"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sortPanel.add(sortComboBox);

        // Top panel to hold controls and search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controlPanel, BorderLayout.WEST);
        topPanel.add(sortPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to card layout
        cardPanel.add(welcomePanel, WELCOME_PANEL);
        cardPanel.add(mainPanel, MAIN_PANEL);

        // Add card panel to frame
        add(cardPanel);

        // Add Action Listeners
        loadButton.addActionListener(e -> loadEmployeeData());
        addButton.addActionListener(e -> showAddEmployeeDialog());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        searchButton.addActionListener(e -> searchEmployee(searchField.getText()));
        sortComboBox.addActionListener(e -> {
            String selection = (String) sortComboBox.getSelectedItem();
            if (selection == null) return;
            
            switch (selection) {
                case "ID":
                    employeeService.sortEmployees(EmployeeService.SortCriteria.BY_ID);
                    break;
                case "Name":
                    employeeService.sortEmployees(EmployeeService.SortCriteria.BY_NAME);
                    break;
                case "Department":
                    employeeService.sortEmployees(EmployeeService.SortCriteria.BY_DEPARTMENT);
                    break;
                case "Salary (Desc)":
                    employeeService.sortEmployees(EmployeeService.SortCriteria.BY_SALARY);
                    break;
                case "Performance":
                    employeeService.sortEmployees(EmployeeService.SortCriteria.BY_PERFORMANCE_RATING);
                    break;
                default:
                    // "Default" will reload the original order from the file
                    employeeService.loadEmployeesFromFile("employee_data.csv");
                    break;
            }
            refreshTable(employeeService.getEmployees());
        });
    }

    private void showAddEmployeeDialog() {
        AddEmployeeDialog dialog = new AddEmployeeDialog(this, employeeService);
        dialog.setVisible(true);
        // After the dialog is closed, refresh the table to show the new employee
        refreshTable(employeeService.getEmployees());
    }

    private void refreshTable(List<Employee> employees) {
        tableModel.setRowCount(0); // Clear existing data
        for (Employee emp : employees) {
            Vector<Object> row = new Vector<>();
            row.add(emp.getId());
            row.add(emp.getName());
            row.add(emp.getDepartment());
            row.add(String.format("%.2f", emp.getBaseSalary()));
            row.add(emp.getPerformanceRating());
            row.add(String.format("%.2f", emp.getBonus()));
            row.add(String.format("%.2f", emp.getFine()));
            row.add(String.format("%.2f", emp.calculateSalary()));
            row.add(emp.getClass().getSimpleName());
            
            // Add special information based on employee type
            String specialInfo = "";
            if (emp instanceof Manager) {
                Manager manager = (Manager) emp;
                specialInfo = "Subordinates: " + manager.getSubordinatesManaged();
            } else if (emp instanceof Intern) {
                specialInfo = "Intern";
            } else {
                specialInfo = "Regular";
            }
            row.add(specialInfo);
            
            tableModel.addRow(row);
        }
    }

    private void loadEmployeeData() {
        employeeService.loadEmployeesFromFile("employee_data.csv");
        refreshTable(employeeService.getEmployees());
    }

    private void addEmployee() {
        // This method is now replaced by showAddEmployeeDialog()
        // but is kept to avoid breaking changes if it were referenced elsewhere.
        showAddEmployeeDialog();
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeId = (String) tableModel.getValueAt(selectedRow, 0);
        employeeService.findEmployeeById(employeeId).ifPresent(employee -> {
            showUpdateEmployeeDialog(employee);
        });
    }

    private void showUpdateEmployeeDialog(Employee employee) {
        JDialog updateDialog = new JDialog(this, "Update Employee - " + employee.getName(), true);
        updateDialog.setSize(500, 600);
        updateDialog.setLocationRelativeTo(this);
        updateDialog.setLayout(new BorderLayout());

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Form fields
        RoundedTextField nameField = new RoundedTextField(employee.getName(), 20);
        RoundedTextField departmentField = new RoundedTextField(employee.getDepartment(), 20);
        RoundedTextField baseSalaryField = new RoundedTextField(String.valueOf(employee.getBaseSalary()), 20);
        RoundedTextField performanceField = new RoundedTextField(employee.getPerformanceRating(), 20);
        RoundedTextField bonusField = new RoundedTextField(String.valueOf(employee.getBonus()), 20);
        RoundedTextField fineField = new RoundedTextField(String.valueOf(employee.getFine()), 20);
        
        // Manager-specific field
        RoundedTextField subordinatesField = new RoundedTextField(20);
        subordinatesField.setEnabled(false);
        if (employee instanceof Manager) {
            subordinatesField.setText(String.valueOf(((Manager) employee).getSubordinatesManaged()));
            subordinatesField.setEnabled(true);
        }

        // Add fields to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Base Salary:"), gbc);
        gbc.gridx = 1;
        formPanel.add(baseSalaryField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Performance Rating:"), gbc);
        gbc.gridx = 1;
        formPanel.add(performanceField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Bonus:"), gbc);
        gbc.gridx = 1;
        formPanel.add(bonusField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Fine:"), gbc);
        gbc.gridx = 1;
        formPanel.add(fineField, gbc);

        if (employee instanceof Manager) {
            gbc.gridx = 0; gbc.gridy = 6;
            formPanel.add(new JLabel("Subordinates Managed:"), gbc);
            gbc.gridx = 1;
            formPanel.add(subordinatesField, gbc);
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        RoundedButton saveButton = new RoundedButton("Save Changes");
        RoundedButton cancelButton = new RoundedButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                // Update basic fields
                String newName = nameField.getText().trim();
                if (!newName.isEmpty()) {
                    employee.setName(newName);
                }

                String newDept = departmentField.getText().trim();
                if (!newDept.isEmpty()) {
                    employee.setDepartment(newDept);
                }

                double newBaseSalary = Double.parseDouble(baseSalaryField.getText().trim());
                employee.setBaseSalary(newBaseSalary);

                String newPerformance = performanceField.getText().trim();
                if (!newPerformance.isEmpty()) {
                    employee.setPerformanceRating(newPerformance);
                }

                // Reset and set new bonus/fine values
                double currentBonus = employee.getBonus();
                double currentFine = employee.getFine();
                
                double newBonus = Double.parseDouble(bonusField.getText().trim());
                double newFine = Double.parseDouble(fineField.getText().trim());
                
                // Adjust bonus and fine by the difference
                if (newBonus != currentBonus) {
                    if (newBonus > currentBonus) {
                        employee.addBonus(newBonus - currentBonus);
                    } else {
                        // For reducing bonus, we'd need a method to set it directly
                        // For now, let's create a simple workaround
                        setEmployeeBonus(employee, newBonus);
                    }
                }
                
                if (newFine != currentFine) {
                    if (newFine > currentFine) {
                        employee.addFine(newFine - currentFine);
                    } else {
                        setEmployeeFine(employee, newFine);
                    }
                }

                // Update Manager-specific field
                if (employee instanceof Manager && subordinatesField.isEnabled()) {
                    int newSubordinates = Integer.parseInt(subordinatesField.getText().trim());
                    ((Manager) employee).setSubordinatesManaged(newSubordinates);
                }

                employeeService.saveChanges();
                refreshTable(employeeService.getEmployees());
                updateDialog.dispose();
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(updateDialog, "Please enter valid numeric values for salary, bonus, fine, and subordinates.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> updateDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add info panel
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Employee Type: " + employee.getClass().getSimpleName()));
        infoPanel.add(new JLabel(" | Current Total Salary: $" + String.format("%.2f", employee.calculateSalary())));

        updateDialog.add(infoPanel, BorderLayout.NORTH);
        updateDialog.add(formPanel, BorderLayout.CENTER);
        updateDialog.add(buttonPanel, BorderLayout.SOUTH);

        updateDialog.setVisible(true);
    }

    // Helper methods to set bonus and fine directly
    private void setEmployeeBonus(Employee employee, double newBonus) {
        employee.setBonus(newBonus);
    }

    private void setEmployeeFine(Employee employee, double newFine) {
        employee.setFine(newFine);
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeId = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete employee " + employeeId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (employeeService.deleteEmployee(employeeId)) {
                refreshTable(employeeService.getEmployees());
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchEmployee(String name) {
        if (name == null || name.trim().isEmpty()) {
            refreshTable(employeeService.getEmployees()); // Show all if search is empty
        } else {
            List<Employee> results = employeeService.findEmployeesByName(name);
            refreshTable(results);
        }
    }

    // Custom rounded text field class
    private static class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(int columns) {
            this("", columns);
        }

        public RoundedTextField(String text, int columns) {
            super(text, columns);
            this.radius = 10;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Draw border
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Custom rounded button class
    private static class RoundedButton extends JButton {
        private int radius;
        private Color hoverColor;
        private Color normalColor;
        private boolean isHovered = false;

        public RoundedButton(String text) {
            this(text, 15);
        }

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            this.normalColor = new Color(70, 130, 180); // Steel blue
            this.hoverColor = new Color(100, 149, 237); // Cornflower blue
            
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD));
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw button background
            if (getModel().isPressed()) {
                g2.setColor(normalColor.darker());
            } else if (isHovered) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(normalColor);
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Draw text
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(getText());
            int textHeight = fm.getAscent();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() + textHeight) / 2 - 2;
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width += 20; // Add some padding
            size.height += 10;
            return size;
        }
    }
}
