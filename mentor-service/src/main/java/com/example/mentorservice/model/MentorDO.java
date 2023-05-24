package com.example.mentorservice.model;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * name：mentor表的数据模型
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@TableName(value = "mentor",schema = "demo_mentor")
public class MentorDO {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
