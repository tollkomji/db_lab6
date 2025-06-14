package test;

import dao.*;
import model.Employee;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/company_db", "root", "your_password");

            EmployeeDAO dao = new EmployeeDAOImpl(conn);

            dao.addEmployee(new Employee("Ivan Petrenko", "ivan@example.com", "HR"));
            dao.addEmployee(new Employee("Olena Shevchenko", "olena@example.com", "IT"));

            System.out.println("All employees:");
            dao.getAllEmployees().forEach(e -> System.out.println(e.getName()));

            System.out.println("IT department:");
            dao.findByDepartment("IT").forEach(e -> System.out.println(e.getName()));

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
