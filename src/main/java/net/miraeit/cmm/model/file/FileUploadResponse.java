package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadResponse {

	@ApiModelProperty(example = "파일 ID")
	private Integer atchFileId;

	@ApiModelProperty(example = "파일순번")
	private Integer fileSn;

	@ApiModelProperty(example = "원파일명")
	private String orignlFileNm;

	@ApiModelProperty(example = "url")
	private String url;

	@ApiModelProperty(example = "파일타입")
	private String atchTy;

	public FileUploadResponse(FileDetailDTO fileDetail) {
		this.atchFileId = fileDetail.getAtchFileId();
		this.fileSn = fileDetail.getFileSn();
		this.orignlFileNm = fileDetail.getOrignlFileNm();
		this.atchTy = fileDetail.getAtchTy();

		if ( fileDetail.getAtchTy().equals("L")) this.url = fileDetail.getStreFileNm(); // 링크 일 경우에만 url 입력
	}
}
