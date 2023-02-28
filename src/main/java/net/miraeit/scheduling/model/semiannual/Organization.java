package net.miraeit.scheduling.model.semiannual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Organization { // 미사용중
	private String	orgCd;	            //조직코드
	private String	orgNm;	            //조직명
	private Integer	orgType;	        //조직유형(1: 이행조직 불가능, 3: 이행조직 가능)
	private String	parentOrgCd;	    //상위 조직 코드
	private String	bonsaYn;	        //본사여부 (본사 'Y' 그외 'N')
	private String	firstOrgCd;	        //1차조직코드(1차사업소)
	private String	mngYn;	            //주관부서 Y/N
	private String	useYn;	            //사용여부
	private String	excType;	        //예외 타입 (N : 예외X , B : 본사소속이 발전소)
	private String	excParent;	        //예외 상황의 상위 조직 코드
	private Integer	mstLawId;	        //마스터법령ID
	private String	excFirstOrgCd;	    //예외 상황의 1차조직코드
	private String	fullOrgNm;	        //전체 조직명
	private String	orgOrderCd;	        //직제순서코드
	private String	jojikjang;	        //조직장
	private Integer	orgRank;	        //조직순차

	private String  lastRrgCd;          // 지난 반기 - 조직코드
	private Integer lastMstLawId;           // 지난 반기 - 마스터법령ID


}
