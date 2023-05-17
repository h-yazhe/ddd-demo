package com.hyz.ddd.demo.domain.model;

import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import com.hyz.ddd.demo.domain.exception.ParamException;

import java.util.Optional;

/**
 * 学生
 */
public class Student implements StudentAbility {

    private Long id;

    private Type type;

    private String name;

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
    public void editStudent(EditStudentCmd editStudentCmd) {
        String name = Optional.ofNullable(editStudentCmd.getName())
                .orElseThrow(() -> new IllegalArgumentException("name cannot be null"));
        if (name.length() > 16) {
            throw new ParamException("length of name cannot be more than 16");
        }
        this.name = name;
    }
}
