package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChecklistImplementStateRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "조직코드")
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
    @ApiModelProperty(example = "이행체크항목 아이디")
    private Integer itemId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "이행상태 N:선택하지 않음, X:불이행, V:이행")
    @Pattern(regexp = Regex.INSPECT, message = "V006")
    private String implState;
}
