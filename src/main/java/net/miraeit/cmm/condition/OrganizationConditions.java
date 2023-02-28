package net.miraeit.cmm.condition;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationConditions {
	private Integer mstLawId;   // 마스터법령 ID
	private String fullOrgNm;   // 전체 조직명
}
