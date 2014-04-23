package com.adserver.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.adserver.util.StringUtil;

/**
 * Has nothing to do with specific ORM implementation parameters and query results paging packages. 
 * Note that all serial * numbers starting from 1.
 * 
 */
public class Page<T> {
	// Public Variables //
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// Paging parameters//
	protected int pageNo = 1;
	protected int pageSize = 1; 
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	// Return result //
	protected List<T> result = Collections.emptyList();
	protected Map<T,List<String>> maps=Collections.emptyMap();
	protected long totalCount = -1;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[totalCount=").append(totalCount)
			.append("|totalPages=").append(getTotalPages())
			.append("|pageNo=").append(pageNo)
			.append("|pageSize=").append(pageSize)
			.append("|orderBy=").append(orderBy)
			.append("|order=").append(order)
			.append("|result.size=").append(result == null ? "0" : result.size())
			.append("]");
		
		return sb.toString();
	}
	
	// Constructor //
	public Page() {
	}

	public Page(final int pageSize) {
		setPageSize(pageSize);
	}

	public Page(final int pageSize, final boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	// Query parameters to access the function //

	/**
	 * Access to the current page the page number, serial number starting at 1, the default is 1.
	 */
	public int getPageNo() {
		if (totalCount == 0) {
			pageNo = 0;
		}
		if(pageNo>getTotalPages()){
			this.pageNo=(int) getTotalPages();
		}
		if(pageNo <= 0) {
			pageNo = 1;
		}
		return pageNo;
	}

	/**
	 * Set the current page the page number, serial number starting at 1, less than 1 automatically adjusted to 1.
	 */
	public void setPageNo(final int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
			return;
		}
		this.pageNo = pageNo;
	}

	/**
	 * To obtain the number of records per page, default is 1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Set the number of records per page, less than 1 automatically adjusted to a.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * According to calculation of the current page pageNo, and pageSize first recorded in the overall results of a
	 * centralized location, serial number starting from 0.
	 */
	public int getFirst() {
		return ((getPageNo() - 1) * pageSize);
	}

	/**
	 * Was sort field, no default value. More sort fields to use ',' separated.
	 */
	public String getOrderBy() {
		if(StringUtils.isEmpty(orderBy)) {
			return ASC;
		}
		return orderBy;
	}

	/**
	 * Set sort field, a number of sort field with ',' separated.
	 */
	public void setOrderBy(final String orderBy) {

		// Check the legal value of the string order
		String[] orders = StringUtil.split(orderBy, ",");
		for (String orderStr : orders) {
			if (StringUtils.isEmpty(orderStr))
				throw new IllegalArgumentException("Sort direction" + orderStr + "Is not a valid value");
		}

		this.orderBy = orderBy;
	}

	/**
	 * Whether it has set up sorting field, no default value.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	/**
	 * Was sort direction.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Set Sort by to the.
	 * 
	 * @param order
	 *            Optional value of desc or asc, multiple sort fields with ',' separated.
	 */
	public void setOrder(final String order) {
		// Check the legal value of the string order
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("Sort direction " + orderStr + " Is not a valid value");
		}

		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * Check whether they are automatically also the implementation of an object query to obtain the total number of
	 * records count, default is false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * Check whether they are automatically also the implementation of an object count query to obtain the total number
	 * of records.
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	// Access to query results function //

	/**
	 * List of pages of records obtained.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	public Map<T, List<String>> getMaps() {
		return maps;
	}

	public void setMaps(Map<T, List<String>> maps) {
		this.maps = maps;
	}

	/**
	 * To obtain the total number of records, the default value is -1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * TotalCount calculated according to pageSize and the total number of pages, the default value of -1.
	 */
	public long getTotalPages() {
		if (totalCount < 0)
			return -1;

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * Are there any page.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * Made on the next page the page number, serial number starting at 1. The current page for the last page still
	 * return to last page number.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * Is there Prev.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * To obtain the page number on the page, serial number starting at 1. The current page number for the home returns
	 * home.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	
	
}
