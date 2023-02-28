package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistPerformanceMainGetResponse {
    @ApiModelProperty(example = "대분류 아이디")
    private Integer lawMainId;
    @ApiModelProperty(example = "대분류 번호")
    private Integer lawMainNo;
    @ApiModelProperty(example = "대분류 이름")
    private String lawMainNm;

}
