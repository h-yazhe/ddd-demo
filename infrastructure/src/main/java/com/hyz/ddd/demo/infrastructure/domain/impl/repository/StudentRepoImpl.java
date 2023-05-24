package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import com.hyz.ddd.demo.domain.repository.StudentRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * name：基于本地内存实现的学生仓库
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
@Repository
public class StudentRepoImpl implements StudentRepo {

    private final Map<Long, StudentAbility> studentMap = new ConcurrentHashMap<>();


    @Override
    public void add(StudentAbility student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public Page<StudentAbility> queryPage(StudentPageQuery studentPageQuery) {
        List<StudentAbility> filteredStudent = studentMap.values()
                .stream()
                .filter(student -> studentPageQuery.getStudyNumber() == null || studentPageQuery.getStudyNumber().equals(student.getStudyNumber()))
                .filter(student -> studentPageQuery.getSchoolClass() == null || studentPageQuery.getSchoolClass().equals(student.getSchoolClass()))
                .filter(student -> studentPageQuery.getPhone() == null || studentPageQuery.getPhone().equals(student.getPhone()))
                .filter(student -> studentPageQuery.getNameKey() == null || (student.getName().contains(studentPageQuery.getNameKey())))
                .collect(Collectors.toList());
        List<StudentAbility> dataList = filteredStudent.stream()
                .skip((long) (studentPageQuery.getPageNo() - 1) * studentPageQuery.getPageSize())
                .limit(studentPageQuery.getPageSize())
                .collect(Collectors.toList());
        return new Page<>(dataList, studentPageQuery.getPageNo(), studentPageQuery.getPageSize(), (long) filteredStudent.size());
    }

    @Override
    public Optional<StudentAbility> getById(Long studentId) {
        return Optional.ofNullable(studentMap.get(studentId));
    }

    @Override
    public void save(StudentAbility student) {
        Optional.ofNullable(studentMap.get(student.getId()))
                .ifPresent(existedStudent -> studentMap.put(student.getId(), student));
    }

    @Override
    public Optional<StudentAbility> getByStudyNumber(String studyNumber) {
        return studentMap.values()
                .stream()
                .filter(studentAbility -> Objects.equals(studyNumber, studentAbility.getStudyNumber()))
                .findAny();
    }

}
