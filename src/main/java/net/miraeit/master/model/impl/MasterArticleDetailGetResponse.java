package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterArticleDetailGetResponse {
    @ApiModelProperty(example = "관련법령 ID")
    private Integer lawRelId;
    @ApiModelProperty(example = "관련법령 이름")
    private String lawRelNm;
    @ApiModelProperty(example = "관련법령 조항 ID")
    private Integer lawRelDtlId;
    @ApiModelProperty(example = "관련법령 조항")
    private String lawRelAtc;
    @ApiModelProperty(example = "관련법령 조항 내용")
    private String lawRelText;
}