package com.example.demo;

import java.util.Optional;
import com.ssn.registration.entity.Register;

public interface RegRepo extends MongoRepository<Register, String> {
    Optional<Register> findByEmail(String email);
}
