package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

	@ApiModelProperty(example = "사용자 아이디")
	private String id;

	@ApiModelProperty(example = "사용자이름")
	private String username;

	@ApiModelProperty(example = "부서 id")
	private String orgId;

	@ApiModelProperty(example = "부서 코드")
	private String orgCd;

	@ApiModelProperty(example = "부서명")
	private String  orgNm;

	@ApiModelProperty(example = "조직유형(1: 이행조직 불가능, 3: 이행조직 가능)")
	private Integer orgType;

	@ApiModelProperty(example = "상위 조직 코드")
	private String parentOrgCd;

	@ApiModelProperty(example = "권한 그룹 아이디")
	private Integer authGroupId;

	@ApiModelProperty(example = "권한 그룹명")
	private String authGroupNm;

	@ApiModelProperty(example = "주관부서 여부")
	private String mngYn;

	@ApiModelProperty(example = "JSON Web Token")
	private String token;

	@ApiModelProperty(example = "ssoId")
	private String ssoId;

	@ApiModelProperty(example = "retCode")
	private String retCode;

	@ApiModelProperty(example = "loginErrorMessage")
	private String loginErrorMessage;

	public LoginResponse(LoginVO loginVO, String token) {
		this.id = loginVO.getId();
		this.username = loginVO.getUsername();
		this.orgId = loginVO.getOrgId();
		this.orgCd = loginVO.getOrgId();
		this.orgNm = loginVO.getOrgNm();
		this.orgType = loginVO.getOrgType();
		this.parentOrgCd = loginVO.getParentOrgCd();
		this.authGroupId = loginVO.getAuthGroupId();
		this.authGroupNm = loginVO.getAuthGroupNm();
		this.mngYn = loginVO.getMngYn();
		this.token = token;
	}
}
