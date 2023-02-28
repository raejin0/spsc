package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInformation {
    @ApiModelProperty(example = "파일 ID")
    private Integer atchFileId;
    @ApiModelProperty(example = "파일순번")
    private Integer fileSn;
    @ApiModelProperty(example = "파일명")
    private String name;
    @ApiModelProperty(example = "링크 주소")
    private String url;
    @ApiModelProperty(example = "파일타입 F:파일, L:링크")
    private String atchTy;
}
