package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

	@ApiModelProperty(example = "사용자 id")
	private String id;

	@ApiModelProperty(example = "조직 id")
	private String orgId;

	@ApiModelProperty(example = "사용자명")
	private String userNm;

	@ApiModelProperty(example = "비밀번호")
	private String password;

	@ApiModelProperty(example = "권한 그룹 ID ( 1: 관리자 그룹, 2: 사용자 그룹 )")
	private int authGroupId;

}
