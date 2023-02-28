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
public class MasterImplPostRequest {
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "의무 사항")
    private String itmReqr;
    @ApiModelProperty(example = "착안 사항")
    private String itmAttn;
    @ApiModelProperty(example = "점검/확인 사항")
    private String itmChk;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "이행 주기")
    private String implProd;
    @Pattern(regexp = Regex.HALF_YEAR, message = "V002")
    @ApiModelProperty(example = "이행 주기 코드")
    private String implProdCd;
    @NotNull(message = "V000")
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
    @ApiModelProperty(notes = "순서상 바로 앞의 이행내용 ID")
    private Integer nextOf;
}
