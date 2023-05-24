package com.hyz.ddd.demo.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyz.ddd.demo.infrastructure.DO.StudentDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Mapper
public interface StudentDetailMapper extends BaseMapper<StudentDetailDO> {

}
