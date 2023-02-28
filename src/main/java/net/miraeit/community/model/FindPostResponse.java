package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.model.BaseModel;
import net.miraeit.cmm.model.file.UploadFileResponse;

import java.util.List;

@Getter
@Setter
public class FindPostResponse extends BaseModel {

	@ApiModelProperty(example = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실")
	private Integer bbsId;

	@ApiModelProperty(example = "게시물 ID")
	private Integer nttId;

	@ApiModelProperty(example = "게시물 제목")
	private String nttSj;

	@ApiModelProperty(example = "게시물 내용")
	private String nttCn;

	@ApiModelProperty(example = "첨부파일 ID")
	private Integer atchFileId;

	@ApiModelProperty(example = "파일상세정보 목록")
	private List<UploadFileResponse> uploadFiles;

	@ApiModelProperty(example = "등록자ID")
	private String frstRegisterId;
}
