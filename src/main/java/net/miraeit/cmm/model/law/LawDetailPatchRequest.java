package net.miraeit.cmm.model.law;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawDetailPatchRequest {

	@ApiModelProperty(example = "관련법령 순번")
	private Integer lawRelDtlId;

	@ApiModelProperty(example = "관련법령 조항")
	private String lawRelAtc;

	@ApiModelProperty(example = "관련법령 내용")
	private String lawRelText;

	@ApiModelProperty(example = "관련법령 ID")
	private Integer lawRelId;
}
