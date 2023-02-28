package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChecklistPerformanceGetRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "조직")
    private String orgCd;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "연도")
    private String optTimeYear;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "대분류 아이디")
    private Integer lawMainId;
    @ApiModelProperty(example = "소분류 아이디")
    private Integer lawSubId;
    @ApiModelProperty(example = "관련법령 아이디")
    private Integer lawRelId;
}
