package com.drifting2.projectbackend.controller;


import com.drifting2.projectbackend.dao.StudentDao;
import com.drifting2.projectbackend.dao.TeacherDao;
import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.entity.Teacher;
import com.drifting2.projectbackend.service.StudentService;
import com.drifting2.projectbackend.service.TeacherService;
import com.drifting2.projectbackend.util.LabMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class RelationController {

    final StudentDao studentDao;
    final TeacherDao teacherDao;
    final StudentService studentService;
    final TeacherService teacherService;

    @PostMapping("/setrelation")
    public ResponseEntity<?> setRelation(@RequestBody String teacherFirstName, @RequestBody String studentFirstName){
        Teacher teacher = teacherService.getTeachers(teacherFirstName);
        Student student = studentService.getStudent(studentFirstName);
        if(teacher != null && student != null){
            student.setAdvisor(teacher);
            teacher.getAdvisee().add(student);
            return ResponseEntity.ok(LabMapper.INSTANCE.getTeacherDTO(teacher));
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"The given id is not found");
        }
    }

}
