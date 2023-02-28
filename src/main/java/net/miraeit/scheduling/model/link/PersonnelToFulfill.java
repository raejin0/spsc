package net.miraeit.scheduling.model.link;

import lombok.Getter;
import lombok.Setter;

/**
 * 이행 담당자 ( 반기 미이행 부서 담당자 )
 */
@Getter @Setter
public class PersonnelToFulfill {
	private String userId;
	private String userNm;
	private String jikgubNm;
	private String posNm;
	private String email;
	private String fullOrgNm;
	private String orgCd;
	private String jikgubCd;
	private String posCd;
}
