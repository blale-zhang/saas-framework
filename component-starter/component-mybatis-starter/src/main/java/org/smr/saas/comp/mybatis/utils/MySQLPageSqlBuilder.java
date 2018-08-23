package org.smr.saas.comp.mybatis.utils;

public class MySQLPageSqlBuilder  extends PageSql  implements PagerSqlBuilder {

	public String buildPageSql(String querySql, Long startIndex  ,Long pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(querySql).append(" limit ").append(startIndex).append(",").append(startIndex + pageSize);
		return sb.toString();
	}
	

}
