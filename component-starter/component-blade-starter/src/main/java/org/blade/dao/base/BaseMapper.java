package org.blade.dao.base;

import java.util.List;

import org.blade.entities.base.BaseEntity;
/**
 * 基础Mapper,包含基础操作
 * @author blade
 *
 * @param <ID>
 * @param <T>
 */
public interface BaseMapper<ID,T extends BaseEntity<ID>> {

	/**
	 * 根据Id查询
	 * @param id 主键
	 * @return
	 */
	public T selectById(ID id);
	
	/**
	 * 根据条件查询
	 * @param entity 条件信息
	 * @return
	 */
	public List<T> select(T entity);
	
	/**
	 * 插入
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 批量插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(List<T> entities);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(ID id);
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	public int updateById(T entity);
	
	/**
	 * 批量更新
	 * @param entities
	 * @return
	 */
	public int batchUpdate(List<T> entities);
	
	
}
