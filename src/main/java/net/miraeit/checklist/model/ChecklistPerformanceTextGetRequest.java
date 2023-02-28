package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChecklistPerformanceTextGetRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "소분류 아이디")
    private Integer lawSubId;
}
