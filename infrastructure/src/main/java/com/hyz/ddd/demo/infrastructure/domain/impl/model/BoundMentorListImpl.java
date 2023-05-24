package com.hyz.ddd.demo.infrastructure.domain.impl.model;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyz.ddd.demo.domain.model.Mentor;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.repository.MentorRepo;
import com.hyz.ddd.demo.infrastructure.DO.BoundMentorDO;
import com.hyz.ddd.demo.infrastructure.mapper.BoundMentorMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * name：已绑定的导师列表实现（基于数据库）
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
public class BoundMentorListImpl implements StudentAbility.BoundMentorList {

    private final Long studentId;
    private final BoundMentorMapper boundMentorMapper;
    private final MentorRepo mentorRepo;

    public BoundMentorListImpl(Long studentId) {
        this.studentId = studentId;
        this.boundMentorMapper = SpringUtil.getBean(BoundMentorMapper.class);
        this.mentorRepo = SpringUtil.getBean(MentorRepo.class);
    }

    @Override
    public List<Mentor> listAll() {
        List<Long> mentorIds = boundMentorMapper.selectList(new LambdaQueryWrapper<BoundMentorDO>()
                .eq(BoundMentorDO::getStudentId, studentId))
                .stream()
                .map(BoundMentorDO::getMentorId)
                .collect(Collectors.toList());
        return mentorRepo.listByIds(mentorIds);
    }

    @Override
    public void add(Mentor mentor) {
        BoundMentorDO boundMentorDO = new BoundMentorDO();
        boundMentorDO.setStudentId(studentId);
        boundMentorDO.setMentorId(mentor.getId());
        boundMentorMapper.insert(boundMentorDO);
    }

}
