package com.drifting2.projectbackend.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.drifting2.projectbackend.entity.CommentHistory;
import com.drifting2.projectbackend.entity.CommentHistoryDTO;
import com.drifting2.projectbackend.entity.CommentMessage;
import com.drifting2.projectbackend.entity.CommentMessageDTO;
import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.entity.StudentDTO;
import com.drifting2.projectbackend.entity.Teacher;
import com.drifting2.projectbackend.entity.TeacherDTO;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);
    StudentDTO getStudentDTO(Student student);
    List<StudentDTO> getStudentDTO(List<Student> students);
    TeacherDTO getTeacherDTO(Teacher teacher);
    List<TeacherDTO> getTeacherDTO(List<Teacher> teachers);
    CommentMessageDTO getCommentMessageDTO(CommentMessage commentMessage);
    List<CommentMessageDTO> getCommentMessageDTO(List<CommentMessage> commentMessage);
    CommentHistoryDTO getCommentHistoryDTO(CommentHistory commentHistory);
    List<CommentHistoryDTO> getCommentHistoryDTO(List<CommentHistory> commentHistory);
}
