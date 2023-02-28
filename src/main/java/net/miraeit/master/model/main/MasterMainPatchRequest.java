package net.miraeit.master.model.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MasterMainPatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 대분류 ID")
    private Integer lawMainId;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 대분류 번호")
    private Integer lawMainNo;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "법령 대분류 이름")
    private String lawMainNm;
    @NotNull(message = "V000")
    @ApiModelProperty(example = "재해구분 코드")
    private String disClaCode;
}
