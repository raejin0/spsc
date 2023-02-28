package net.miraeit.cmm.model.equipment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.miraeit.checklist.model.ChecklistDepartment;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetDepartment {
	@ApiModelProperty(example = "조직코드")
    private String orgCd;
    @ApiModelProperty(example = "조직명")
    private String orgNm;
    @ApiModelProperty(example = "전체 조직명")
    private String fullOrgNm;
}
