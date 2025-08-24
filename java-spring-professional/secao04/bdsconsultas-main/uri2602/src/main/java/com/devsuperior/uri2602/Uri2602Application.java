package com.devsuperior.uri2602;

import com.devsuperior.uri2602.dto.CustomerMinDTO;
import com.devsuperior.uri2602.projections.CustomerMinProjection;
import com.devsuperior.uri2602.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2602Application implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Uri2602Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<CustomerMinProjection> list = customerRepository.search1("RS");
        List<CustomerMinDTO> dtoList = list.stream().map(CustomerMinDTO::new).collect(Collectors.toList());

        System.out.println("\n*** RESULTADO SQL RAIZ ***");
        for (CustomerMinDTO obj : dtoList) {
            System.out.println(obj);
        }
        System.out.println("\n\n");

        List<CustomerMinDTO> dtoList2 = customerRepository.search2("RS");

        System.out.println("\n*** RESULTADO JPQL ***");
        for (CustomerMinDTO obj : dtoList2) {
            System.out.println(obj);
        }

    }

}
