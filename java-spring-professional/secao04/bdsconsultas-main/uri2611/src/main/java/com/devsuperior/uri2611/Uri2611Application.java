package com.devsuperior.uri2611;

import com.devsuperior.uri2611.dto.MovieMinDTO;
import com.devsuperior.uri2611.projections.MovieMinProjection;
import com.devsuperior.uri2611.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Uri2611Application implements CommandLineRunner {

    @Autowired
    MovieRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Uri2611Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<MovieMinProjection> list1 = repository.search1("Action");
        List<MovieMinDTO> result1 = list1.stream().map(MovieMinDTO::new).collect(Collectors.toList());

        System.out.println("\n*** SQL RAIZ ***");
        for (MovieMinDTO obj : result1) {
            System.out.println(obj);
        }

        List<MovieMinDTO> result2 = repository.search2("Action");
        System.out.println("\n*** JPQL ***");
        for (MovieMinDTO obj : result2) {
            System.out.println(obj);
        }

    }

}
