package com.hyz.ddd.demo.infrastructure.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hyz.ddd.demo.domain.model.SchoolClass;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Data
@TableName(value = "student", schema = "demo")
public class StudentDO {

    private Long id;

    private String name;

    private StudentAbility.Type type;

    /**
     * 学号
     */
    private String studyNumber;

    private SchoolClass schoolClass;

    private Integer age;

    private String address;

    private String phone;

    private Boolean deleted;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
