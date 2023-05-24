package com.hyz.ddd.demo.application.dto;

import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDetailDTO extends StudentDTO {

    private String remark;

    public StudentDetailDTO(StudentDTO studentDTO, String remark) {
        BeanUtils.copyProperties(studentDTO, this);
        this.remark = remark;
    }

    public static StudentDetailDTO from(StudentAbility student) {
        StudentDTO studentDTO = StudentDTO.from(student);
        return new StudentDetailDTO(studentDTO, student.getDetail().getRemark().orElse(null));
    }

}
