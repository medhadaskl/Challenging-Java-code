package org.example;
import java.sql.*;
import java.util.*;

//This application manages employee records in a PostgreSQL database.
//It provides functionalities to insert, update, delete, and find employees.
// Represents an employee with an ID, name, salary, and department.

class Employee {
    private int id;
    private String name;
    private double salary;
    private String department;


    public Employee(int id, String name, double salary,String department) {
        this.id = id; this.name = name; this.salary = salary;
        this.department = department;

    }
//Returns the employee ID,name,slary, department
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    //Getter for department


    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary + ", department='" + department + "'}";
    }
}
// Interface for employee data access operations.
interface EmployeeDAO {
    // Inserts a new employee into the database , update the database , delete the data
    void insert(Employee e);
    void update(Employee e);
    void delete(int id);
    Employee findById(int id);
    List<Employee> findAll();
}
// Implementation of the EmployeeDAO interface for PostgreSQL database.

class EmployeeDAOImpl implements EmployeeDAO {

    private Connection getConnection() throws SQLException {
        //Establishes a connection to the PostgreSQL database and return the database connection.
        //throws SQLException if a database access error occurs.

        String url = "jdbc:postgresql://localhost:5432/employeedb";
        String user = "postgres";
        String password = "1234";

        return DriverManager.getConnection(url, user, password);
    }

    public void insert(Employee e) {
        String sql = "INSERT INTO employee (id, name, salary, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getId());
            ps.setString(2, e.getName());
            ps.setDouble(3, e.getSalary());
            ps.setString(4, e.getDepartment());
            ps.executeUpdate();
            System.out.println("Inserted: " + e);
        } catch (SQLException ex) {
            System.out.println("Insert error: " + ex.getMessage());
        }
    }

    public void update(Employee e) {
        String sql = "UPDATE employee SET name=?, salary=?, department=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());
            ps.setString(3, e.getDepartment());
            ps.setInt(4, e.getId());
            ps.executeUpdate();
            System.out.println("Updated: " + e);
        } catch (SQLException ex) {
            System.out.println("Update error: " + ex.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Deleted employee with ID: " + id);
        } catch (SQLException ex) {
            System.out.println("Delete error: " + ex.getMessage());
        }
    }

    public Employee findById(int id) {
        String sql = "SELECT * FROM employee WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),rs.getString("department"));
            }
        } catch (SQLException ex) {
            System.out.println("Find error: " + ex.getMessage());
        }
        return null;
    }

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getDouble("salary"),rs.getString("department")));
            }
        } catch (SQLException ex) {
            System.out.println("FindAll error: " + ex.getMessage());
        }
        return list;
    }
}
public class EmployeeApp {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAOImpl();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Insert Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Find Employee by ID");
            System.out.println("4. Find All Employees");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                // Insert
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    dao.insert(new Employee(id, name, salary,department));
                    break;
                // Update
                case 2:
                    System.out.print("Enter ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Salary: ");
                    double newSalary = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String updateDepartment = scanner.nextLine();
                    dao.update(new Employee(updateId, newName, newSalary,updateDepartment));
                    break;
                // Find by ID
                case 3:
                    System.out.print("Enter ID to find: ");
                    int findId = scanner.nextInt();
                    Employee found = dao.findById(findId);
                    if (found != null) {
                        System.out.println("Found: " + found);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 4: // Find All
                    System.out.println("All employees:");
                    dao.findAll().forEach(System.out::println);
                    break;

                case 5: // Delete
                    System.out.print("Enter ID to delete: ");
                    int deleteId = scanner.nextInt();
                    dao.delete(deleteId);
                    break;

                case 6: // Exit
                    running = false;
                    System.out.println("Exiting ");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}

