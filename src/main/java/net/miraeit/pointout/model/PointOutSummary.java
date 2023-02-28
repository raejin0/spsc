package net.miraeit.pointout.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.cmm.model.file.FileDetailDTO;

import java.util.ArrayList;
import java.util.List;

import static net.miraeit.pointout.model.PointOutADMClassEnum.*;
import static net.miraeit.pointout.model.PointOutADMClassEnum.기타사항;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutSummary {
	@ApiModelProperty(example = "지적조치사항 총괄 ID")
	private Integer advId;
	@ApiModelProperty(example = "마스터 템플릿 ID")
	private Integer mstLawId;
	@ApiModelProperty(example = "지적조치사항 순번")
	private String advNo;
	@ApiModelProperty(example = "주관부서코드")
	private String mngOrgCd;
	@ApiModelProperty(example = "주관부서명")
	private String mngOrgNm;
	@ApiModelProperty(example = "점검기관명")
	private String chkOrgNm;
	@ApiModelProperty(example = "점검명")
	private String chkNm;
	@ApiModelProperty(example = "점검일")
	private String chkDt;
	@ApiModelProperty(example = "총위반건수")
	private int cntTotalViolation;
	@ApiModelProperty(example = "사법조치사항 건수")
	private int cntJudicialAct;
	@ApiModelProperty(example = "사용중지 건수")
	private int cntStopUse;
	@ApiModelProperty(example = "사용중지 장비(시설) 총수")
	private int cntStopFacilityTotal;
	@ApiModelProperty(example = "시정명령 건수")
	private int cntCorrectOrder;
	@ApiModelProperty(example = "시정지시 건수")
	private int cntCorrectInstruct;
	@ApiModelProperty(example = "과태료 건수")
	private int cntFine;
	@ApiModelProperty(example = "과태료 총액")
	private int amtFineTotal;
	@ApiModelProperty(example = "권고사항 건수")
	private int cntRecmd;
	@ApiModelProperty(example = "기타사항 건수")
	private int cntEtc;
	@ApiModelProperty(example = "주관부서명(전체)")
	private String fullMngOrgNm;
	@ApiModelProperty(example = "증빙파일 ID")
	private Integer evdFileId;
	@ApiModelProperty(example = "증빙파일 목록")
	private List<FileDetailDTO> evdFiles = new ArrayList<>();

	private List<PointOutDetail> pointOutDetailList = new ArrayList<>();


	public void addEvdFile(FileDetailDTO fileDTO) {
		if(evdFiles==null) evdFiles = new ArrayList<>();
		evdFiles.add(fileDTO);
	}

	public void subCntByAdmCd(String admCd) {
		if(사법조치.equalsCode(admCd))
			setCntJudicialAct(-1);
		if(사용중지.equalsCode(admCd))
			setCntStopUse(-1);
		if(시정명령.equalsCode(admCd))
			setCntCorrectOrder(-1);
		if(시정지시.equalsCode(admCd))
			setCntCorrectInstruct(-1);
		if(권고사항.equalsCode(admCd))
			setCntRecmd(-1);
		if(기타사항.equalsCode(admCd))
			setCntEtc(-1);
	}

	public void addCntByAdmCd(String admCd) {
		if(사법조치.equalsCode(admCd))
			setCntJudicialAct(1);
		if(사용중지.equalsCode(admCd))
			setCntStopUse(1);
		if(시정명령.equalsCode(admCd))
			setCntCorrectOrder(1);
		if(시정지시.equalsCode(admCd))
			setCntCorrectInstruct(1);
		if(권고사항.equalsCode(admCd))
			setCntRecmd(1);
		if(기타사항.equalsCode(admCd))
			setCntEtc(1);
	}
}
