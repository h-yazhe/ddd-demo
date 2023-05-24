package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.infrastructure.DO.StudentDetailDO;
import com.hyz.ddd.demo.infrastructure.mapper.StudentDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * name：通过mybatis-plus实现的学生详情仓库
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Repository
public class MpStudentDetailRepoImpl implements StudentAbility.StudentDetailRepo {

    @Autowired
    private StudentDetailMapper studentDetailMapper;

    @Override
    public Optional<StudentAbility.StudentDetail> getByStudentId(Long studentId) {
        return Optional.ofNullable(restore(studentDetailMapper.selectOne(new LambdaQueryWrapper<StudentDetailDO>()
                .eq(StudentDetailDO::getStudentId, studentId))));
    }

    @Override
    public void add(Long studentId, StudentAbility.StudentDetail studentDetail) {
        StudentDetailDO studentDetailDO = new StudentDetailDO();
        studentDetailDO.setStudentId(studentId);
        studentDetailDO.setRemark(studentDetail.getRemark().orElse(""));
        studentDetailMapper.insert(studentDetailDO);
    }

    @Override
    public void remove(Long studentId, StudentAbility.StudentDetail detail) {
        StudentDetailDO studentDetailDO = studentDetailMapper.selectOne(new LambdaQueryWrapper<StudentDetailDO>()
                .eq(StudentDetailDO::getStudentId, studentId));
        if (studentDetailDO == null) {
            return;
        }
        studentDetailMapper.deleteById(studentDetailDO.getId());
    }

    private StudentAbility.StudentDetail restore(StudentDetailDO studentDetailDO) {
        if (studentDetailDO == null) {
            return null;
        }
        String remark = "".equals(studentDetailDO.getRemark()) ? null : studentDetailDO.getRemark();
        return new StudentAbility.StudentDetail(remark);
    }

}
