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
public class MasterLawText {
	private Integer	lawCttsId;      // 법령내용 ID
	private Integer	lawSubId;       // 법령 소분류 ID
	private String	text;   	    // 내용
	private Integer	lawCttsSort;    // 정렬 순서

	private Integer lastLawCttsId;  // 지난반기 - 법령내용 ID
	private Integer lastLawSubId;   // 지난반기 - 법령 소분류 ID
}
