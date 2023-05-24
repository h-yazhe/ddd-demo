package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.repository.StudentRepo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Repository
public class StudentDetailRepoImpl implements StudentAbility.StudentDetailRepo {

    Map<Long, StudentAbility.StudentDetail> studentDetailMap = new ConcurrentHashMap<>();

    @Override
    public Optional<StudentAbility.StudentDetail> getByStudentId(Long studentId) {
        return Optional.ofNullable(studentDetailMap.get(studentId));
    }

    @Override
    public void add(Long studentId, StudentAbility.StudentDetail studentDetail) {
        studentDetailMap.put(studentId, studentDetail);
    }

    @Override
    public void remove(Long studentId, StudentAbility.StudentDetail detail) {
        studentDetailMap.remove(studentId);
    }

}
