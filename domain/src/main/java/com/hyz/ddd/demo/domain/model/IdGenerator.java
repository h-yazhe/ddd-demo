package com.hyz.ddd.demo.domain.model;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
public interface IdGenerator {

    String getNamespace();

    Long nextId();

}
