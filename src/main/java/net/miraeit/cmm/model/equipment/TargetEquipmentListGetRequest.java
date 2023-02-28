package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TargetEquipmentListGetRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example="연도", dataType="String")
	private String optTimeYear;
	@NotNull(message = "V000")
	@ApiModelProperty(example="반기", dataType="String")
	private String optTimeHalf;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "관련법령 아이디", dataType="Integer")
	private Integer lawRelId;
}
