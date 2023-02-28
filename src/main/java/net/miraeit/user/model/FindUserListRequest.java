package net.miraeit.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.fx.listing.model.CommonPagingConditions;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FindUserListRequest extends CommonPagingConditions {

	@ApiModelProperty(example = "조직명")
	private String fullOrgNm;

	@ApiModelProperty(example = "사용자명")
	private String userNm;

	@ApiModelProperty(example = "권한 그룹 ID ( 1: 관리자 그룹, 2: 사용자 그룹 )")
	private Integer authGroupId;
}
