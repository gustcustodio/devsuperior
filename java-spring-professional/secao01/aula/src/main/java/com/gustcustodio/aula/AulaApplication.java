package com.gustcustodio.aula;

import com.gustcustodio.aula.entities.Employee;
import com.gustcustodio.aula.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AulaApplication implements CommandLineRunner {

    @Autowired
    SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(AulaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee("Maria", 4000.0);
        System.out.println(salaryService.netSalary(employee));
    }

}
