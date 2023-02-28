package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutionDepartmentList {
    @ApiModelProperty(example = "이행 부서 코드")
    private String orgCd;
    @ApiModelProperty(example = "이행 부서 이름")
    private String orgNm;
    @ApiModelProperty(example = "이행 부서 전체 이름")
    private String fullOrgNm;
    @ApiModelProperty(example = "상위 조직 코드")
    private String parentOrgCd;
}
