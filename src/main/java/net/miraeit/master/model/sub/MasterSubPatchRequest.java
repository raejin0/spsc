package net.miraeit.master.model.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterSubPatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 소분류 ID")
    private Integer lawSubId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 대분류 ID")
    private Integer lawMainId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 소분류 번호")
    private Integer lawSubNo;
    @ApiModelProperty(example = "법령 소분류 이름")
    private String lawSubNm;
    @ApiModelProperty(example = "비고")
    private String lawSubEtc;
    @ApiModelProperty(example = "순번")
    private Integer lawSubSort;
}
