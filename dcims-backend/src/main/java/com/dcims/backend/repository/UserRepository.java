package com.dcims.backend.repository;

import com.dcims.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySsoId(String ssoId);
}