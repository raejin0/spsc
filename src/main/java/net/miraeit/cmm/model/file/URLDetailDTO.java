package net.miraeit.cmm.model.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class URLDetailDTO {

	private Integer atchFileId;
	private Integer fileSn;
	private String fileStreCours;
	private String streFileNm;
	private String orignlFileNm;
	private String fileExtsn;
//	private String fileCn;
//	private String fileSize;
	private String atchTy;

}
