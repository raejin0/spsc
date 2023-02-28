package net.miraeit.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResponse {

	@ApiModelProperty(example = "게시물 ID")
	private Integer nttId;
}
