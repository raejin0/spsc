package net.miraeit.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginVO {

	@ApiModelProperty(example = "사용자 아이디")
	private String id;              // 사용자 아이디

	@ApiModelProperty(example = "사용자이름")
	private String username;

//	@ApiModelProperty(example = "비밀번호")
//	private String password;

	@ApiModelProperty(example = "부서 id")
	private String orgId;

	@ApiModelProperty(example = "부서명")
	private String orgNm;

	@ApiModelProperty(example = "조직유형(1: 이행조직 불가능, 3: 이행조직 가능)")
	private Integer orgType;

	@ApiModelProperty(example = "권한 그룹 아이디")
	private Integer authGroupId;

	@ApiModelProperty(example = "권한 그룹명")
	private String authGroupNm;

	@ApiModelProperty(example = "주관부서 여부")
	private String mngYn;

	@ApiModelProperty(example = "상위 조직 코드")
	private String parentOrgCd;
}
