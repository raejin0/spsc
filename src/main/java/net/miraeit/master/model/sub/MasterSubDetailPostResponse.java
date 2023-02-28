package net.miraeit.master.model.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterSubDetailPostResponse {
    @ApiModelProperty(example = "법령 소분류 ID")
    private Integer lawSubId;
    @ApiModelProperty(example = "법령 소분류 번호")
    private Integer lawSubNo;
    @ApiModelProperty(example = "법령 소분류 이름")
    private String lawSubNm;
    @ApiModelProperty(example = "법령 대분류 ID")
    private Integer lawMainId;
    @ApiModelProperty(example = "비고")
    private String lawSubEtc;
    @ApiModelProperty(example = "순번")
    private Integer lawSubSort;
    @ApiModelProperty(example = "법령 텍스트 ID")
    private Integer lawTextId;
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;

    public MasterSubDetailPostResponse(Integer lawSubId,Integer lawTextId,Integer lawImplId, MasterSubPostRequest request){
        this.lawSubId = lawSubId;
        this.lawTextId = lawTextId;
        this.lawImplId = lawImplId;
        this.lawSubNo = request.getLawSubNo();
        this.lawSubNm = request.getLawSubNm();
        this.lawMainId = request.getLawMainId();
        this.lawSubEtc = request.getLawSubEtc();
        this.lawSubSort = request.getLawSubSort();
    }
}
