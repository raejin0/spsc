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
public class MasterLawImpl implements Comparable<MasterLawImpl> {
	private Integer	lawCttsId;	    // 법령내용 ID
	private String	itmReqr;	    // 의무사항
	private String	implProd;	    // 이행주기
	private Integer	lawImplSort;	// 정렬순서
	private Integer	lawImplId;	    // 법령 이행 ID
	private String	itmChk;	        // 점검/확인사항 (필요여부 확실치 않음)
	private String	implProdCd;	    // 이행 상/하반기 구분
	private String	itmAttn;	    // 착안사항

	private Integer	lastLawImplId;  // 지난 반기 - 법령 이행 ID
	private Integer	lastLawCttsId;  // 지난 반기 - 법령내용 ID

	private Integer mstLawId;       // 마스터정보 id

	@Override
	public int compareTo(MasterLawImpl lawImpl) {
		if ( this.lastLawImplId > lawImpl.getLastLawImplId() ) {
			return 1;
		} else if ( this.lastLawImplId < lawImpl.getLastLawImplId() ) {
			return -1;
		} else {
			return 0;
		}
	}
}
