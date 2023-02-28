package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChecklistPerformanceSubGetRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "대분류 아이디")
    private Integer lawMainId;
}
