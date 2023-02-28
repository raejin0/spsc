package net.miraeit.cmm.model.file;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadDTO {

	private String fileStreCours;
	private String orignlFileNm;
	private String fileExtsn;

}
