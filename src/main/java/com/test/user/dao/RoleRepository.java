package com.test.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.user.vo.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
