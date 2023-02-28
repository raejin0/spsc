package net.miraeit.scheduling.model.semiannual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 조직 관련법령별 대상설비
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrgLawRelEquip {
	private Integer	orgLawRelEquipId;	// 조직 관련법령별 대상설비 아이디
	private Integer	mstLawId;	        // 마스터법령ID
	private Integer	equipId;	        // 대상설비ID
	private String	orgCd;	            // 조직코드
	private Integer	lawRelId;	        // 관련법령ID

	private Integer	lastOrgLawRelEquipId;	// 지난 반기 - 조직 관련법령별 대상설비 아이디
}
