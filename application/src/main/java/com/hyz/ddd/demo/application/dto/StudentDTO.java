package com.hyz.ddd.demo.application.dto;

import com.hyz.ddd.demo.domain.model.SchoolClass;
import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Data
public class StudentDTO {

    private Long id;

    private String name;

    /**
     * 学生类型名
     */
    private String typeName;

    /**
     * 学号
     */
    private String studyNumber;

    /**
     * 班级名
     */
    private String schoolClassName;

    private Integer age;

    private String address;

    private String phone;

    public static StudentDTO from(StudentAbility student) {
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        studentDTO.setTypeName(student.getType().getName());
        studentDTO.setSchoolClassName(student.getSchoolClass().getName());
        return studentDTO;
    }
}
