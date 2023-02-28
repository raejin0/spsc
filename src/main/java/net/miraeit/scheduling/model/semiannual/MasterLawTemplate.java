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
public class MasterLawTemplate {
	private Integer	mstLawId;	    // 마스터법령ID
	private String	optTimeYear;	// 적용시점-년도
	private String	optTimeHalf;	// 적용시점-상/하반기 구분

	private Integer lastMstLawId;    // 지난 반기 - 마스터법령ID
//	private String	enactDt;	    // 제/개정일
//	private String	regDt;	        // 등록일
//	private String	note;	        // 참고사항
//	private String	modDt;	        // 수정일
}
