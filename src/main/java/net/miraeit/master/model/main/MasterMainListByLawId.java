package net.miraeit.master.model.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterMainListByLawId {
    @ApiModelProperty(example = "법령 대분류 ID")
    private Integer lawMainId;
    @ApiModelProperty(example = "법령 대분류 번호")
    private Integer lawMainNo;
    @ApiModelProperty(example = "법령 대분류 이름")
    private String lawMainNm;
    @ApiModelProperty(example = "재해구분 코드")
    private String disClaCode;
}
