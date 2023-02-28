package net.miraeit.master.model.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterSubDeleteRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령내용 ID")
    private Integer lawSubId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "강제 삭제 여부")
    private boolean forced;
}
