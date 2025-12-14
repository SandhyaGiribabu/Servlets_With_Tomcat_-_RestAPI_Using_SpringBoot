package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.repository.BookRepository;  // adjust if your repo is in another package
import entity.Book;  // adjust if your entity is in another package

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner test(BookRepository repo, MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println("Database Name: " + mongoTemplate.getDb().getName());
            System.out.println("Collections: ");
            mongoTemplate.getDb().listCollectionNames().forEach(System.out::println);

            System.out.println("\nBooks found:");
            repo.findAll().forEach(System.out::println);
        };
    }
}
