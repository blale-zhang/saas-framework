package org.blade.utils;

import java.util.List;
import java.util.Map;

/**
 * Pager Class is encapsulating logic of Paging-querying,
 * If someone want to get the index of record Pager object
 * require them  setting  pageSize and currentPage
 * @author zgf
 *
 * @param <T>
 */
public class Pager<T> {
	
	private Long pageSize = 30L;// 每页记录数,默认30
	private Long totalPageNum = 0L;// 总共页数,toatlCount/pageSize得到（能整除，有余数+1)
	private Long currentPage = 1L;// 当前页码
	private Long totalCount = 0L ;// 总记录数
	private List<T> entities;

	public Pager(Long pageSize, Long currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}
	
	public Pager(Long pageSize, Long currentPage, Long totalCount){
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
	}
	
	public Pager() {
		// TODO Auto-generated constructor stub
	}

	public Long getIndex(){
		return pageSize * (currentPage-1);
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long correntPage) {
		this.currentPage = correntPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * Set the total count of record
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	private Long computeTotalPageNum(){
		totalPageNum = totalCount/pageSize;
		if(totalCount % pageSize != 0){
			totalPageNum++;
		}
		return totalPageNum;
	}
	
	public Long getTotalPageNum(){
		return this.computeTotalPageNum();
	}
	
	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public boolean hasNext() {
		if ((currentPage - 1) * pageSize <= getTotalCount())
			return true;
		return false;
	}

	public boolean hasPrevious() {
		if ((currentPage - 1) * pageSize >= getPageSize())
			return true;
		return false;
	}

	public void nextPage() {
		if (this.hasNext())
			currentPage += currentPage;

	}

	public void prePage() {
		if (this.hasPrevious())
			currentPage -= currentPage;
	}
}

