package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostRequest {

	@ApiModelProperty(example = "게시글 ID")
	private Integer nttId;

	@ApiModelProperty(example = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실")
	private Integer bbsId;

	@ApiModelProperty(example = "첨부파일 ID")
	private Integer atchFileId;
}
