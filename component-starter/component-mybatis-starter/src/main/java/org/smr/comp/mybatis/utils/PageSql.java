package org.smr.comp.mybatis.utils;

public class PageSql {

	/**
	 * 根据原Sql语句获取对应的查询�?�记录数的Sql语句
	 */
	public String getCountSql(String sql) {
		return " SELECT COUNT(*) FROM (" + sql + ") totalCount ";
	}

}
