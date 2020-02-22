package org.smr.comp.mybatis.utils;

import org.apache.commons.lang3.StringUtils;

public class MySQLPageSqlBuilder  extends PageSql  implements PagerSqlBuilder {

	public String buildPageSql(String querySql, Long startIndex  ,Long pageSize, String orderByClause) {
		StringBuffer sb = new StringBuffer();
		sb.append(querySql);
		if(StringUtils.isNoneBlank(orderByClause)){
		    sb.append(ORDER_BY_CLAUSE).append(orderByClause);
        }
        sb.append(LIMIT_CLAUSE)
        .append(startIndex)
        .append(",").append(pageSize);
		return sb.toString();
	}
	

}
