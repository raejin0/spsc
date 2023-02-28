package net.miraeit.cmm.model.law;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LawDetailPostRequest {

	@ApiModelProperty(example = "관련법령 조항")
	private String lawRelAtc;

	@ApiModelProperty(example = "관련법령 내용")
	private String lawRelText;

	@ApiModelProperty(example = "관련법령 ID")
	private Integer lawRelId;
}
