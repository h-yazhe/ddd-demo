package com.example.mentorservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mentorservice.dao.MentorMapper;
import com.example.mentorservice.model.MentorDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@RestController
public class MentorController {

    @Autowired
    MentorMapper mentorMapper;

    /**
     * 查询所有导师
     * @return
     */
    @GetMapping("mentors")
    public List<MentorDO> listAllMentors() {
        return mentorMapper.selectList(new LambdaQueryWrapper<>());
    }

}
