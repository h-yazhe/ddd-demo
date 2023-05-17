package com.hyz.ddd.demo.application.service;

import com.hyz.ddd.demo.domain.model.BindService;
import com.hyz.ddd.demo.domain.model.Mentor;
import com.hyz.ddd.demo.domain.model.Student;

public class StudentApp {

    BindService bindService;

    public void bind(Long studentId, Long mentorId) {
        bindService.bind(studentId, mentorId);

    }

    public void autoBind(Long studentId, Long mentorId) {
//        xxxxx
//
//                xxxx
//
        Student student;
        student.check(mentorId);
        Mentor mentor;
        mentor.check(studentId);
        student.bindMentor(mentorId);

    }
}
