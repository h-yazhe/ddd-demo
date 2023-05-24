package com.hyz.ddd.demo.domain.cmd;

import com.hyz.ddd.demo.domain.model.SchoolClass;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import lombok.Data;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
@Data
public class CreateStudentCmd {

    private StudentAbility.Type type;

    private String studyNumber;

    private String name;

    private SchoolClass schoolClass;

    private Integer age;

    private String address;

    private String phone;

    private String remark;
}
