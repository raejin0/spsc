package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetEquipment {
	@ApiModelProperty(example = "대상설비ID", dataType="long")
	private long orgLawRelEquipId;
	@ApiModelProperty(example = "관련법령ID", dataType="long")
	private long lawRelId;
	private String lawRelNm;
	@ApiModelProperty(example = "마스터ID", dataType="long")
	private long mstLawId;
	@ApiModelProperty(example = "조직코드", dataType="string")
	private String orgCd;
	private String orgNm;
	private String fullOrgNm;
	@ApiModelProperty(example = "설비ID", dataType = "long")
	private long equipId;
	@ApiModelProperty(example = "설비명", dataType="string")
	private String equipNm;
}
