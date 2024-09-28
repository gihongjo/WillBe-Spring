package com.aidis.teama.student.controller;

import com.aidis.teama.student.model.StudentDTO;
import com.aidis.teama.student.model.StudentAddRequest;
import com.aidis.teama.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/student")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 2. 요청을 받는 POST 메서드 생성
    @PostMapping("/add")
    public ResponseEntity<StudentDTO> add(
            @Validated
            @RequestBody
            StudentAddRequest studentAddRequest
    ) {
        log.info(studentAddRequest.toString());
        return studentService.add(studentAddRequest);
    }



}
