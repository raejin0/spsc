package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistPerformanceTextGetResponse {
    @ApiModelProperty(example = "관련법령 아이디")
    private Integer lawCttsId;
    @ApiModelProperty(example = "관련법령 명")
    private String text;

}
