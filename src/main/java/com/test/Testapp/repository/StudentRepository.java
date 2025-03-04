package com.test.Testapp.repository;

import com.test.Testapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

}
