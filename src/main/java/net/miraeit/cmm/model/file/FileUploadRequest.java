package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadRequest {

	@ApiModelProperty(example = "파일 ID")
	private Integer atchFileId;

	@ApiModelProperty(example = "파일순번")
	private Integer fileSn;

	@ApiModelProperty(example = "url")
	private String url;

	@ApiModelProperty(example = "url 명칭")
	private String name;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "파일타입")
	private String atchTy;

}
