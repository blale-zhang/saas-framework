/**
 * 
 */
package org.blade.service.base;


import org.blade.entities.base.BaseEntity;

/**
 * 基础
 * @author blade
 *
 */
public interface BaseService<ID ,T extends BaseEntity<ID>> {

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public int save(T entity);

	/**
	 * 根据id更新
	 * @param id
	 * @return
	 */
	public int updateById(T id);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public T queryById(ID id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(ID id);

}
