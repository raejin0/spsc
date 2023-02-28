package net.miraeit.master.model.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MasterTemplateGetRequest {
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "적용시점 - 년도")
    private String optTimeYear;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "적용시점 - 상/하반기 구분")
    private String optTimeHalf;
}
