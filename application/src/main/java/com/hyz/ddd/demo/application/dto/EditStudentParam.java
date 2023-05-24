package com.hyz.ddd.demo.application.dto;

import com.hyz.ddd.demo.domain.model.SchoolClass;
import lombok.Data;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Data
public class EditStudentParam {

    private Long studentId;

    private String name;

    private SchoolClass schoolClass;

    private Integer age;

    private String address;

    private String phone;
}
