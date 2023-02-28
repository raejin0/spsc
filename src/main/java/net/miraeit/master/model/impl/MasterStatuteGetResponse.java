package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterStatuteGetResponse {
    @ApiModelProperty(example = "법령 ID")
    private Integer lawRelId;
    @ApiModelProperty(example = "법령 이름")
    private String lawRelNm;
}
