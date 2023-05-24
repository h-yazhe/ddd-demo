package com.hyz.ddd.demo.domain.query;

import com.hyz.ddd.demo.domain.model.SchoolClass;
import lombok.Data;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/19
 */
@Data
public class StudentPageQuery {

    private Integer pageNo;

    private Integer pageSize;

    private String studyNumber;

    private SchoolClass schoolClass;

    private String phone;

    private String nameKey;
}
