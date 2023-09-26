package com.example.application.data.repository;

import com.example.application.data.model.Signup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepository extends JpaRepository<Signup, Long>, JpaSpecificationExecutor<Signup> {

}
