package net.miraeit.cmm.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CMMCodesGetResponse {
    @ApiModelProperty(example="코드 ID")
    private String codeId;
    @ApiModelProperty(example = "코드")
    private String code;
    @ApiModelProperty(example = "코드명")
    private String codeNm;
}
