package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterArticleDeleteRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 조항 ID")
    private Integer lawRelDtlId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "강제 삭제 여부")
    private boolean forced;
}
