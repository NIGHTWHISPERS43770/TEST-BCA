package com.test.Testapp.service;

import com.test.Testapp.entity.Student;
import com.test.Testapp.model.request.StudentRequest;
import com.test.Testapp.model.request.StudentSearchRequest;
import com.test.Testapp.model.response.StudentResponse;
import org.springframework.data.domain.Page;

public interface StudentService {
    StudentResponse registerStudent(StudentRequest studentRequest);

    Page<Student> getAllStudent(StudentSearchRequest request);

    Student getById(String id);

    Student update(Student student);

    void deleteById(String id);
}
