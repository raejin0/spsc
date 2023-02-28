package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterStatutePatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 ID")
    private Integer lawRelId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령명")
    private String lawRelNm;
}
