package net.miraeit.master.model.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterSubPostRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 소분류 번호")
    private Integer lawSubNo;
    @ApiModelProperty(example = "법령 소분류 이름")
    private String lawSubNm;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 대분류 ID")
    private Integer lawMainId;
    @ApiModelProperty(example = "비고")
    private String lawSubEtc;
    @ApiModelProperty(example = "순번")
    private Integer lawSubSort;
    @ApiModelProperty(notes = "소분류 ID", hidden = true)
    private Integer lawSubId;
    @ApiModelProperty(notes = "순서상 바로 앞의 소분류 ID")
    private Integer nextOf;
}
