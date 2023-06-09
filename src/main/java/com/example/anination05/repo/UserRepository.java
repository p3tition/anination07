package com.example.anination05.repo;

import com.example.anination05.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

}
