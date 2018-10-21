/**
 * 
 */
package org.blade.service.base;

import java.util.List;

import org.blade.entities.base.BaseEntity;

/**
 * 批量操作接口
 * 提供批量操作
 * @author blade
 *
 */
public interface BaseBatchService<T extends BaseEntity<Long>> {

	/**
	 * 批量保存操作
	 * @param entities 需要保存的信息
	 * @return
	 */
	public int batchSave(List<T> entities);
	
}
