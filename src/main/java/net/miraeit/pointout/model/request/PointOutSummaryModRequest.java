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
public class PointOutSummaryModRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적조치사항 총괄 ID")
	private int advId;
	@ApiModelProperty(example = "관리번호")
	private String advNo;
	@ApiModelProperty(example = "점검기관명")
	private String chkOrgNm;
	@ApiModelProperty(example = "점검명")
	private String chkNm;
	@ApiModelProperty(example = "점검일")
	private String chkDt;
	@ApiModelProperty(example = "파일삭제여부")
	private boolean doDeleteFile;
	@ApiModelProperty(example = "증빙파일 ID")
	private int evdFileId;
}
