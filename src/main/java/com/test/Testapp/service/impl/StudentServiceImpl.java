package com.test.Testapp.service.impl;

import com.test.Testapp.entity.Student;
import com.test.Testapp.model.request.StudentRequest;
import com.test.Testapp.model.request.StudentSearchRequest;
import com.test.Testapp.model.response.StudentResponse;
import com.test.Testapp.repository.StudentRepository;
import com.test.Testapp.service.StudentService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public StudentResponse registerStudent(StudentRequest studentRequest) {
        Student student = Student.builder()
                .name(studentRequest.getName())
                .department(studentRequest.getDepartment())
                .mark(studentRequest.getMark())
                .build();
        if (student.getMark()>=40){
            student.setStatus("Pass");
        }else {
            student.setStatus("Fail");
        }
        Student newstudent = studentRepository.saveAndFlush(student);
        return StudentResponse.builder()
                .name(newstudent.getName())
                .department(newstudent.getDepartment())
                .mark(newstudent.getMark())
                .status(newstudent.getStatus())
                .build();
    }

    @Override
    public Page<Student> getAllStudent(StudentSearchRequest request) {
        if (request.getPage() == null || request.getPage() <= 0) {
                request.setPage(1);
        }

            if (request.getSize() == null || request.getSize() <= 0) {
                request.setSize(10); // Jumlah default item per halaman
            }

            // Buat objek Pageable untuk paging
            Pageable pageable = PageRequest.of(
                    request.getPage() - 1, request.getSize());

            Specification<Student> spec = (root, query, criteriaBuilder) -> {
                // Inisialisasi list of predicates
                List<Predicate> predicates = new ArrayList<>();

                // Tambahkan predikat jika nilai parameter tidak null atau kosong
                if (request.getId() != null && !request.getId().isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("id"), request.getId()));
                }
                if (request.getName() != null && !request.getName().isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), request.getName()));
                }
                if (request.getStatus() != null && !request.getStatus().isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
                }
                if (request.getMinMark() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("Mark"), request.getMinMark()));
                }
                if (request.getMaxMark() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("Mark"), request.getMaxMark()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            // Dapatkan daftar room berdasarkan spesifikasi dan paging
            return studentRepository.findAll(spec, pageable);
        }

    @Override
    public Student getById(String id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) return  optionalStudent.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student Not Found");
    }


    @Override
    public Student update(Student student) {
        this.getById(student.getId());
        return studentRepository.save(student);
    }


    @Override
    public void deleteById(String id) {
        this.getById(id);
        studentRepository.deleteById(id);
    }

}
