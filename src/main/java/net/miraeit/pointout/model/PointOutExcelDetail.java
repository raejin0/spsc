package net.miraeit.pointout.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.miraeit.pointout.model.excel.ExcelBaseModel;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PointOutExcelDetail extends ExcelBaseModel {
	private Integer	advDtlId;	        // 지적조치사항 상세 ID
	private Integer	advDtlSn;	        // 지적조치사항 상세 순번
	private String	admCd;	            // 행정조치사항 구분
	private String	admNm;	            // 행정조치사항명
	private int	    cntStopFacility;	// 사용중지 시설 수
	private int	    amtFine;	        // 과태료
	private String	advDtlText;	        // 지적사항
	private String	imprPlan;	        // 개선계획
	private String	imprRst;	        // 개선조치결과
	private String	orgCd;	            // 부서코드(조치부서)
	private String	orgNm;	            // 부서명(조치부서)
	private Integer	mstLawId;	        // 마스터 템플릿 ID
	private Integer	befFileId;	        // 조치전 증빙자료 ID
	private Integer	aftFileId;	        // 조치후 증빙자료 ID
	private Integer	advId;	            // 지적조치사항 총괄 ID

}
