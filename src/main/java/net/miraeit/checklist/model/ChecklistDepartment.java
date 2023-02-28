package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChecklistDepartment {
    @ApiModelProperty(example = "조직코드")
    private String orgCd;
    @ApiModelProperty(example = "조직명")
    private String orgNm;
    @ApiModelProperty(example = "전체 조직명")
    private String fullOrgNm;
    @ApiModelProperty(example = "조직 타입 - 1:불가 3:가능")
    private Integer orgType;
    @ApiModelProperty(example = "상위조직 코드")
    private String parentOrgCd;
    @ApiModelProperty(example = "이행리스트 아이디")
    private Integer itemId;

    public ChecklistDepartment(ChecklistDepartment checklistDepartment){
        this.orgCd = checklistDepartment.getOrgCd();
        this.orgNm = checklistDepartment.getOrgNm();
        this.fullOrgNm = checklistDepartment.getFullOrgNm();
        this.orgType = checklistDepartment.getOrgType();
        this.parentOrgCd = checklistDepartment.getParentOrgCd();
        this.itemId = checklistDepartment.getItemId();
    }
}
