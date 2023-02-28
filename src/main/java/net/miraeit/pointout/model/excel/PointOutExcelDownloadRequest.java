package net.miraeit.pointout.model.excel;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutExcelDownloadRequest {
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.YEAR, message = "V001")
    @ApiModelProperty(example = "적용시점 - 년도")
    private String optTimeYear;

    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "적용시점 - 상/하반기 구분")
    private String optTimeHalf;

    @ApiModelProperty(example = "점검일(무시) - 필요없으나 공통 쿼리에 존재하기 때문에 넣음")
    private String chkDt;
}
