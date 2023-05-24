package com.hyz.ddd.demo.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@RequiredArgsConstructor
@Getter
public enum SchoolClass {

    /**
     * 一班
     */
    ONE("一班"),
    /**
     * 二班
     */
    TWO("二班"),
    /**
     * 三班
     */
    THREE("三班"),
    ;

    private final String name;

}
