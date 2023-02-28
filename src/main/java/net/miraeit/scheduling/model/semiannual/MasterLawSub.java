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
public class MasterLawSub {
	private Integer lawSubId;       // 법령 소분류 ID
	private Integer lawSubNo;       // 법령 소분류 번호
	private Integer lawMainId;      // 법령 대분류 ID
	private String lawSubEtc;       // 비고
	private String lawSubNm;        // 법령 소분류 이름
	private Integer lawSubSort;     // 정렬순서

	private Integer lastLawMainId;  // 지난반기 - 법령 대분류 ID
	private Integer lastLawSubId;   // 지난반기 - 법령 소분류 ID
}
