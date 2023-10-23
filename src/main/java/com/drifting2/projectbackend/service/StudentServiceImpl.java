package com.drifting2.projectbackend.service;

import com.drifting2.projectbackend.dao.StudentDao;
import com.drifting2.projectbackend.dao.TeacherDao;
import com.drifting2.projectbackend.entity.Teacher;
import lombok.RequiredArgsConstructor;
import com.drifting2.projectbackend.entity.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    final StudentDao studentDao;
    final TeacherDao teacherDao;
    @Override
    public Integer getStudentSize() {
        return studentDao.getStudentSize();
    }

    @Override
    public Page<Student> getStudents(Integer pageSize, Integer page) {
        return studentDao.getStudents(pageSize, page);
    }

    @Override
    public Student getStudent(Long id) {
        return studentDao.getStudent(id);
    }

    @Override
    @Transactional
    public Student save(Student student) {
        if(student.getAdvisor() != null){
            if(student.getAdvisor().getId() != 0){
                Teacher teacher = teacherDao.findById(student.getAdvisor().getId()).orElse(null);
                teacher.getAdvisee().add(student);
            } else {
                student.setAdvisor(null);
            }
        }
        return studentDao.save(student);
    }

    @Override
    public Page<Student> getStudents(String title, Pageable pageable) {
        return studentDao.getStudents(title,pageable);
    }

}