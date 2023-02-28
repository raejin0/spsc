package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TargetEquipmentOnLawGetRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example="연도", dataType="String")
	private String optTimeYear;
	@NotNull(message = "V000")
	@ApiModelProperty(example="반기", dataType="String")
	private String optTimeHalf;
	@NotNull(message = "V000")
	@ApiModelProperty(example = "관련법령 아이디 리스트", dataType="Integer")
	private List<Integer> lawRelIds;
}
