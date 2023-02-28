package net.miraeit.cmm.model.file;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponse {
	private Integer atchFileId;
	private Integer fileSn;
	private String orignlFileNm;
}
