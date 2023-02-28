package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FindUserListResponse {

	@ApiModelProperty(example = "사용자 ID")
	private String id;

	@ApiModelProperty(example = "조직 ID")
	private String orgId;

	@ApiModelProperty(example = "조직명")
	private String orgNm;

	@ApiModelProperty(example = "전체 조직명")
	private String fullOrgNm;

	@ApiModelProperty(example = "사용자명")
	private String userNm;

	@ApiModelProperty(example = "사용자 유형(I: 연계)")
	private String userTy;

	@ApiModelProperty(example = "이메일")
	private String email;

	@ApiModelProperty(example = "권한 그룹 ID ( 1: 관리자 그룹, 2: 사용자 그룹 )")
	private Integer authGroupId;

	@ApiModelProperty(example = "재직상태 ( C:재직, H:휴직, J:정직, M:전출, T:퇴직 )")
	private String stateCd;

//	@JsonFormat(pattern = Format.DATE)
//	private LocalDateTime createdDate; // SqlMapException: No type handler could be found 에러 발생
	@ApiModelProperty(example = "등록일")
	private String createdDate;

	@ApiModelProperty(example = "직급코드")
	private String jikgubCd;

	@ApiModelProperty(example = "직급명")
	private String jikgubNm;

	@ApiModelProperty(example = "직군코드")
	private String jikgunCd;

	@ApiModelProperty(example = "직군명")
	private String jikgunNm;

	@ApiModelProperty(example = "직위코드")
	private String posCd;

	@ApiModelProperty(example = "직위명(직책)")
	private String posNm;

	@ApiModelProperty(example = "직무코드")
	private String jikmuCd;

	@ApiModelProperty(example = "직무명")
	private String jikmuNm;
}
