package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistMainDepartmentsList {
    @ApiModelProperty(example = "주관부서 코드")
    private String orgCd;
    @ApiModelProperty(example = "주관부서 이름")
    private String orgNm;
    @ApiModelProperty(example = "전체 조직명")
    private String fullOrgNm;
    @ApiModelProperty(example = "조직 타입 - 1:불가 3:가능")
    private Integer orgType;
    @ApiModelProperty(example = "상위조직 코드")
    private String parentOrgCd;
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
}
