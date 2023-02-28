package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChecklistPerformanceInspectPatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "이행체크항목 아이디")
    private Integer itemId;
    @ApiModelProperty(example = "점검내용")
    private String inspect;
}
