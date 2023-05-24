package com.hyz.ddd.demo.domain.model;

import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * 学生接口
 * 聚合根
 */
public interface StudentAbility {

    /**
     * 学生类型
     * 值对象
     */
    enum Type {

        NORMAL("普通学生"),
        /**
         * 小队长
         */
        SMALL_CAPTAIN("小队长"),
        /**
         * 中队长
         */
        MIDDLE_CAPTAIN("中队长"),
        /**
         * 大队长
         */
        BIG_CAPTAIN("大队长"),
        ;

        Type(String name) {
            this.name = name;
        }

        private final String name;

        /**
         * 类型名
         * @return
         */
        public String getName() {
            return name;
        }
    }

    /**
     * 学生详情
     * 值对象
     */
    @AllArgsConstructor
    class StudentDetail {

        /**
         * 备注
         */
        private String remark;

        public Optional<String> getRemark() {
            return Optional.of(remark);
        }

    }

    interface StudentDetailRepo {

        /**
         * 获取detail
         *
         * @return
         */
        Optional<StudentDetail> getByStudentId(Long studentId);

        void add(Long studentId, StudentDetail studentDetail);

        void remove(Long studentId, StudentDetail detail);
    }

    /**
     * 学生已绑定的导师列表
     * 值对象
     */
    interface BoundMentorList {

        /**
         * 查询所有导师
         * @return
         */
        List<Mentor> listAll();

        /**
         * 添加导师
         * @param mentor
         */
        void add(Mentor mentor);
    }

    /**
     * BoundMentorList的工厂类
     * 因为领域层不感知BoundMentorList的实现，所以无法自己去构造BoundMentorList对象，
     * 需要通过这个工厂来将构造的过程交给基础设施层
     */
    interface BoundMentorListFactory {

        BoundMentorList create(Long studentId);

    }


    /**
     * 获取id
     * @return
     */
    Long getId();

    /**
     * 获取学生类型
     * @return
     */
    Type getType();

    /**
     * 获取姓名
     * @return
     */
    String getName();

    /**
     * 学号
     * @return
     */
    String getStudyNumber();

    /**
     * 班级
     * @return
     */
    SchoolClass getSchoolClass();

    /**
     * 年龄
     * @return
     */
    Integer getAge();

    /**
     * 地址
     * @return
     */
    String getAddress();

    /**
     * 手机号
     * @return
     */
    String getPhone();

    /**
     * 获取详情
     * @return
     */
    StudentDetail getDetail();

    /**
     * 编辑学生
     * @param editStudentCmd
     */
    void editStudent(EditStudentCmd editStudentCmd);

    /**
     * 绑定导师
     * @param mentorIds
     */
    void bindMentors(List<Long> mentorIds);

    /**
     * 查询已绑定的导师
     * @return
     */
    List<Mentor> queryBoundMentors();

}
