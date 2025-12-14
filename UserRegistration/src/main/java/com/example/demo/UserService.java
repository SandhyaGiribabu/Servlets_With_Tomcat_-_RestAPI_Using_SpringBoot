package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // -----------------------------
    // 1. Get all users
    // -----------------------------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // -----------------------------
    // 2. Get user by ID
    // -----------------------------
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // -----------------------------
    // 3. Get user by email
    // -----------------------------
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // -----------------------------
    // 4. Create or save user
    // -----------------------------
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // -----------------------------
    // 5. Update user by ID
    // -----------------------------
    public Optional<User> updateUser(String id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setMobile(userDetails.getMobile());
            return userRepository.save(existingUser);
        });
    }

    // -----------------------------
    // 6. Delete user by ID
    // -----------------------------
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // -----------------------------
    // 7. Delete user by email
    // -----------------------------
    public boolean deleteUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }
    
    public User validateLogin(String email, String password) {
    	 Optional<User> useropt = userRepository.findByEmail(email);
    	 if (useropt.isPresent()) {
    		 User reg = useropt.get();
    		 
    		 if (reg.getPassword().equals(password)) {
    			 return reg;
    			 }
    		 }
    	 return null; 
    	 }
    	

}
