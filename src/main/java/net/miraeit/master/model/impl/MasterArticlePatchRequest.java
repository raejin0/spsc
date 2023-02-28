package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterArticlePatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 조항")
    private Integer lawRelAtc;

    @ApiModelProperty(example = "관련법령 조항 하위구분")
    private Integer lawRelAtcSub;

//    @NotEmpty(message = "V010")
	@NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 내용")
    private String lawRelText;

    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 조항 ID")
    private Integer lawRelDtlId;

}
