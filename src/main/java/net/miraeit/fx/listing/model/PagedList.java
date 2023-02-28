package net.miraeit.fx.listing.model;

import java.util.List;

/**
*
* @Class Name : PagedList.java
* @Description : 페이징 처리된 결과를 사용하기 위해 필요한 부가적인 값들에 대한 일반화 클래스. 
* @Modification Information
* @
* @     수정일     	  수정자                 수정내용
* @  ----------   	--------    ---------------------------
* @  2015.12.16     김동한       최초 생성
*
* @author 김동한
* @since 2015.12.16
* @version 1.0
* @see
*/
public class PagedList<T> {
	/** 현재 페이지 번호 */
	private Long currentPageNo;
	
	/** 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 */
	private Long linksPerPage;
	
	/** 하나의 페이지에 나타낼 행의 수 */
	private Long rowsPerPage;
	
	/** 조회 결과의 전체 행의 수 */
	private Long totalCount;
	
	/** 조회 결과의 전체 페이지 수 */
	private Long totalPageCount;
	
	/** 현재 페이지에서 첫 행이 가지는 역순번호( 총 10개라면 첫 행이 10 마지막 행이 1) */
	private Long maxNoOfCurrentPage;
	
	/** 조회 결과의 전체 페이지 수를 linksPerPage의 수로 나눌때 현재 페이지가 속하는 그룹의 번호 */
	private Long currentPageGroup;
	
	/** 전체 페이지의 그룹중 마지막 그룹의 번호 */
	private Long lastPageGroup;
	
	/** 현재 페이지의 시작 행번호 */
	private Long startRowNo;
	
	/** 현재 페이지의 끝 행번호 */
	private Long endRowNo;
	
	/** 현재 페이지 그룹에 포함되는 페이지 번호 목록 */
	private List<Long> linkPages;
	
	/** 현재 페이지의 목록 객체 */
	private List<T> list;
	
	/** 다음 페이지 그룹의 시작 페이지 번호 */
	private Long nextGroupPageNo;
	
	/** 이전 페이지 그룹의 마지막 페이지 번호 */
	private Long prevGroupPageNo;
	
	/** 이전 페이지 그룹의 존재 여부 */
	private boolean hasPrevPageGroup;
	
	/** 다음 페이지 그룹의 존재 여부 */
	private boolean hasNextPageGroup;

    /** 검색 조건 */
	private List<T> searchReq;
	
	/**
	 * 다음 페이지 그룹의 시작 페이지 번호 반환
	 * @return
	 */
	public Long getNextGroupPageNo() {
		return nextGroupPageNo;
	}
	
	/**
	 * 다음 페이지 그룹의 시작 페이지 번호 설정
	 * @param nextGroupPageNo 다음 페이지 그룹의 시작 페이지 번호
	 */
	public void setNextGroupPageNo(Long nextGroupPageNo) {
		this.nextGroupPageNo = nextGroupPageNo;
	}
	
	/**
	 * 이전 페이지 그룹의 마지막 페이지 번호 반환
	 * @return
	 */
	public Long getPrevGroupPageNo() {
		return prevGroupPageNo;
	}
	
	/**
	 * 이전 페이지 그룹의 마지막 페이지 번호 설정
	 * @param prevGroupPageNo 이전 페이지 그룹의 마지막 페이지 번호
	 */
	public void setPrevGroupPageNo(Long prevGroupPageNo) {
		this.prevGroupPageNo = prevGroupPageNo;
	}
	
	/**
	 * 이전 페이지 그룹의 존재 여부 반환
	 * @return
	 */
	public boolean isHasPrevPageGroup() {
		return hasPrevPageGroup;
	}
	/**
	 * 이전 페이지 그룹의 존재 여부 설정
	 * @param hasPrevPageGroup 이전 페이지 그룹의 존재 여부
	 */
	public void setHasPrevPageGroup(boolean hasPrevPageGroup) {
		this.hasPrevPageGroup = hasPrevPageGroup;
	}
	
	/**
	 * 다음 페이지 그룹의 존재 여부 반환
	 * @return
	 */
	public boolean isHasNextPageGroup() {
		return hasNextPageGroup;
	}
	
	/**
	 * 다음 페이지 그룹의 존재 여부 설정
	 * @param hasNextPageGroup 다음 페이 그룹의 존재 여부
	 */
	public void setHasNextPageGroup(boolean hasNextPageGroup) {
		this.hasNextPageGroup = hasNextPageGroup;
	}
	
	/**
	 * 현재 페이지 번호 반환
	 * @return
	 */
	public Long getCurrentPageNo() {
		return currentPageNo;
	}
	
