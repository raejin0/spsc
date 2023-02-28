package net.miraeit.master.model.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterMainDeleteRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령대분류 ID")
    private Integer lawMainId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "강제 삭제 여부")
    private boolean forced;
}
