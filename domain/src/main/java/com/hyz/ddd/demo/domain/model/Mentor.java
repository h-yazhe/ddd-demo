package com.hyz.ddd.demo.domain.model;

/**
 * 导师
 * 对于学生这个服务来说，不承担导师的修改、新增职责，所以导师的属性应当是不可变的
 */
public class Mentor {

    private final Long id;

    private final String name;

    public Mentor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
