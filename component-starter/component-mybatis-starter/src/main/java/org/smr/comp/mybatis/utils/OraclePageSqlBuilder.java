package org.smr.comp.mybatis.utils;

public class OraclePageSqlBuilder extends PageSql implements PagerSqlBuilder {

	public String buildPageSql(String querySql,Long startIndex , Long pageSize ,String orderByClause ) {

		StringBuilder pageSQl = new StringBuilder();
		pageSQl.append(" SELECT * FROM  ");
		pageSQl.append(" ( ");
		pageSQl.append(" 	SELECT A.*, ROWNUM RN  ");
		pageSQl.append(" 	FROM ( " + querySql  + "  ) A  ");
		pageSQl.append(" 	WHERE ROWNUM <=  ");
		pageSQl.append( startIndex + pageSize );
		pageSQl.append("  ) ");
		pageSQl.append(" WHERE RN >=  ");
		pageSQl.append(startIndex);
		return pageSQl.toString();
	}

}
