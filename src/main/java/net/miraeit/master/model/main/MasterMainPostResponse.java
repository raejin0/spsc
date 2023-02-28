package net.miraeit.master.model.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterMainPostResponse {
    @ApiModelProperty(example = "법령 대분류 아이디")
    private Integer lawMainId;
    @ApiModelProperty(example = "법령 대분류 번호")
    private Integer lawMainNo;
    @ApiModelProperty(example = "법령 대분류 이름")
    private String lawMainNm;
    @ApiModelProperty(example = "템플릿 ID")
    private Integer mstLawId;
    @ApiModelProperty(example = "재해구분 코드")
    private String disClaCode;

    public MasterMainPostResponse(Integer lawMainId,MasterMainPostRequest request){
        this.lawMainId = lawMainId;
        this.lawMainNo = request.getLawMainNo();
        this.lawMainNm = request.getLawMainNm();
        this.mstLawId = request.getMstLawId();
        this.disClaCode = request.getDisClaCode();
    }
}
