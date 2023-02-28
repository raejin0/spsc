package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChecklistPerformanceInspectStatePatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "이행체크항목 아이디")
    private Integer itemId;
    @Pattern(regexp = Regex.INSPECT_STATE, message = "V007")
    @ApiModelProperty(example = "점검내용")
    private String inspectState;
}
