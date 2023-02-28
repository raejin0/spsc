package net.miraeit.master.model.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MasterTemplateDeleteRequest {
    @NotNull(message = "V000")
    @Max(value = 99999999, message = "V004")
    @ApiModelProperty(example = "마스터 템플릿 ID")
    private Integer mstLawId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "강제 삭제 여부")
    private boolean forced;
}
