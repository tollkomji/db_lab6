package dao;

import model.Employee;
import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    List<Employee> findByDepartment(String department);
    void updateEmployee(Employee employee);
    void deleteEmployee(int id);
}
