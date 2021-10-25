package com.backend.backend.repositories;

import com.backend.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

//    @Query(nativeQuery = true, value = "select * from tb_users")
//    List<User> findAllUsers();
}
