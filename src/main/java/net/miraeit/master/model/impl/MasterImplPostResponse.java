package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class MasterImplPostResponse {
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
    @ApiModelProperty(example = "의무 사항")
    private String itmReqr;
    @ApiModelProperty(example = "착안 사항")
    private String itmAttn;
    @ApiModelProperty(example = "점검/확인 사항")
    private String itmChk;
    @ApiModelProperty(example = "이행 주기")
    private String implProd;
    @ApiModelProperty(example = "이행 주기 코드")
    private String implProdCd;
    @ApiModelProperty(example = "법령 내용 ID")
    private Integer lawCttsId;
    @ApiModelProperty(example = "법령 이행 정렬")
    private Integer lawImplSort;
    @ApiModelProperty(example = "법조항 ID")
    private List<Integer> lawRelDtlIdList;
    @ApiModelProperty(example = "주관부서 코드")
    private List<String> mngOrgCdList;
    @ApiModelProperty(example = "이행부서 코드")
    private List<String> orgCdList;

    public MasterImplPostResponse(MasterImplPostRequest request){
        this.lawImplId = request.getLawImplId();
        this.itmReqr = request.getItmReqr();
        this.itmAttn = request.getItmAttn();
        this.itmChk = request.getItmChk();
        this.implProd = request.getImplProd();
        this.implProdCd = request.getImplProdCd();
        this.lawCttsId = request.getLawCttsId();
        this.lawImplSort = request.getLawImplSort();
        this.lawRelDtlIdList = request.getLawRelDtlIdList();
        this.mngOrgCdList = request.getMngOrgCdList();
        this.orgCdList = request.getOrgCdList();
    }
}
