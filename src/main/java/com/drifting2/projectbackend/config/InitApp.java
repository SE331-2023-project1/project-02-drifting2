package com.drifting2.projectbackend.config;

import com.drifting2.projectbackend.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.drifting2.projectbackend.entity.CommentHistory;
import com.drifting2.projectbackend.entity.CommentMessage;
import com.drifting2.projectbackend.entity.Student;
import com.drifting2.projectbackend.entity.Teacher;
import com.drifting2.projectbackend.repository.CommentHistoryRepository;
import com.drifting2.projectbackend.repository.CommentMessageRepository;
import com.drifting2.projectbackend.repository.StudentRepository;
import com.drifting2.projectbackend.repository.TeacherRepository;

import com.drifting2.projectbackend.security.user.User;
@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired

    final StudentRepository studentRepository;
    final TeacherRepository teacherRepository;
    final CommentMessageRepository commentMessageRepository;
    final CommentHistoryRepository commentHistoryRepository;
    final UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setEmail("admin@admin.com");
        userRepository.save(admin);



        Teacher t1, t2;
        t1 = teacherRepository.save(Teacher.builder()
            .teacherId("001")
            .firstname("Kana")
            .surname("Momonogi")
            .academicPosition("Lecture")
            .department("CAMT")
            .build()
        );
        t2 = teacherRepository.save(Teacher.builder()
            .teacherId("002")
            .firstname("白桃")
            .surname("はな")
            .academicPosition("Lecture")
            .department("CAMT")
            .build()
        );

        Student tempSt;
        tempSt = studentRepository.save(Student.builder()
            .studentId("622115501")
            .firstname("Alice")
            .surname("Li")
            .department("CAMT")
            .build()
        );
        tempSt.setAdvisor(t1);
        t1.getAdvisee().add(tempSt);
        CommentHistory his1 = commentHistoryRepository.save(CommentHistory.builder()
            .adviseeId(tempSt.getId())
            .advisorId(t1.getId())
            .build()
        );
        CommentHistory his2 = commentHistoryRepository.save(CommentHistory.builder()
            .adviseeId(tempSt.getId())
            .advisorId(t2.getId())
            .build()
        );
        CommentMessage msg1 = commentMessageRepository.save(CommentMessage.builder()
            .message("Knock knock")
            .sentFromAdvisor(false)
            .timeSent("X")
            .build()
        );
        msg1.setFrom(his1);
        his1.getHistory().add(msg1);
        CommentMessage msg2 = commentMessageRepository.save(CommentMessage.builder()
            .message("Who's there?")
            .sentFromAdvisor(true)
            .timeSent("X")
            .build()
        );
        msg2.setFrom(his1);
        his1.getHistory().add(msg2);
        CommentMessage msg3 = commentMessageRepository.save(CommentMessage.builder()
            .message("It's me, Daddy~~~~~~")
            .sentFromAdvisor(false)
            .timeSent("X")
            .build()
        );
        msg3.setFrom(his1);
        his1.getHistory().add(msg3);

        tempSt = studentRepository.save(Student.builder()
            .studentId("622115502")
            .firstname("Tom")
            .surname("X")
            .department("CAMT")
            .build()
        );
        tempSt.setAdvisor(t1);
        t1.getAdvisee().add(tempSt);
        tempSt = studentRepository.save(Student.builder()
            .studentId("622115503")
            .firstname("Jerry")
            .surname("Shu")
            .department("CAMT")
            .build()
        );
        tempSt.setAdvisor(t2);
        t2.getAdvisee().add(tempSt);
        tempSt = studentRepository.save(Student.builder()
            .studentId("622115504")
            .firstname("Xukun")
            .surname("Cai")
            .department("CAMT")
            .build()
        );
        tempSt.setAdvisor(t2);
        t2.getAdvisee().add(tempSt);




//        System.out.println("Init Finished.");


    }
    
}
