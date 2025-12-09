package com.Employeestatus.Tool.Repository;

import com.Employeestatus.Tool.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmailIgnoreCase(String email);

    Optional<User> findFirstByEmail(String email);
}
