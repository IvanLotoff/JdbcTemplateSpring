package com.example.demo.repository;

import com.example.demo.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    void save(Department department);
    void update(Department department);
    void delete(long id);

    Department getMostProfitableDepartment();
    Department getDepartmentWithMostEmployees();
    Department getDepartmentWithHighestSalary();

    Optional<Department> findDepartmentById(long id);
    Optional<List<Department>> findDepartmentByName(String name);

    List<Department> getAllDepartments();
}
