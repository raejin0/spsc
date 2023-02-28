package net.miraeit.scheduling.model.semiannual.map;

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
public class ImplOrgMap {
	private Integer	lawImplId;	// 법령 이행 ID
	private String	orgCd;	    // 조직ID
	private Integer	mstLawId;	// 마스터법령ID

	private Integer lastLawImplId; // 지난 반기 - 법령 이행 ID

}