package com.example.mentorservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mentorservice.model.MentorDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */

@Mapper
public interface MentorMapper extends BaseMapper<MentorDO> {
}
