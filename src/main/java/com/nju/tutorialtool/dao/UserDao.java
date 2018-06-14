package com.nju.tutorialtool.dao;

import com.nju.tutorialtool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Integer> {
    @Query("select max(user.id) from User user")
    Integer findMaxId();
}
