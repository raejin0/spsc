package net.miraeit.equipment.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEquipmentResponse {

	@ApiModelProperty(example = "장비 id")
	private Integer equipId;
}
