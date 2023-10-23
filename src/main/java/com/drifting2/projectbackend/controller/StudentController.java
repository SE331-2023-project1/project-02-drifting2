package com.drifting2.projectbackend.controller;

import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.service.StudentService;
import com.drifting2.projectbackend.util.LabMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {
    List<Student> studentList;
    final StudentService studentService;
    @GetMapping("students")
    public ResponseEntity<?> getStudentLists(@RequestParam(value ="_limit", required = false) Integer perPage,
                                            @RequestParam(value = "_page", required = false) Integer page,
                                            @RequestParam(value = "title", required = false) String title) {
                                                
        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Student> pageOutput;
        if (title == null) {
            pageOutput = studentService.getStudents(perPage,page);
        }else{
            pageOutput = studentService.getStudents(title,PageRequest.of(page-1,perPage));
        }
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getStudentDTO(pageOutput.getContent()),responseHeader,HttpStatus.OK);

    }

    @GetMapping("students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") Long id) {
        Student output = studentService.getStudent(id);
        if (output != null){
            return ResponseEntity.ok(LabMapper.INSTANCE.getStudentDTO(output));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given id is not found");
        }
    }

    @PostMapping("/savestudent")
    public ResponseEntity<?> addStudent(@RequestBody Student Student){
        Student output = studentService.save(Student);
        return ResponseEntity.ok(LabMapper.INSTANCE.getStudentDTO(output));
    }

    @GetMapping("students/searchByStudentId")
    public ResponseEntity<?> getStudent(@RequestParam String id) {
        Student output = studentService.getStudent(id);
        if (output != null){
            return ResponseEntity.ok(LabMapper.INSTANCE.getStudentDTO(output));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given id is not found");
        }
    }

    @GetMapping("students/searchByStudentFirstname")
    public ResponseEntity<?> getStudentByFName(@RequestParam String firstname) {
        Student output = studentService.getStudentByFName(firstname);
        if (output != null){
            return ResponseEntity.ok(LabMapper.INSTANCE.getStudentDTO(output));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given id is not found");
        }
    }
}
