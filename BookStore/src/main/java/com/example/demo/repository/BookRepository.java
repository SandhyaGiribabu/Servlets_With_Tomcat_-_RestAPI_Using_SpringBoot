package com.example.demo.repository;
import org.springframework.data.mongodb.repository.MongoRepository;


import entity.Book;

public interface BookRepository extends MongoRepository<Book,String>{

}
