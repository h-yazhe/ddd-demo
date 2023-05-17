package com.hyz.ddd.demo.infrastructure.mapper;

import java.util.List;

public class MentorListImpl implements MentorList {

    private final Long studentId;
    private MentorMapper mentorMapper;

    public MentorListImpl(Long studentId) {
        this.studentId = studentId;
    }

    public List<Mentor> list() {

    }
}
