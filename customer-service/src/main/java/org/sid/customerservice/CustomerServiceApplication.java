package org.sid.customerservice;

import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);

    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(new Customer(null, "Walid", "walid@gmail.com"));
            customerRepository.save(new Customer(null, "Amine", "amine@gmail.com"));
            customerRepository.save(new Customer(null, "Test", "test@gmail.com"));
            customerRepository.findAll().forEach(c ->{
                System.out.println(c.toString());
            });

        };

    }

}
