package net.miraeit.equipment.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.fx.listing.model.CommonPagingConditions;

@Getter @Setter
public class FindEquipmentListRequest extends CommonPagingConditions {

	@ApiModelProperty(example = "장비명")
	private String equipNm;
}
