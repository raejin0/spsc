package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "파일 ID")
	private Integer atchFileId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "파일순번")
	private Integer fileSn;
}
