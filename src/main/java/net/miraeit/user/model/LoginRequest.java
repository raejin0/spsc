package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "사용자 ID")
	private String id;

	@NotNull(message = "V000")
	@ApiModelProperty(example = "비밀번호")
	private String password;

	public LoginRequest(String userId) {
		this.id = userId;
	}
}
