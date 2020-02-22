package org.smr.comp.mybatis.utils;

/**
 * 分页SQL建�?��??
 * @author blade
 *
 */
public interface PagerSqlBuilder {


	public static final String ORDER_BY_CLAUSE = " ORDER BY ";
	public static final String LIMIT_CLAUSE = " LIMIT ";


	/**
	 * 分页Sql构�??
	 * @param querySql 原始不带分页逻辑的查询SQL
	 * @param startIndex 当前页记录数
	 * @param pageSize 排序字段 ,为了兼容MSSQL,如果不需要排序请输入“�??
	 * @return
	 */
	public String buildPageSql(String querySql, Long startIndex, Long pageSize, String orderByClause);
	
	/**
	 * 获取totalCount语句
	 * @param querySql
	 * @return
	 */
	public String getCountSql(String querySql);
}
