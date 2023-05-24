package com.hyz.ddd.demo.domain.repository;

import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;

import java.util.Optional;

/**
 * name：学生仓库，用于存储系统内的所有学生
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
public interface StudentRepo {

    /**
     * 添加学生
     * @param student
     */
    void add(StudentAbility student);

    /**
     * 分页查询
     * @param studentPageQuery
     * @return
     */
    Page<StudentAbility> queryPage(StudentPageQuery studentPageQuery);

    /**
     * 根据id查询学生
     * @param studentId
     * @return
     */
    Optional<StudentAbility> getById(Long studentId);

    /**
     * 保存学生
     * @param student
     */
    void save(StudentAbility student);

    /**
     * 根据学号查询学生
     * @param studyNumber
     * @return
     */
    Optional<StudentAbility> getByStudyNumber(String studyNumber);
}
