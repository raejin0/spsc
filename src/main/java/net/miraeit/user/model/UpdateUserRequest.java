package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "사용자 ID")
	private String id;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "비밀번호")
	private String password;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "권한 그룹 ID ( 1: 관리자 그룹, 2: 사용자 그룹 )")
	private Integer authGroupId;

}
