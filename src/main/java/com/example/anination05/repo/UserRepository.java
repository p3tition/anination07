package com.example.anination05.repo;

import com.example.anination05.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    @Query("SELECT u.createdAt, COUNT(u) FROM Users u GROUP BY u.createdAt")
    List<Object[]> countUsersByCreatedAt();
    @Query("SELECT u.createdAt, " +
            "(SELECT COUNT(u2) FROM Users u2 WHERE u2.createdAt = u.createdAt) AS countCreatedOnDate, " +
            "(SELECT COUNT(u3) FROM Users u3 WHERE u3.createdAt < u.createdAt) AS countCreatedBeforeDate " +
            "FROM Users u GROUP BY u.createdAt")
    List<Object[]> countUsersByCreatedAtAndBefore();
    @Query("SELECT u.createdAt, COUNT(u) FROM Users u GROUP BY u.createdAt ORDER BY u.createdAt")
    List<Object[]> countUsersByCreatedAtWithOrder();
}
