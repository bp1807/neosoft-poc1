package com.neosoft.poc1.repo;

import com.neosoft.poc1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
