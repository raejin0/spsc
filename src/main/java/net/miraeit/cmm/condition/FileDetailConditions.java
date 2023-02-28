package net.miraeit.cmm.condition;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDetailConditions {
	private Integer atchFileId;
	private List<Integer> atchFileIds;
	private Integer fileSn;

	public FileDetailConditions(int atchFileId) {
		this.atchFileId = atchFileId;
	}

	public void addAtchFileId(int atchFileId) {
		if(atchFileIds==null) atchFileIds = new ArrayList<>();
		atchFileIds.add(atchFileId);
	}
}
