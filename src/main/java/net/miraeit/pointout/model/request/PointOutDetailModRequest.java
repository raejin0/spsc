package net.miraeit.pointout.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutDetailModRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적조치사항 상세 ID")
	private int advDtlId;
	@ApiModelProperty(example = "지적사항 구분코드")
	private String admCd;
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
	@ApiModelProperty(example = "조직코드")
	private String orgCd;
	@ApiModelProperty(example = "마스터 템플릿 ID")
	private int mstLawId;
}
