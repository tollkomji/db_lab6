package dao;

import model.Employee;
import java.sql.*;
import java.util.*;

public class EmployeeDAOImpl implements EmployeeDAO {
    private Connection conn;

    public EmployeeDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void addEmployee(Employee employee) {
        try (PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO employees (name, email, department) VALUES (?, ?, ?)")) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getDepartment());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM employees WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return extractEmployee(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            while (rs.next()) list.add(extractEmployee(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employee> findByDepartment(String dept) {
        List<Employee> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM employees WHERE department = ?")) {
            stmt.setString(1, dept);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(extractEmployee(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateEmployee(Employee employee) {
        try (PreparedStatement stmt = conn.prepareStatement(
            "UPDATE employees SET name = ?, email = ?, department = ? WHERE id = ?")) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getDepartment());
            stmt.setInt(4, employee.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(
            "DELETE FROM employees WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setName(rs.getString("name"));
        e.setEmail(rs.getString("email"));
        e.setDepartment(rs.getString("department"));
        return e;
    }
}
