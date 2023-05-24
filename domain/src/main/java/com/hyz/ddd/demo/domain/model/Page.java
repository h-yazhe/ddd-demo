package com.hyz.ddd.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@AllArgsConstructor
@Getter
public class Page<T> {

    private List<T> dataList;

    private Integer pageNo;

    private Integer pageSize;

    private Long totalCount;
}
