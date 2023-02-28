package net.miraeit.fx.listing;

import net.miraeit.fx.listing.model.CommonPagingConditions;
import net.miraeit.fx.listing.model.PagedList;

import java.util.ArrayList;

/**
*
* @Class Name : PagedListVarValuesGenerator.java
* @Description : PagedListVars의 객체를 자동 생성하기 위한 클래스 
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
public class PagedListValuesGenerator {
	/** 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 기본값 */ 
	public final static Long DEFAULT_LINKS_PER_PAGE = 10l;
	
	/** 하나의 페이지에 나타낼 행의 수 기본값 */
	public final static Long DEFAULT_FETCH_COUNT = 10l;
	
	/** 한페이지에 전체 목록을 나타내기 위해 사용할 행의 수를 표현하는 상수 */
	public final static Long FETCH_COUNT_FULLSCAN = -1l;
	
	/**
	 * PagedListVars를 생성하기 위한 static 메소드. 오류가 아닌 이상 가급적 변경하지 말 것.
	 * @param pagingConditions 페이징을 하기 위한 조건 객체
	 * @param totalCount 조회결과의 전체 페이지 수
	 * @return
	 */
	public static <T> PagedList<T> getPagedListVarValues(CommonPagingConditions pagingConditions, Long totalCount) {
		Long currentPageNo = pagingConditions.getCurrentPageNo();
		if (currentPageNo < 1) currentPageNo = 1l;
		
		Long linksPerPage = pagingConditions.getLinksPerPage();
		if(linksPerPage <= 0) linksPerPage = DEFAULT_LINKS_PER_PAGE;
		
		Long fetchCount = pagingConditions.getFetchCount();
		if(fetchCount == FETCH_COUNT_FULLSCAN) {
			fetchCount = totalCount; 
		} else if(fetchCount <= 0) {
			fetchCount = DEFAULT_FETCH_COUNT;
		}
		
		Long maxNoOfCurrentPage = totalCount - (fetchCount*(currentPageNo-1));
		Long totalPageCount = 0l;
		if(fetchCount != 0) {
			totalPageCount = totalCount/fetchCount;
			if((totalCount%fetchCount) != 0)
				totalPageCount++;
		}
		
		Long currentPageGroup = (currentPageNo-1) / linksPerPage;
		Long lastPageGroup = (totalPageCount-1) / linksPerPage;
		Long startRowNo = (currentPageNo-1) * fetchCount;
		Long endRowNo = startRowNo + fetchCount;
		
		pagingConditions.setStartRowNo(startRowNo);
		pagingConditions.setEndRowNo(endRowNo);
		
		ArrayList<Long> linkPages = new ArrayList<Long>();
		for(Long i = (currentPageGroup*linksPerPage)+1 ; i<=(currentPageGroup+1)*linksPerPage && i<=totalPageCount ; i++) {
			linkPages.add(new Long(i));
		}
		
		PagedList<T> listVars = new PagedList<T>();
		listVars.setTotalCount(totalCount);
		listVars.setCurrentPageNo(currentPageNo);
		listVars.setLinksPerPage(linksPerPage);
		listVars.setRowsPerPage(fetchCount);
		listVars.setMaxNoOfCurrentPage(maxNoOfCurrentPage);
		listVars.setTotalPageCount(totalPageCount);
		listVars.setCurrentPageGroup(currentPageGroup);
		listVars.setLastPageGroup(lastPageGroup);
		listVars.setStartRowNo(startRowNo);
		listVars.setEndRowNo(endRowNo);
		listVars.setLinkPages(linkPages);
		
		listVars.setHasPrevPageGroup(currentPageGroup>0);
		listVars.setHasNextPageGroup(currentPageGroup<lastPageGroup);
		listVars.setNextGroupPageNo(listVars.getNextGroupPage());
		listVars.setPrevGroupPageNo(listVars.getPrevGroupPage());

		pagingConditions.setFetchCount(fetchCount);
		
		return listVars;
	}
}

