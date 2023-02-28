package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindPostListResponse {

	@ApiModelProperty(example = "조회한 게시물 목록")
	private List<FindPostResponse> noticeList;

	@ApiModelProperty(example = "조회 목록 총 건수")
	private int totalCnt;

}
