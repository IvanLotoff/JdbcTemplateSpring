package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    private Long id;
    private String name;
    private Integer number_of_employees;
    private Double average_salary;
    private Double profit_generated;
}
