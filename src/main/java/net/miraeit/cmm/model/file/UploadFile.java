package net.miraeit.cmm.model.file;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

	private Integer atchFileId;
	private Integer fileSn;
	private String fileStreCours;
	private String streFileNm;
	private String orignlFileNm;
	private String fileExtsn;
	private Long fileSize;
	private String atchTy;
}
