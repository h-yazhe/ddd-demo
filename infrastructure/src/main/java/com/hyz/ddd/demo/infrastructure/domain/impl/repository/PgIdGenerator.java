package com.hyz.ddd.demo.infrastructure.domain.impl.repository;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.hyz.ddd.demo.domain.model.IdGenerator;
import com.hyz.ddd.demo.infrastructure.mapper.SeqMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * name：基于postgresql的sequence实现id生成
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PgIdGenerator implements IdGenerator {

    SeqMapper seqMapper;
    String seqName;

    public PgIdGenerator(SeqMapper seqMapper, Class<?> entityClass) {
        this.seqMapper = seqMapper;
        this.seqName = buildSeqName(entityClass);
    }

    @Override
    public String getNamespace() {
        return seqName;
    }

    @Override
    public Long nextId() {
        return seqMapper.nextId(seqName);
    }

    /**
     * 表名拼接上_id_seq
     * @param entityClass
     * @return
     */
    public String buildSeqName(Class<?> entityClass) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        String tableName = tableInfo.getTableName();
        return tableName + "_id_seq";
    }

}
