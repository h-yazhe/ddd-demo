package com.hyz.ddd.demo.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyz.ddd.demo.infrastructure.DO.StudentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {

}
