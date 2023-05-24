package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import com.hyz.ddd.demo.domain.repository.StudentRepo;
import com.hyz.ddd.demo.infrastructure.DO.StudentDO;
import com.hyz.ddd.demo.infrastructure.mapper.StudentMapper;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * name：mybatis plus 实现的学生仓库
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Repository
public class MpStudentRepoImpl implements StudentRepo {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public void add(@NonNull StudentAbility student) {
        StudentDO studentDO = convertToDO(student);
        LocalDateTime now = LocalDateTime.now();
        studentDO.setCreatedAt(now);
        studentDO.setUpdatedAt(now);
        studentMapper.insert(studentDO);
    }

    @Override
    public Page<StudentAbility> queryPage(@NonNull StudentPageQuery studentPageQuery) {
        LambdaQueryWrapper<StudentDO> queryWrapper = new LambdaQueryWrapper<StudentDO>()
                .eq(studentPageQuery.getStudyNumber() != null, StudentDO::getStudyNumber, studentPageQuery.getStudyNumber())
                .like(studentPageQuery.getNameKey() != null, StudentDO::getName, studentPageQuery.getNameKey())
                .eq(studentPageQuery.getSchoolClass() != null, StudentDO::getSchoolClass, studentPageQuery.getSchoolClass().getName())
                .eq(studentPageQuery.getPhone() != null, StudentDO::getPhone, studentPageQuery.getPhone())
                .eq(StudentDO::getDeleted, false);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<StudentDO> studentDOPage =
                studentMapper.selectPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page.of(studentPageQuery.getPageNo(), studentPageQuery.getPageSize()), queryWrapper);
        List<StudentAbility> studentList = studentDOPage.getRecords()
                .stream()
                .map(this::restore)
                .collect(Collectors.toList());
        return new Page<>(studentList, studentPageQuery.getPageNo(), studentPageQuery.getPageSize(), studentDOPage.getTotal());
    }

    @Override
    public Optional<StudentAbility> getById(@NonNull Long studentId) {
        return Optional.ofNullable(studentMapper.selectOne(new LambdaQueryWrapper<StudentDO>()
                .eq(StudentDO::getId, studentId)
                .eq(StudentDO::getDeleted, false)))
                .map(this::restore);
    }

    @Override
    public void save(@NonNull StudentAbility student) {
        getById(student.getId())
                .ifPresent(studentAbility -> studentMapper.updateById(convertToDO(student)));
    }

    @Override
    public Optional<StudentAbility> getByStudyNumber(String studyNumber) {
        return Optional.ofNullable(studentMapper.selectOne(new LambdaQueryWrapper<StudentDO>()
                .eq(StudentDO::getStudyNumber, studyNumber)
                .eq(StudentDO::getDeleted, false)))
                .map(this::restore);
    }

    /**
     * 转化成数据库存储的数据对象
     * @param student
     * @return
     */
    private StudentDO convertToDO(StudentAbility student) {
        if (student == null) {
            return null;
        }
        StudentDO studentDO = new StudentDO();
        BeanUtils.copyProperties(student, studentDO);
        studentDO.setDeleted(false);
        return studentDO;
    }

    /**
     * 从数据库还原领域对象
     * @param studentDO
     * @return
     */
    private StudentAbility restore(StudentDO studentDO) {
        if (studentDO == null) {
            return null;
        }
        return new Student(studentDO.getId(), studentDO.getType(), studentDO.getName(), studentDO.getStudyNumber(),
                studentDO.getSchoolClass(), studentDO.getAge(), studentDO.getAddress(), studentDO.getPhone());
    }
}
