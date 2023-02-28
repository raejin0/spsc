package net.miraeit.checklist.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChecklistDashboardGetResponse {
    @ApiModelProperty(example = "연도")
    private String optTimeYear;
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;
    @ApiModelProperty(example = "조직코드")
    private String orgCd;
    @ApiModelProperty(example = "재해구분")
    private String disClaCode;
    @ApiModelProperty(example = "주관부서코드")
    private String mngOrgCd;
    @ApiModelProperty(example = "이행률 정보")
    private ChecklistProgressOnDepartment progressOnTargetOrg;
    private List<ChecklistProgressOnDepartment> progressListOnMainDepartments = new ArrayList<>();
    private List<ChecklistProgressOnDepartment> progressListOnImplDepartments = new ArrayList<>();
}
