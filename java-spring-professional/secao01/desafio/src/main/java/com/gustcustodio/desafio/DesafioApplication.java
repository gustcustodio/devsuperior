package com.gustcustodio.desafio;

import com.gustcustodio.desafio.entities.Order;
import com.gustcustodio.desafio.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {
    Scanner scanner = new Scanner(System.in);

    @Autowired
    OrderService orderService;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        SpringApplication.run(DesafioApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.print("Enter the order code: ");
        int code = scanner.nextInt();
        System.out.print("Enter the value of order: ");
        double basic = scanner.nextDouble();
        System.out.print("Enter the discount of order: ");
        double discount = scanner.nextDouble();

        Order order = new Order(code, basic, discount);

        System.out.printf(
                "Pedido código %d%nValor total: R$ %.2f",
                order.getCode(), orderService.order(order)
        );

    }

}
