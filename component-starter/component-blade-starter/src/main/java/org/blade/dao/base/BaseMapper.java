package org.blade.dao.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.blade.entities.base.BizEntity;

/**
 * 基础Mapper,包含基础操作
 * @author blade
 *
 * @param <ID>
 * @param <T>
 */
public interface BaseMapper<ID,T extends BizEntity<ID>> {

	/**
	 * 根据Id查询
	 * @param id 主键
	 * @return
	 */
	public T selectById(@Param("id") ID id);
	
	/**
	 * 根据条件查询
	 * @param entity 条件信息
	 * @return
	 */
	public List<T> select(@Param("entity")T entity);
	
	/**
	 * 插入
	 * @param entity
	 * @return
	 */
	public int insert(@Param("entity")T entity);
	
	/**
	 * 批量插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(@Param("entities")List<T> entities);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id") ID id);
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	public int updateById(@Param("entity")T entity);
	
	/**
	 * 批量更新
	 * @param entities
	 * @return
	 */
	public int batchUpdate(@Param("entities")List<T> entities);


}
