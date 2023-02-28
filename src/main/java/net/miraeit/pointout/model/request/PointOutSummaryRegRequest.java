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
public class PointOutSummaryRegRequest {
	@ApiModelProperty(example = "지적조치사항 총괄 ID")
	private int advId;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "관리번호")
	private String advNo;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "연도")
	private String optTimeYear;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "반기")
	private String optTimeHalf;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "주관부서코드")
	private String mngOrgCd;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "")
	private String chkOrgNm;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "")
	private String chkNm;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "")
	private String chkDt;
	@ApiModelProperty(example = "증빙파일 ID")
	private long evdFileId;
}
