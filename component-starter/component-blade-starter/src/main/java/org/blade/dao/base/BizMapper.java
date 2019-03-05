package org.blade.dao.base;

import org.apache.ibatis.annotations.Param;
import org.blade.entities.base.BizEntity;

/**
 * 业务mapper
 */
public interface BizMapper extends BaseMapper<Long , BizEntity<Long>>{

    BizEntity<Long> selectById(@Param("id")Long id);

}
