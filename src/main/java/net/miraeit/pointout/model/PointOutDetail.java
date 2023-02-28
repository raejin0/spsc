package net.miraeit.pointout.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.pointout.model.request.PointOutDetailModRequest;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutDetail {
	@ApiModelProperty(example = "지적사항 상세 아이디")
	private Integer advDtlId;
	@ApiModelProperty(example = "지적사항 상세 순번")
	private Integer advDtlSn;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적사항 구분코드")
	private String admCd;
	@ApiModelProperty(example = "지적사항 구분코드 명")
	private String admCdNm;
	@ApiModelProperty(example = "장비(시설) 사용중지 수")
	private int cntStopFacility;
	@ApiModelProperty(example = "과태료 금액")
	private int amtFine;
	@ApiModelProperty(example = "지적사항")
	private String advDtlText;
	@ApiModelProperty(example = "개선계획")
	private String imprPlan;
	@ApiModelProperty(example = "개선결과")
	private String imprRst;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "조직코드")
	private String orgCd;
	@ApiModelProperty(example = "조직명")
	private String orgNm;
	@ApiModelProperty(example = "전체 조직명")
	private String fullOrgNm;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "마스터 템플릿 ID")
	private Integer mstLawId;
	@ApiModelProperty(example = "조치전 증빙파일 ID")
	private Integer befFileId;
	@ApiModelProperty(example = "조치후 증빙파일 ID")
	private Integer aftFileId;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적조치사항 ID")
	private Integer advId;
	@ApiModelProperty(example = "조치전 파일 목록")
	private List<FileDetailDTO> befFiles; // 리스트로 관리할 필요는 없지만 시스템상 목록이 맞으니 그렇게 처리
	@ApiModelProperty(example = "조치후 파일 목록")
	private List<FileDetailDTO> aftFiles;

	public void addBefFile(FileDetailDTO befFile) {
		if(befFiles==null) befFiles = new ArrayList<>();
		befFiles.add(befFile);
	}

	public void addAftFile(FileDetailDTO aftFile) {
		if(aftFiles==null) aftFiles = new ArrayList<>();
		aftFiles.add(aftFile);
	}


	public PointOutSummary getSummaryForSTSMod(PointOutDetailModRequest request) {
		PointOutSummary retVal = PointOutSummary.builder().advId(advId).build();
		retVal.setAmtFineTotal(request.getAmtFine() - amtFine);
		retVal.setCntStopFacilityTotal(request.getCntStopFacility() - cntStopFacility);

		if(!admCd.equals(request.getAdmCd())) {
			retVal.subCntByAdmCd(admCd);
			retVal.addCntByAdmCd(request.getAdmCd());
		}

		if(amtFine > 0 && request.getAmtFine()<=0) // 물론 적을일은 없겠지만
			retVal.setCntFine(-1);
		if(amtFine == 0 && request.getAmtFine()>0)
			retVal.setCntFine(1);

		return retVal;
	}

	public PointOutSummary getSummaryForSTSCut() {
		PointOutSummary retVal = PointOutSummary.builder().advId(advId).build();
		retVal.subCntByAdmCd(admCd);
		retVal.setAmtFineTotal(-amtFine);
		retVal.setCntStopFacilityTotal(-cntStopFacility);
		if(amtFine>0) retVal.setCntFine(-1);
		return retVal;
	}
}
