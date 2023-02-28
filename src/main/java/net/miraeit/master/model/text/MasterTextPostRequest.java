package net.miraeit.master.model.text;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterTextPostRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 소분류 아이디")
    private Integer lawSubId;
    @ApiModelProperty(example = "법령 내용")
    private String text;
    @ApiModelProperty(example = "법령 내용 정렬")
    private Integer lawCttsSort;
    @ApiModelProperty(notes = "법령내용 ID", hidden = true)
    private Integer lawCttsId;
    @ApiModelProperty(notes = "순서상 바로 앞의 법령내용 ID")
    private Integer nextOf;
}
