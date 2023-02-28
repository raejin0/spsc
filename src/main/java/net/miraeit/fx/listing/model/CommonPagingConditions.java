package net.miraeit.fx.listing.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.fx.listing.PagedListValuesGenerator;

/**
*
* @Class Name : CommonPagignConditions.java
* @Description : 페이징 처리된 목록을 출력하기 위해 자주 사용되는 조건들의 일반화 클래스
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
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPagingConditions {
	/** 현재의 페이지 번호 */
	private long currentPageNo;
	/** 조회할 페이지의 시작 행번호 */
	private long startRowNo;
	/** 조회할 페이지의 끝 행번호 */
	private long endRowNo;
	/** 하나의 페이지에 나타낼 행의 수 */
	private long fetchCount;
	/** 조회 후 한번에 표현해줄 링크 가능한 페이지의 수 */
	private long linksPerPage;
	private boolean pageByIndex;

	/**
	 * 조회할 페이지 번호를 반환한다.
	 * @return
	 */
	public long getCurrentPageNo() {
		if(currentPageNo<=0) currentPageNo = 1L;
		return currentPageNo;
	}

	/**
	 * 하나의 페이지에 나타낼 행의 수를 반환한다.
	 * @return
	 */
	public long getFetchCount() {
		if(fetchCount==0) return PagedListValuesGenerator.DEFAULT_FETCH_COUNT;
		return fetchCount;
	}

}
