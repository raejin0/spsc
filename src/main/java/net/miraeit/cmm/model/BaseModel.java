package net.miraeit.cmm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel extends BaseTimeModel{

	@ApiModelProperty(example = "등록자")
	private String createdBy;

	@ApiModelProperty(example = "수정자")
	private String lastModifiedBy;
}
