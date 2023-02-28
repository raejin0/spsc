package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatuteList {
    @ApiModelProperty(example = "법 조항 ID")
    private Integer lawRelDtlId;

	@ApiModelProperty(example = "관련법령 조항")
    private Integer lawRelAtc;

    @ApiModelProperty(example = "관련법령 조항 하위 구분")
    private Integer lawRelAtcSub;

    @ApiModelProperty(example = "법령 ID")
    private Integer lawRelId;

    @ApiModelProperty(example = "법령 이름")
    private String lawRelNm;
}
