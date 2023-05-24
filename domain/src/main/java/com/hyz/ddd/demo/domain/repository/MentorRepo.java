package com.hyz.ddd.demo.domain.repository;

import com.hyz.ddd.demo.domain.model.Mentor;

import java.util.List;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
public interface MentorRepo {

    /**
     * 查询所有导师
     * @return
     */
    List<Mentor> listAll();

    /**
     * 根据id批量查询导师
     * @param mentorIds
     * @return
     */
    List<Mentor> listByIds(List<Long> mentorIds);

}
