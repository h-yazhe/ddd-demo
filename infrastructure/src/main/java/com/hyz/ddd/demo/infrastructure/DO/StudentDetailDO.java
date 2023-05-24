package com.hyz.ddd.demo.infrastructure.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * name：学生详情表的数据模型
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@TableName(value = "student_detail",schema = "demo")
@Data
public class StudentDetailDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long studentId;

    /**
     * 备注
     */
    private String remark;

}
