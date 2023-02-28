package net.miraeit.master.model.text;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterTextDeleteRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령내용 ID")
    private Integer lawCttsId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "강제 삭제 여부")
    private boolean forced;
}
