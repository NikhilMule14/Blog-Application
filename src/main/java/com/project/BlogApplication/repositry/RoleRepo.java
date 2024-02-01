package com.project.BlogApplication.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.BlogApplication.entity.Role;

public interface RoleRepo extends JpaRepository<Role,  Integer> {

}
