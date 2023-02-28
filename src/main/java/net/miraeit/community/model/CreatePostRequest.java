package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CreatePostRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실")
	private Integer bbsId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시물 제목")
	private String nttSj;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시물 내용")
	private String nttCn;

	@ApiModelProperty(example = "파일 ID")
	private Integer atchFileId;

	public int getAtchFileId() {
		return (atchFileId==null) ? 0 : atchFileId;
	}
}