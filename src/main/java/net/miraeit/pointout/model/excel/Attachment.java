package net.miraeit.pointout.model.excel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.cmm.model.file.FileDetailDTO;

import java.io.File;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends FileDetailDTO {
	private Integer row;
	private Integer col;
	private byte[] bytes;
	private File file;

}
