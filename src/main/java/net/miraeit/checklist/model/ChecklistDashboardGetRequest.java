package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChecklistDashboardGetRequest {
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "연도")
    private String optTimeYear;
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;
    @ApiModelProperty(example = "조직코드(사업소, 부처 등)")
    private String orgCd;
    @ApiModelProperty(example = "재해구분(serious, civil)")
    private String disClaCode;
    @ApiModelProperty(example = "주관부서코드")
    private String mngOrgCd;
}
