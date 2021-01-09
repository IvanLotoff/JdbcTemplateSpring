package com.example.demo.repository;

import com.example.demo.models.Department;
import com.example.demo.utils.PreparedStatementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcDepartmentRepository implements DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(JdbcDepartmentRepository.class);

    @Autowired
    public JdbcDepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Object> getArgsWithoutId(Department department) {
        return new ArrayList<>() {
            {
                add(department.getName());
                add(department.getNumber_of_employees());
                add(department.getAverage_salary());
                add(department.getProfit_generated());
            }
        };
    }

    @Override
    public Department save(Department department) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into " +
                    "department_final(name,number_of_employees,average_salary,profit_generated) " +
                    "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            return PreparedStatementBuilder.getInstance(ps)
                    .setString(1,department.getName())
                    .setInt(2,department.getNumber_of_employees())
                    .setDouble(3,department.getAverage_salary())
                    .setDouble(4,department.getProfit_generated())
                    .build();
        };
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator,holder);
        department.setId(holder.getKey().longValue());
        return department;
    }

    @Override
    public void update(Department department) {
        String sqlTxt = "update firm.department_final " +
                "set name = ?, " +
                "number_of_employees = ?, " +
                "average_salary = ?, " +
                "profit_generated = ?" +
                "where id = ?";
        var args = getArgsWithoutId(department);
        args.add(department.getId());
        jdbcTemplate.update(sqlTxt, args);
    }

    @Override
    public void delete(long id) {
        String sqlTxt = "delete from firm.department_final " +
                "where id =?";
        jdbcTemplate.update(sqlTxt, id);
    }

    @Override
    public Department getMostProfitableDepartment() {
        String sqlTxt = "SELECT * FROM firm.department_final " +
                "ORDER BY profit_generated DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sqlTxt, new DepartmentRowMapper());
    }

    @Override
    public Department getDepartmentWithMostEmployees() {
        String sqlTxt = "SELECT * FROM firm.department_final " +
                "ORDER BY number_of_employees DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sqlTxt, new DepartmentRowMapper());
    }

    @Override
    public Department getDepartmentWithHighestSalary() {
        String sqlTxt = "SELECT * FROM firm.department_final " +
                "ORDER BY average_salary DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sqlTxt, new DepartmentRowMapper());
    }

    @Override
    public Optional<Department> findDepartmentById(long id) {
        String sqlTxt = "SELECT * FROM firm.department_final " +
                "WHERE id =?";
        List<Department> deps = jdbcTemplate.query(sqlTxt,new DepartmentRowMapper(), id);
        if(deps.isEmpty())
            return Optional.empty();
        return Optional.of(deps.get(0));
    }

    @Override
    public Optional<List<Department>> findDepartmentByName(String name) {
        String sqlTxt = "SELECT * FROM firm.department_final " +
                "WHERE name =?";
        List<Department> deps = jdbcTemplate.query(sqlTxt,new DepartmentRowMapper(), name);
        if(deps.isEmpty())
            return Optional.empty();
        return Optional.of(deps);
    }


    @Override
    public List<Department> getAllDepartments() {
        String sqlTxt = "SELECT * FROM firm.department_final";
        return jdbcTemplate.query(sqlTxt,new DepartmentRowMapper());
    }
    private static class DepartmentRowMapper implements RowMapper<Department>{

        @Override
        public Department mapRow(ResultSet rs, int i) throws SQLException {
            Department department = new Department();
            department.setId(rs.getLong("id"));
            department.setName(rs.getString("name"));
            department.setAverage_salary(rs.getDouble("average_salary"));
            department.setNumber_of_employees(rs.getInt("number_of_employees"));
            department.setProfit_generated(rs.getDouble("profit_generated"));
            return department;
        }
    }
}
