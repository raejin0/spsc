package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistStatute {
    @ApiModelProperty(example = "법 아이디")
    private Integer lawRelId;
    @ApiModelProperty(example = "법명")
    private String lawRelNm;
}
