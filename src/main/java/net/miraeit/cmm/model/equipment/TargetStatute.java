package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetStatute {
	@ApiModelProperty(example = "관련법령ID", dataType="long")
	private long lawRelId;
	@ApiModelProperty(example = "법명")
    private String lawRelNm;
}
