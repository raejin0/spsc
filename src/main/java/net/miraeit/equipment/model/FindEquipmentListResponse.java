package net.miraeit.equipment.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindEquipmentListResponse {
	@ApiModelProperty(example = "장비 id")
	private Integer equipId;

	@ApiModelProperty(example = "장비명")
	private String equipNm;
}
