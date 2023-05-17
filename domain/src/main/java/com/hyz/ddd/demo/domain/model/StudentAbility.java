package com.hyz.ddd.demo.domain.model;

import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;

import java.util.Iterator;

/**
 * 学生接口
 */
public interface StudentAbility {

    /**
     * 学生类型
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
     * 编辑学生
     * @param editStudentCmd
     */
    void editStudent(EditStudentCmd editStudentCmd);

}
