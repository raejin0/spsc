package net.miraeit.master.model.text;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterTextPatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 내용 아이디")
    private Integer lawCttsId;
    @ApiModelProperty(example = "법령 내용")
    private String text;
    @ApiModelProperty(example = "법령 내용 정렬")
    private Integer lawCttsSort;
    @ApiModelProperty(example = "법령 소분류 아이디")
    private Integer lawSubId;
}
