package org.blade.service.base;

import org.blade.entities.base.BizEntity;
import org.blade.utils.IdGenUtils;
import org.blade.utils.TimeUtils;

/**
 * 抽象服务
 */
public class AbstractService {


    /**
     *
     * @param entity
     */
    public void saveAttatch(BizEntity entity ){

        Long id = IdGenUtils.getNextLongId();
        entity.setId(id);
        entity.setCreateTime(TimeUtils.getNowTime());
        entity.setModifyTime(TimeUtils.getNowTime());
    }

    public void modifyAttach( BizEntity entity ){

        entity.setModifyTime(TimeUtils.getNowTime());
    }


}
