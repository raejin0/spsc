package net.miraeit.pointout.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PointerOutExcelUploadRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example = "적용시점-년도")
	private String optTimeYear;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "적용시점-상/하반기 구분")
	private String optTimeHalf;
}
