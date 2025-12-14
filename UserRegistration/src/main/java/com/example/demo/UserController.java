package com.example.demo;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/users_register")
@CrossOrigin(origins="*")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // -----------------------------
    // 1. POST: Add a new user
    // -----------------------------
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // -----------------------------
    // 2. GET: Retrieve all users
    // -----------------------------
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // -----------------------------
    // 3. GET: Retrieve a user by email
    // -----------------------------
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        return userOpt.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // -----------------------------
    // 4. PUT: Update a user by email
    // -----------------------------
    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User userDetails) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            existingUser.setName(userDetails.getName());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setMobile(userDetails.getMobile());
            existingUser.setEmail(userDetails.getEmail());
            User updated = userService.createUser(existingUser); // save changes
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // -----------------------------
    // 5. DELETE: Delete a user by email
    // -----------------------------
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        boolean deleted = userService.deleteUserByEmail(email);
        if (deleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User register) {
	    User user = userService.validateLogin(register.getEmail(), register.getPassword());
	    
	    HashMap<String, String> response = new HashMap<>();
	    if (user != null) {
	    	response.put("name", user.getName());
	    	return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	    } else {
	    	response.put("message", "Invalid credentials");
	    	return new ResponseEntity<Map<String, String>>(response, HttpStatus.UNAUTHORIZED);
	    }
    }
}
