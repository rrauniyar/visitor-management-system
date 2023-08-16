package com.management.visitor.repository;

import com.management.visitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {


}
