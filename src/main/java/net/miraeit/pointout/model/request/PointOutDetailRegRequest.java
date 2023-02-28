package net.miraeit.pointout.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.pointout.model.PointOutSummary;

import javax.validation.constraints.NotNull;

import static net.miraeit.pointout.model.PointOutADMClassEnum.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutDetailRegRequest {
	@ApiModelProperty(example = "지적조치사항 상세 ID")
	private int advDtlId;
	@ApiModelProperty(example = "지적조치사항 상세 순번")
	private int advDtlSn;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적사항 구분코드")
	private String admCd;
	@ApiModelProperty(example = "장비(시설) 사용중지 수")
	private int cntStopFacility;
	@ApiModelProperty(example = "과태료 금액")
	private int amtFine;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적사항")
	private String advDtlText;
	@ApiModelProperty(example = "개선계획")
	private String imprPlan;
	@ApiModelProperty(example = "개선결과")
	private String imprRst;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "조직코드")
	private String orgCd;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "마스터 템플릿 ID")
	private int mstLawId;
	@ApiModelProperty(example = "조치전 증빙파일 ID")
	private long befFileId;
	@ApiModelProperty(example = "조치후 증빙파일 ID")
	private long aftFileId;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적조치사항 ID")
	private int advId;

	public PointOutSummary getSummaryForSTSMod() {
		PointOutSummary retVal =  PointOutSummary.builder()
			.advId(advId)
			.amtFineTotal(amtFine)
			.cntFine(amtFine>0 ? 1 : 0)
			.cntStopFacilityTotal(cntStopFacility)
			.build();

		if(사법조치.equalsCode(admCd))
			retVal.setCntJudicialAct(1);
		if(사용중지.equalsCode(admCd))
			retVal.setCntStopUse(1);
		if(시정명령.equalsCode(admCd))
			retVal.setCntCorrectOrder(1);
		if(시정지시.equalsCode(admCd))
			retVal.setCntCorrectInstruct(1);
		if(권고사항.equalsCode(admCd))
			retVal.setCntRecmd(1);
		if(기타사항.equalsCode(admCd))
			retVal.setCntEtc(1);

		return retVal;
	}
}
