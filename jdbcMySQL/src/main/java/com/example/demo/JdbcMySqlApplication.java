package com.example.demo;

import com.example.demo.models.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.JdbcDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class JdbcMySqlApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMySqlApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(JdbcMySqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS 'department'" +
                "('id' int(11) NOT NULL auto_increment " +
                "'name' varchar(20) NOT NULL " +
                "'number_of_employees' int(10) " +
                "'average_salary' double(10) " +
                "'profit_generated' double(10))");*/
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS department_final (\n" +
                "    id bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "    name varchar(100) NOT NULL,\n" +
                "    number_of_employees integer(20),\n" +
                "    average_salary double, \n" +
                "    profit_generated double ,\n" +
                "    PRIMARY KEY (id)\n" +
                ");");
    }
    @Bean
    Docket docs(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }
}
