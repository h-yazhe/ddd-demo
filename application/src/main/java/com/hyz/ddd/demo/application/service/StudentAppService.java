package com.hyz.ddd.demo.application.service;

import com.hyz.ddd.demo.application.dto.*;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import com.hyz.ddd.demo.domain.exception.ParamException;
import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import com.hyz.ddd.demo.domain.repository.StudentRepo;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生app服务
 * 提供学生相关的应用能力
 */
@Service
public class StudentAppService {

    @Autowired
    @Qualifier("mpStudentRepoImpl")
    private StudentRepo studentRepo;

    /**
     * 新建学生
     * @param createStudentCmd
     */
    public void addNewStudent(CreateStudentCmd createStudentCmd) {
        Student student = Student.create(createStudentCmd);
        studentRepo.add(student);
    }

    /**
     * 查询学生
     * @param studentPageQuery
     * @return
     */
    public Page<StudentDTO> queryStudentPage(StudentPageQuery studentPageQuery) {
        Page<StudentAbility> page = studentRepo.queryPage(studentPageQuery);
        List<StudentDTO> studentDTOS = page.getDataList().stream()
                .map(StudentDTO::from)
                .collect(Collectors.toList());
        return new Page<>(studentDTOS, page.getPageNo(), page.getPageSize(), page.getTotalCount());
    }

    /**
     * 查询学生详情
     * @param studentId
     * @return
     */
    public StudentDetailDTO queryStudentDetail(Long studentId) {
        return studentRepo.getById(studentId)
                .map(StudentDetailDTO::from)
                .orElseThrow(() -> new ParamException("student not found"));
    }

    /**
     * 编辑学生
     * @param editStudentParam
     */
    public void editStudent(EditStudentParam editStudentParam) {
        StudentAbility student = studentRepo.getById(editStudentParam.getStudentId())
                .orElseThrow(() -> new ParamException("student not found"));
        EditStudentCmd editStudentCmd = new EditStudentCmd();
        BeanUtils.copyProperties(editStudentCmd, editStudentCmd);
        student.editStudent(editStudentCmd);
        studentRepo.save(student);
    }

    /**
     * 绑定导师
     * @param bindMentorParam
     */
    public void bindMentors(BindMentorParam bindMentorParam) {
        StudentAbility student = studentRepo.getById(bindMentorParam.getStudentId())
                .orElseThrow(() -> new ParamException("student not found"));
        student.bindMentors(bindMentorParam.getMentorIds());
    }

    /**
     * 查询已绑定的导师
     * @param studentId
     * @return
     */
    public List<MentorDTO> queryBoundMentors(@NonNull Long studentId) {
        StudentAbility student = studentRepo.getById(studentId)
                .orElseThrow(() -> new ParamException("student not found"));
        return student.queryBoundMentors()
                .stream()
                .map(MentorDTO::from)
                .collect(Collectors.toList());
    }
}
