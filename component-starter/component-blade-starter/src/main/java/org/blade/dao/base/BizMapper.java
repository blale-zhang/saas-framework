package org.blade.dao.base;

import org.blade.entities.base.BizEntity;

/**
 * 业务mapper
 */
public interface BizMapper<String> extends BaseMapper<String,BizEntity<String>> , PageQueryMapper<String,BizEntity<String>> {


}
