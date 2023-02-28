package net.miraeit.master.model.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MasterTemplatePostRequest {
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "적용시점 - 년도")
    private String optTimeYear;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "적용시점 - 상/하반기 구분")
    private String optTimeHalf;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.DATE, message = "V003")
    @ApiModelProperty(example = "제/개정일")
    private String enactDt;
    @ApiModelProperty(example = "참고사항")
    private String note;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "복사여부")
    private boolean copy;
    @ApiModelProperty(notes = "템플릿 ID", hidden = true)
    private Integer mstLawId;
}
