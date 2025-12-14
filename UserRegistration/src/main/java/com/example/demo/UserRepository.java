package com.example.demo;
//import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String>{
	Optional<User> findByEmail(String email);
}
