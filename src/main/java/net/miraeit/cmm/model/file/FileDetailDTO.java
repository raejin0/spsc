package net.miraeit.cmm.model.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailDTO {

	private Integer atchFileId;
	private Integer fileSn;
	private String fileStreCours;
	private String streFileNm;
	private String orignlFileNm;
	private String fileExtsn;
	private String fileCn;
	private Integer fileSize;
	private String atchTy;
}
