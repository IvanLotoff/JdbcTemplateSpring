package com.example.demo.RestControllers;

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.models.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    DepartmentRepository repository;

    @Autowired
    public RestController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/insert")
    public void insert(@RequestParam(required = false) String name,
                       @RequestParam(required = false) double profit_generated,
                       @RequestParam(required = false) double average_salary,
                       @RequestParam(required = false) int number_of_employees){
        repository.save(Department.builder()
                .name(name)
                .profit_generated(profit_generated)
                .number_of_employees(number_of_employees)
                .average_salary(average_salary)
                .build());
    }
    @PostMapping("/insertPost")
    public void insertPost(@RequestBody Department department){
        repository.save(department);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        repository.delete(id);
    }
    @GetMapping("/update")
    public void update(@RequestParam long id,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Double profit_generated, //<--- Double, чтобы не было NPE
                       @RequestParam(required = false) Double average_salary, //<--- Double, чтобы не было NPE
                       @RequestParam(required = false) int number_of_employees){
        repository.update(Department.builder()
                .id(id)
                .name(name)
                .profit_generated(profit_generated)
                .number_of_employees(number_of_employees)
                .average_salary(average_salary)
                .build());
    }
    @GetMapping("/mostProfitableDepartment")
    public ResponseEntity<Department> getMostProfitableDep(){
        Department mostProfitableDepartment = repository.getMostProfitableDepartment();
        return ResponseEntity.ok(mostProfitableDepartment);
    }
    @GetMapping("/getMostEmployedDepartment")
    public ResponseEntity<Department> getMostEmployedDep(){
        Department departmentWithMostEmployees = repository.getDepartmentWithMostEmployees();
        return ResponseEntity.ok(departmentWithMostEmployees);
    }
    @GetMapping("/getDepartmentWithHighestSalary")
    public ResponseEntity<Department> getDepartmentWithHighestSalary(){
        Department DepartmentWithHighestSalary = repository.getDepartmentWithHighestSalary();
        return ResponseEntity.ok(DepartmentWithHighestSalary);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Department> findById(@PathVariable long id){
        Department department = repository.findDepartmentById(id)
                .orElseThrow(ItemNotFoundException::new);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Department>> findByName(@PathVariable String name){
        List<Department> departments = repository.findDepartmentByName(name)
                .orElseThrow(ItemNotFoundException::new);
        return ResponseEntity.ok(departments);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDeps(){
        return ResponseEntity.ok(repository.getAllDepartments());
    }
}
