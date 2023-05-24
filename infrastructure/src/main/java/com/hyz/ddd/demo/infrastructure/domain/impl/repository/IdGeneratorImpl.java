package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.hyz.ddd.demo.domain.model.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
public class IdGeneratorImpl implements IdGenerator {

    private final AtomicLong atomicLong = new AtomicLong();

    private final String namespace;

    public IdGeneratorImpl(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public Long nextId() {
        return atomicLong.incrementAndGet();
    }
}
