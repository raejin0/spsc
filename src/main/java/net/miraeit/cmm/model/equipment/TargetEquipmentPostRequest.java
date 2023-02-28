package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetEquipmentPostRequest {
	@NotNull(message = "V000")
	@ApiModelProperty(example = "관련법령ID", dataType="Integer")
	private Integer lawRelId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "연도")
    private String optTimeYear;

	@NotNull(message = "V000")
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "조직코드", dataType="string")
	private String orgCd;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "설비ID", dataType = "Integer")
	private Integer equipId;

	private Integer orgLawRelEquipId;
}
