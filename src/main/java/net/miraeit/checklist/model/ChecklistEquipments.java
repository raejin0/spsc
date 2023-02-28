package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistEquipments {
    @ApiModelProperty(example = "대상설비 아이디")
    private Integer equipId;
    @ApiModelProperty(example = "대상설비 이름")
    private String equipNm;
}
