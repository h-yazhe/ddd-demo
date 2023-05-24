package com.hyz.ddd.demo.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * name：
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/4/24
 */
@Mapper
public interface SeqMapper {

    @Select(value = "select nextval(#{seqName}::regclass);")
    Long nextId(@Param("seqName") String seqName);

}
