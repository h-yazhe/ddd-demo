package com.hyz.ddd.demo.application.dto;

import lombok.Data;

import java.util.List;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Data
public class BindMentorParam {

    private Long studentId;

    /**
     * 要绑定的导师id列表
     */
    private List<Long> mentorIds;
}