	/**
	 * 현재 페이지 번호 설정
	 * @param currentPageNo 현재 페이지 번호
	 */
	public void setCurrentPageNo(Long currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	
	/**
	 * 하나의 페이지에 나타낼 행의 수 반환
	 */
	public Long getRowsPerPage() {
		return rowsPerPage;
	}
	
	/**
	 * 하나의 페이지에 나타낼 행의 수 설정
	 * @param rowsPerPage 하나의 페이지에 나타낼 행의 수
	 */
	public void setRowsPerPage(Long rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	/**
	 * 조회 결과의 전체 행의 수 반환
	 * @return
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 조회 결과의 전체 행의 수 설정
	 * @param totalCount 조회 결과의 전체 행의 수
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 조회 결과의 전체 페이지 수
	 * @return
	 */
	public Long getTotalPageCount() {
		return totalPageCount;
	}
	
	/**
	 * 조회 결과의 전체 페이지 수 설정
	 * @param totalPageCount 조회 결과의 전체 페이지 수
	 */
	public void setTotalPageCount(Long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	/**
	 * 현재 페이지에서 첫 행이 가지는 역순번호 반환
	 * @return
	 */
	public Long getMaxNoOfCurrentPage() {
		return maxNoOfCurrentPage;
	}
	
	/**
	 * 현재 페이지에서 첫 행이 가지는 역순번호 설정
	 * @param maxNoOfCurrentPage
	 */
	public void setMaxNoOfCurrentPage(Long maxNoOfCurrentPage) {
		this.maxNoOfCurrentPage = maxNoOfCurrentPage;
	}
	
	/**
	 * 전체 페이지의 그룹중 마지막 그룹의 번호 반환
	 * @return
	 */
	public Long getLastPageGroup() {
		return lastPageGroup;
	}
	
	/**
	 * 전체 페이지의 그룹중 마지막 그룹의 번호 설정
	 * @param lastPageGroup 전체 페이지의 그룹중 마지막 그룹의 번호
	 */
	public void setLastPageGroup(Long lastPageGroup) {
		this.lastPageGroup = lastPageGroup;
	}
	
	/**
	 * 다음 페이지 그룹의 시작 페이지 번호 반환
	 * @return
	 */
	public Long getNextGroupPage() {
		return (linkPages!=null && !linkPages.isEmpty() && isHasNextPageGroup())?linkPages.get(linkPages.size()-1)+1:totalPageCount;
	}
	
	/**
	 * 이전 페이지 그룹의 마지막 페이지 번호 반환
	 * @return
	 */
	public Long getPrevGroupPage() {
		return (linkPages!=null && !linkPages.isEmpty() && isHasPrevPageGroup())?linkPages.get(0)-1:1l;
	}
	
	/**
	 * 현재 페이지 그룹에 포함되는 페이지 번호 목록 반환
	 * @return
	 */
	public List<Long> getLinkPages() {
		return linkPages;
	}
	/**
	 * 현재 페이지 그룹에 포함되는 페이지 번호 목록 설정
	 * @param linkPages 현재 페이지 그룹에 포함되는 페이지 번호 목록
	 */
	public void setLinkPages(List<Long> linkPages) {
		this.linkPages = linkPages;
	}
	
	/**
	 * 현재 페이지의 목록 객체 반환
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	
	/**
	 * 현재 페이지의 목록 객체 설정
	 * @param list 현재 페이지의 목록 객체
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 반환
	 * @return
	 */
	public Long getLinksPerPage() {
		return linksPerPage;
	}
	
	/**
	 * 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 설정
	 * @param linksPerPage 조회 후 한번에 표현해줄 링크 가능한 페이지의 수
	 */
	public void setLinksPerPage(Long linksPerPage) {
		this.linksPerPage = linksPerPage;
	}
	
	/**
	 * 조회 결과의 전체 페이지 수를 linksPerPage의 수로 나눌때 현재 페이지가 속하는 그룹의 번호 반환
	 * @return
	 */
	public Long getCurrentPageGroup() {
		return currentPageGroup;
	}
	
	/**
	 * 조회 결과의 전체 페이지 수를 linksPerPage의 수로 나눌때 현재 페이지가 속하는 그룹의 번호 설정
	 * @param currentPageGroup 조회 결과의 전체 페이지 수를 linksPerPage의 수로 나눌때 현재 페이지가 속하는 그룹의 번호
	 */
	public void setCurrentPageGroup(Long currentPageGroup) {
		this.currentPageGroup = currentPageGroup;
	}
	
	/**
	 * 현재 페이지의 시작 행번호 반환
	 * @return
	 */
	public Long getStartRowNo() {
		return startRowNo;
	}
	
	/**
	 * 현재 페이지의 시작 행번호 설정
	 * @param startRowNo 현재 페이지의 시작 행번호
	 */
	public void setStartRowNo(Long startRowNo) {
		this.startRowNo = startRowNo;
	}
	
	/**
	 * 현재 페이지의 끝 행번호 반환
	 * @return
	 */
	public Long getEndRowNo() {
		return endRowNo;
	}
	
	/**
	 * 현재 페이지의 끝 행번호 설정
	 * @param endRowNo 현재 페이지의 끝 행번호
	 */
	public void setEndRowNo(Long endRowNo) {
		this.endRowNo = endRowNo;
	}

	public List<T> getSearchReq() {
		return searchReq;
	}

	public void setSearchReq(List<T> searchReq) {
		this.searchReq = searchReq;
	}
}
