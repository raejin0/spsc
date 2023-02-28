package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistStatuteList {
    @ApiModelProperty(example = "법 아이디")
    private Integer lawRelId;
    @ApiModelProperty(example = "법명")
    private String lawRelNm;
    @ApiModelProperty(example = "법 조항 아이디")
    private Integer lawRelDtlId;
    @ApiModelProperty(example = "법 조항")
    private Integer lawRelAtc;
    @ApiModelProperty(example = "법 조항 하위 구분")
    private Integer lawRelAtcSub;
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;

    public ChecklistStatute getChecklistStatute() {
        ChecklistStatute retVal = new ChecklistStatute();
        retVal.setLawRelId(lawRelId);
        retVal.setLawRelNm(lawRelNm);
        return retVal;
    }
}
