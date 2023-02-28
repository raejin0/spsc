package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistPerformanceSubGetResponse {
    @ApiModelProperty(example = "소분류 아이디")
    private Integer lawSubId;
    @ApiModelProperty(example = "소분류 번호")
    private Integer lawSubNo;
    @ApiModelProperty(example = "소분류 이름")
    private String lawSubNm;
    @ApiModelProperty(example = "비고")
    private String lawSubEtc;

}
