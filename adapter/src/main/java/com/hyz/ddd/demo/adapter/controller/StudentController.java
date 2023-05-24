package com.hyz.ddd.demo.adapter.controller;

import com.hyz.ddd.demo.adapter.common.Result;
import com.hyz.ddd.demo.application.dto.*;
import com.hyz.ddd.demo.application.service.StudentAppService;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * name：学生api
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
@RestController
public class StudentController {

    @Autowired
    private StudentAppService studentAppService;

    /**
     * 新建学生
     * @param createStudentCmd
     * @return
     */
    @PostMapping("student")
    public Result<Void> addNewStudent(CreateStudentCmd createStudentCmd) {
        studentAppService.addNewStudent(createStudentCmd);
        return Result.ok();
    }

    /**
     * 查询学生列表
     * @param studentPageQuery
     * @return
     */
    @GetMapping("studentPage")
    public Result<Page<StudentDTO>> queryStudentPage(@RequestBody StudentPageQuery studentPageQuery) {
        return Result.ok(studentAppService.queryStudentPage(studentPageQuery));
    }

    /**
     * 查询学生详情
     * @param studentId
     * @return
     */
    @GetMapping("studentDetail")
    public Result<StudentDetailDTO> queryStudentDetail(@RequestParam Long studentId) {
        return Result.ok(studentAppService.queryStudentDetail(studentId));
    }

    /**
     * 编辑学生
     * @param editStudentParam
     * @return
     */
    @PostMapping("editStudent")
    public Result<Void> editStudent(@RequestBody EditStudentParam editStudentParam) {
        studentAppService.editStudent(editStudentParam);
        return Result.ok();
    }

    /**
     * 绑定导师
     * @param bindMentorParam
     * @return
     */
    @PostMapping("student/bindMentors")
    public Result<Void> bindMentors(@RequestBody BindMentorParam bindMentorParam) {
        studentAppService.bindMentors(bindMentorParam);
        return Result.ok();
    }

    /**
     * 查询已绑定的导师
     * @param studentId 学生id
     * @return
     */
    @GetMapping("student/queryBoundMentors")
    public Result<List<MentorDTO>> queryBoundMentors(@RequestParam Long studentId) {
        return Result.ok(studentAppService.queryBoundMentors(studentId));
    }
}
