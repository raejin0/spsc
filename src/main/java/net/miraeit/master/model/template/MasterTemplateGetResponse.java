package net.miraeit.master.model.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.master.model.main.MasterMainListByLawId;

import java.util.List;

@Getter
@Setter
public class MasterTemplateGetResponse {
    @ApiModelProperty(example = "마스터 법령 ID")
    private Integer mstLawId;
    @ApiModelProperty(example = "적용시점 - 년도")
    private String optTimeYear;
    @ApiModelProperty(example = "적용시점 - 상/하반기 구분")
    private String optTimeHalf;
    @ApiModelProperty(example = "제/개정일")
    private String enactDt;
    @ApiModelProperty(example = "참고사항")
    private String note;
//    @ApiModelProperty(example = "조항(list)")
    private List<MasterMainListByLawId> masterMainListByLawId;
    private List<AppliedPeriod> appliedPeriodList;
}
