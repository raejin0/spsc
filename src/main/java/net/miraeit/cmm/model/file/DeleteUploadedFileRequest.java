package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUploadedFileRequest {
	@ApiModelProperty(example = "파일 ID")
	private Integer atchFileId;

	@ApiModelProperty(example = "파일순번")
	private Integer fileSn;

	@ApiModelProperty(example = "파일타입")
	private String atchTy;

}
