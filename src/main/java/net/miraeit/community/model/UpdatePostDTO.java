package net.miraeit.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDTO {

	private Integer nttId;
	private Integer bbsId;
	private String nttSj;
	private String nttCn;
	private Integer atchFileId;
	private String userId;

	public UpdatePostDTO(UpdatePostRequest updatePostRequest, String userId) {
		this.nttId = updatePostRequest.getNttId();
		this.bbsId = updatePostRequest.getBbsId();
		this.nttSj = updatePostRequest.getNttSj();
		this.nttCn = updatePostRequest.getNttCn();
		this.atchFileId = updatePostRequest.getAtchFileId();
		this.userId = userId;
	}
}
