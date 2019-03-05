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
        entity.setCreateDate(TimeUtils.getNowTime());
        entity.setUpdateDate(TimeUtils.getNowTime());
    }

    public void modifyAttach( BizEntity entity ){

        entity.setUpdateDate(TimeUtils.getNowTime());
    }


}
