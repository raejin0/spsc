package net.miraeit.organization.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class PatchOrganizationResponse {

//	@NotEmpty(message = "V010")
	@NotNull(message = "V000")
	@ApiModelProperty(example = "조직코드")
	private String orgCd;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "마스터법령 ID")
	private Integer mstLawId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "조직유형(1: 이행조직 불가능, 3: 이행조직 가능)")
	private Integer orgType;

//	@NotEmpty(message = "V010")
	@NotNull(message = "V000")
	@ApiModelProperty(example = "주관부서 여부")
	private String mngYn;
}
