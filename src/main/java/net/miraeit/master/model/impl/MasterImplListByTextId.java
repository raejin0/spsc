package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterImplListByTextId {
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
    @ApiModelProperty(example = "의무사항")
    private String itmReqr;
    @ApiModelProperty(example = "착안사항")
    private String itmAttn;
    @ApiModelProperty(example = "점검/확인사항")
    private String itmChk;
    @ApiModelProperty(example = "이행주기")
    private String implProd;
    @ApiModelProperty(example = "이행 상/하반기 구분")
    private String implProdCd;
    @ApiModelProperty(example = "법령 이행 정렬")
    private Integer lawImplSort;
    private List<MainDepartmentList> mainDepartmentLists;
    private List<ExecutionDepartmentList> executionDepartmentLists;
    private List<StatuteList> statuteLists;
}
