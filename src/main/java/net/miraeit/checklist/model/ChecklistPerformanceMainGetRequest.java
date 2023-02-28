package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChecklistPerformanceMainGetRequest {
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "연도")
    private String optTimeYear;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;
}
