package com.hyz.ddd.demo.infrastructure.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * name：用户已绑定的mentor表
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@TableName(value = "bound_mentor",schema = "demo")
@Data
public class BoundMentorDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long studentId;

    private Long mentorId;
}
