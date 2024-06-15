package com.test.Testapp.controller;

import com.test.Testapp.entity.Student;
import com.test.Testapp.model.request.StudentRequest;
import com.test.Testapp.model.request.StudentSearchRequest;
import com.test.Testapp.model.response.PagingResponse;
import com.test.Testapp.model.response.StudentResponse;
import com.test.Testapp.model.response.WebResponse;
import com.test.Testapp.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/rooms")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody StudentRequest studentRequest) {
        StudentResponse studentResponse = studentService.registerStudent(studentRequest);
        WebResponse<StudentResponse> response = WebResponse.<StudentResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Student Has Registered")
                .data(studentResponse)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<?> getAllRooms(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer minMark,
            @RequestParam(required = false) Integer maxMark) {
        StudentSearchRequest studentSearchRequest = StudentSearchRequest.builder()
                .id(id)
                .name(name)
                .status(status)
                .minMark(minMark)
                .maxMark(maxMark)
                .page(page)
                .size(size)
                .build();
        Page<Student> roomList = studentService.getAllStudent(studentSearchRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(roomList.getTotalPages())
                .totalElements(roomList.getTotalElements())
                .build();
        WebResponse<?> response = WebResponse.<List<Student>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get All Student List")
                .paging(pagingResponse)
                .data(roomList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable String id) {
        Student findStudentById = studentService.getById(id);
        WebResponse<Student> response = WebResponse.<Student>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Student By ID")
                .data(findStudentById)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> UpdateRoomById(@RequestBody Student student) {
        Student updateRoomById = studentService.update(student);
        WebResponse<Student> response = WebResponse.<Student>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Update Student By ID")
                .data(updateRoomById)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteRoomById(@PathVariable String id) {
        studentService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete Student Data")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }
}