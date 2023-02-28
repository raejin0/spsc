package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDepartmentList {
    @ApiModelProperty(example = "주관 부서 코드")
    private String mngOrgCd;
    @ApiModelProperty(example = "주관 부서 이름")
    private String mngOrgNm;
}
