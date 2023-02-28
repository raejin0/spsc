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
public class MasterTemplatePatchRequest {
    @NotNull(message = "V000")
    @Max(value = 99999999, message = "V004")
    @ApiModelProperty(example = "마스터 템플릿 ID")
    private Integer mstLawId;
    @Pattern(regexp = Regex.DATE, message = "V003")
    @ApiModelProperty(example = "제/개정일")
    private String enactDt;
    @ApiModelProperty(example = "참고사항")
    private String note;
}
