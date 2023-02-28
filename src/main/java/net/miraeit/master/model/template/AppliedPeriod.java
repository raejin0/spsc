package net.miraeit.master.model.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppliedPeriod {
    @ApiModelProperty(example = "적용시점 - 년도")
    private String optTimeYear;
    @ApiModelProperty(example = "적용시점 - 상/하반기 구분")
    private String optTimeHalf;
}
