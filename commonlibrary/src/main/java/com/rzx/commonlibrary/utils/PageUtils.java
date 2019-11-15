package com.rzx.commonlibrary.utils;

/**
 * 分页请求页数控制
 * 
 * @author wanghang
 */
public class PageUtils {

	public static final int DEFAULT_FIRST_PAGE = 1;

	private Integer currentPage = DEFAULT_FIRST_PAGE;

	private int perPage = 8;

	public int getCurrentPage() {
		synchronized (this.currentPage) {
			return currentPage;
		}
	}

	public String getCurrentPageForString() {
		return String.valueOf(currentPage);
	}

	public void setCurrentPage(int currentPage) {
		synchronized (this.currentPage) {
			this.currentPage = currentPage;
		}
	}

    public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public void nextPage() {
		synchronized (this.currentPage) {
			this.currentPage += 1;
		}
	}

	public void rollBackPage() {
		synchronized (this.currentPage) {
			if (this.currentPage > 1) {
				this.currentPage -= 1;
			}
		}
	}

	public boolean isFirstPage() {
		return currentPage == DEFAULT_FIRST_PAGE;
	}

	public void setFirstPage() {
		setCurrentPage(DEFAULT_FIRST_PAGE);
	}

}
