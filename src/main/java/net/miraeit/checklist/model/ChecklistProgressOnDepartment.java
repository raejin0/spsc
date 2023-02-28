package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistProgressOnDepartment {
    @ApiModelProperty(example = "조직정보")
    private ChecklistDepartment department;
    @ApiModelProperty(example = "이행률")
    private Double progress;
    @ApiModelProperty(example = "하위 이행체크리스트 아이템 갯수")
    private Integer itemCnt;
}
