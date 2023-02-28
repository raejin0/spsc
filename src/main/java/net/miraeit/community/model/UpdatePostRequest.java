package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시글 ID")
	private Integer nttId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실")
	private Integer bbsId;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시물 제목")
	private String nttSj;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "게시물 내용")
	private String nttCn;

	@ApiModelProperty(example = "첨부파일 ID")
	private Integer atchFileId;

	@ApiModelProperty(example = "파일 삭제 여부")
	private boolean doDeleteFile;
}
