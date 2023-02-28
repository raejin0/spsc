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
public class MasterLawMain {
	private Integer lawMainId;      // 법령 대분류 ID
	private Integer lawMainNo;      // 법령 대분류 번호
	private String lawMainNm;       // 법령 대분류명
	private Integer mstLawId;       // 마스터법령ID
	private String disClaCode;      // 재해구분

	private Integer lastLawMainId;  // 지난 반기 - 법령 대분류 ID
}
