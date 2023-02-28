package net.miraeit.cmm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseTimeModel {

	@ApiModelProperty(example = "등록일")
	private String createdDate;

	@ApiModelProperty(example = "수정일")
	private String lastModifiedDate;
}
