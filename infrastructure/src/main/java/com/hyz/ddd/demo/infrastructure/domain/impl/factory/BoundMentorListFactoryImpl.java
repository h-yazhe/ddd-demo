package com.hyz.ddd.demo.infrastructure.domain.impl.factory;

import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.infrastructure.domain.impl.model.BoundMentorListImpl;
import org.springframework.stereotype.Component;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Component
public class BoundMentorListFactoryImpl implements StudentAbility.BoundMentorListFactory {

    @Override
    public StudentAbility.BoundMentorList create(Long studentId) {
        return new BoundMentorListImpl(studentId);
    }

}
