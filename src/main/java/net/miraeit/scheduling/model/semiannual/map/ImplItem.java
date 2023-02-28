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
public class ImplItem {
	private Integer itemId;             // 이행체크항목ID
	private String orgCd;               // 부서 ID
	private String implState;           // 이행상태 N : 선택하지 않음 X : 부적합 V : 적합
	private Integer lawImplId;          // 법령 이행 ID
	private Integer implFileId;         // 이행 증빙파일 ID
	private Integer pointOutFileId;     // 지적사항 증빙파일 ID
	private Integer mstLawId;           // 마스터법령ID
	private String inspect;             // 점검내용
	private String inspectState;        // 점검사항 상태 I:개선 G:양호
	private String unimplReason;        // 부적합 사유

	private Integer lastItemId;         // 지난 반기 - 이행체크항목ID
	private Integer lastLawImplId;      // 지난 반기 - 법령 이행 ID
	private Integer lastMstLawId;       // 지난 반기 - 마스터법령ID
}
