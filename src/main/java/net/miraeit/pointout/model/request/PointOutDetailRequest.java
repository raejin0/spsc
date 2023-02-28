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
public class PointOutDetailRequest extends CommonPagingConditions {
	@NotNull(message = "V000")
	@ApiModelProperty(example = "지적조치사항 총괄 ID")
	private int advId;
	@ApiModelProperty(example = "행정조치사항")
	private String admCd;
	@ApiModelProperty(example = "지적사항")
	private String advDtlText;
	@ApiModelProperty(example = "조직명")
	private String orgNm;
}
