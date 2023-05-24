package com.hyz.ddd.demo.infrastructure.config;

import com.hyz.ddd.demo.domain.model.IdGenerator;
import com.hyz.ddd.demo.infrastructure.DO.StudentDO;
import com.hyz.ddd.demo.infrastructure.mapper.SeqMapper;
import com.hyz.ddd.demo.infrastructure.domain.impl.repository.PgIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
@Configuration
public class IdGeneratorConfig {

//    /**
//     * 内存的id生成
//     * @return
//     */
//    @Bean
//    public IdGenerator studentIdGenerator() {
//        return new IdGeneratorImpl(StudentAbility.class.getName());
//    }

    /**
     * 通过postgresql的sequence实现id生成
     * @param seqMapper
     * @return
     */
    @Bean
    public IdGenerator studentIdGenerator(SeqMapper seqMapper) {
        return new PgIdGenerator(seqMapper, StudentDO.class);
    }
}
