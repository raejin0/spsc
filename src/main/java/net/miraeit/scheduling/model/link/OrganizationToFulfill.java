package net.miraeit.scheduling.model.link;

import lombok.Getter;
import lombok.Setter;

/**
 * 이행 부서 ( 해당 반기 준수사항 미이행 부서 )
 */
@Getter @Setter
public class OrganizationToFulfill {
	private String orgCd;
	private String fullOrgNm;
}