package net.miraeit.checklist.model.inspect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class DeleteInspectContentRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "점검사항 ID")
	private Integer inspectId;

    @NotNull(message = "V000")
    @ApiModelProperty(example = "이행체크항목 아이디")
    private Integer implItemId;
}
