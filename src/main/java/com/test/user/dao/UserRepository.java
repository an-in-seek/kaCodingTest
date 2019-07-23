package com.test.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.user.vo.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
