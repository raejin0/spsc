package net.miraeit.checklist.model.inspect;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInspectContentResponse {

	@ApiModelProperty(example = "점검사항 ID")
	private Integer inspectId;

}
