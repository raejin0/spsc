package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterImplDeleteRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
}
