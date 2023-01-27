package com.sogogo.bo.repo;

import com.sogogo.bo.model.UserSogogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserSogogo, Long> {
    Optional<UserSogogo> findByUsername(String username);
    Boolean existsByUsername(String username);
}
