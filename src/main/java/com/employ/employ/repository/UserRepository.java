package com.employ.employ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employ.employ.entity.UserEntity;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
     // Spring Data JPA will automatically generate the query
    Optional<UserEntity> findByEmail(String email);
    
    // Or if you expect exactly one (throws exception if not found)
    UserEntity getByEmail(String email);
}
