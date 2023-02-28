package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class ValidateTokenRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "JWT")
	private String token;
}
