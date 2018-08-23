package org.smr.saas.comp.mybatis.utils;

public class MsSQLPageSqlBuilder extends PageSql implements PagerSqlBuilder {

	public String buildPageSql(String querySql, Long startIndex, Long pageSize ) {
		
		String orderBy = "";
		String pageSql = "WITH query AS(SELECT ROW_NUMBER() OVER (" + orderBy
				+ ") AS _row_number_," + "t.* from (" + querySql
				+ ")t) \n SELECT * FROM query WHERE _row_number_ BETWEEN "
				+ ( startIndex + 1 )  + " AND " + (startIndex + pageSize);
		return pageSql;
	}

}
