package net.miraeit.master.model.text;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterTextPostResponse {
    @ApiModelProperty(example = "법령 소분류 아이디")
    private Integer lawSubId;
    @ApiModelProperty(example = "법령 내용")
    private String text;
    @ApiModelProperty(example = "법령 내용 정렬")
    private Integer lawCttsSort;
    @ApiModelProperty(example = "텍스트 ID")
    private Integer lawCttsId;

    public MasterTextPostResponse(Integer lawCttsId, MasterTextPostRequest request){
        this.lawSubId = request.getLawSubId();
        this.text = request.getText();
        this.lawCttsSort = request.getLawCttsSort();
        this.lawCttsId = lawCttsId;
    }
}
