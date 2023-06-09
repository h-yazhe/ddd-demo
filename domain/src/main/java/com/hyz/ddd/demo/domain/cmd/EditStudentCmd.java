package com.hyz.ddd.demo.domain.cmd;

import com.hyz.ddd.demo.domain.model.SchoolClass;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import lombok.Data;

@Data
public class EditStudentCmd {

    private String name;

    private SchoolClass schoolClass;

    private StudentAbility.Type type;

    private Integer age;

    private String address;

    private String phone;

    private String remark;
}
