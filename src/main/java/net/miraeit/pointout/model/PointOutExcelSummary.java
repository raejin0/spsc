package net.miraeit.pointout.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.pointout.model.excel.Attachment;
import net.miraeit.pointout.model.excel.ExcelBaseModel;

import java.util.List;


/**
 * 엑셀 총괄표 관련 정보
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutExcelSummary extends ExcelBaseModel {
	private List<Attachment> attachments; // 첨부파일 리스트
	private Integer rowNumber;              // 행번호 for image

	private Integer advId;                  // 지적/조치사항 총괄 id
	private String  advNo;                  // 관리번호ExcelPointOutSummary
	private String  mngOrgCd;               // 총괄관리부서 코드
	private String  mngOrgNm;               // 총괄관리부서명
	private String  chkOrgNm;               // 기관명
	private String  chkNm;                  // 점검명
	private String  chkDt;                  // 점검일
	private Integer cntTotalViolation;      // 총위반건수
	private Integer cntJudicialAct;         // 사법조치(건수)
	private Integer cntStopUse;             // 사용중지(건수)
	private Integer cntStopFacilityTotal;   // 사용중지(대수) : 장비 및 시설
	private Integer cntCorrectOrder;        // 시정명령(건수)
	private Integer cntCorrectInstruct;     // 시정지시(건수)
	private Integer cntFine;                // 과태료 위반조항수
	private Integer amtFineTotal;           // 과태료 금액
	private Integer cntRecmd;               // 권고사항(건수)
	private Integer cntEtc;                 // 기타사항(건수)
	private Integer evdFileId;              // 증빙 파일 id

}
