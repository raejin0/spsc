package net.miraeit.pointout.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.fx.listing.model.CommonPagingConditions;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutSummaryRequest extends CommonPagingConditions {
	@ApiModelProperty(example = "지적조치사항 총괄 ID")
	private int advId;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "연도")
	private String optTimeYear;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "반기")
	private String optTimeHalf;
	@ApiModelProperty(example = "점검일")
	private String chkDt;
}
