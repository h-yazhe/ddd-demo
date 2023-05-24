package com.hyz.ddd.demo.domain.model;

import cn.hutool.extra.spring.SpringUtil;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import com.hyz.ddd.demo.domain.exception.ParamException;
import com.hyz.ddd.demo.domain.repository.MentorRepo;
import com.hyz.ddd.demo.domain.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 学生
 */
public class Student implements StudentAbility {

    private final Long id;

    private String name;

    private Type type;

    /**
     * 学号
     */
    private final String studyNumber;

    private SchoolClass schoolClass;

    private Integer age;

    private String address;

    private String phone;
    private final StudentDetailRepo studentDetailRepo;
    private final MentorRepo mentorRepo;
    private final BoundMentorList boundMentorList;

    public Student(Long id, Type type, String name, String studyNumber, SchoolClass schoolClass, Integer age, String address, String phone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.studyNumber = studyNumber;
        this.schoolClass = schoolClass;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.studentDetailRepo = SpringUtil.getBean(StudentDetailRepo.class);
        this.mentorRepo = SpringUtil.getBean(MentorRepo.class);
        BoundMentorListFactory boundMentorListFactory = SpringUtil.getBean(BoundMentorListFactory.class);
        this.boundMentorList = boundMentorListFactory.create(id);
    }

    /**
     * 创建学生
     * @param createStudentCmd
     * @return
     */
    public static Student create(CreateStudentCmd createStudentCmd) {
        Type type = Optional.ofNullable(createStudentCmd.getType())
                .orElseThrow(() -> new ParamException("type cannot be null"));
        String studyNumber = checkStudyNumber(createStudentCmd.getStudyNumber());
        String name = checkName(createStudentCmd.getName());
        SchoolClass schoolClass = Optional.ofNullable(createStudentCmd.getSchoolClass())
                .orElseThrow(() -> new ParamException("school class cannot be null"));
        Integer age = checkAge(createStudentCmd.getAge());
        String address = checkAddress(createStudentCmd.getAddress());
        String phone = Optional.ofNullable(createStudentCmd.getPhone())
                .orElseThrow(() -> new ParamException("phone cannot be null"));
        String remark = checkRemark(createStudentCmd.getRemark());

        IdGenerator idGenerator = SpringUtil.getBean("studentIdGenerator", IdGenerator.class);
        Long studentId = idGenerator.nextId();
        StudentDetail studentDetail = new StudentDetail(remark);
        SpringUtil.getBean(StudentDetailRepo.class).add(studentId, studentDetail);
        return new Student(studentId, type, name, studyNumber, schoolClass, age, address, phone);
    }

    /**
     * 校验学号
     * @param studyNumber
     * @return
     */
    private static String checkStudyNumber(String studyNumber) {
        if (studyNumber == null) {
            throw new ParamException("studyNumber cannot be null");
        }
        StudentRepo studentRepo = SpringUtil.getBean(StudentRepo.class);
        //学号不能重复
        studentRepo.getByStudyNumber(studyNumber)
                .orElseThrow(() -> new ParamException("studyNumber already existed"));
        return studyNumber;
    }

    /**
     * 校验备注
     * @param remark
     * @return
     */
    private static String checkRemark(String remark) {
        if (remark == null) {
            return null;
        }
        if (remark.length() > 2000) {
            throw new ParamException("length of remark cannot be more than 2000");
        }
        return remark;
    }

    /**
     * 校验地址
     * @param address
     * @return
     */
    private static String checkAddress(String address) {
        if (address == null) {
            throw new ParamException("address cannot be null");
        }
        if (address.length() > 100) {
            throw new ParamException("length of address cannot be more than 100");
        }
        return address;
    }

    /**
     * 校验名字
     * @param name
     * @return
     */
    private static String checkName(String name) {
        if (name == null) {
            throw new ParamException("name cannot be null");
        }
        if (name.length() > 16) {
            throw new ParamException("length of name cannot be more than 16");
        }
        return name;
    }

    /**
     * 校验年龄
     * @param age
     * @return
     */
    private static Integer checkAge(Integer age) {
        if (age == null) {
            throw new ParamException("age cannot be null");
        }
        if (age <= 0 || age > 100) {
            throw new ParamException("age should between 0 and 100");
        }
        return age;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStudyNumber() {
        return studyNumber;
    }

    @Override
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    /**
     * 查看详情
     * @return
     */
    @Override
    public StudentDetail getDetail() {
        return studentDetailRepo.getByStudentId(this.id)
                .orElseThrow(() -> new RuntimeException("cannot find detail"));
    }

    /**
     * 编辑学生
     * @param editStudentCmd
     */
    @Override
    public void editStudent(EditStudentCmd editStudentCmd) {
        if (editStudentCmd.getPhone() == null) {
            throw new ParamException("phone cannot be null");
        }
        if (editStudentCmd.getSchoolClass() == null) {
            throw new ParamException("school class cannot be null");
        }
        if (editStudentCmd.getType() == null) {
            throw new ParamException("type cannot be null");
        }
        String name = checkName(editStudentCmd.getName());
        String address = checkAddress(editStudentCmd.getAddress());
        Integer age = checkAge(editStudentCmd.getAge());

        String remark = checkRemark(editStudentCmd.getRemark());
        StudentDetail detail = getDetail();
        studentDetailRepo.remove(this.id, detail);
        studentDetailRepo.add(this.id, new StudentDetail(remark));

        this.name = name;
        this.address = address;
        this.age = age;
        this.phone = editStudentCmd.getPhone();
        this.schoolClass = editStudentCmd.getSchoolClass();
        this.type = editStudentCmd.getType();
    }

    /**
     * 绑定导师
     * @param mentorIds
     */
    @Override
    public void bindMentors(List<Long> mentorIds) {
        List<Mentor> mentors = mentorRepo.listByIds(mentorIds);
        mentors.forEach(boundMentorList::add);
    }

    /**
     * 查询已绑定的导师
     * @return
     */
    @Override
    public List<Mentor> queryBoundMentors() {
        return boundMentorList.listAll();
    }
}
