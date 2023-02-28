package net.miraeit.cmm.model.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementGetResponse {
    @ApiModelProperty(example = "조직 코드")
    private String orgCd;
    @ApiModelProperty(example = "조직 명")
    private String orgNm;
    @ApiModelProperty(example = "전체 조직 명")
    private String fullOrgNm;
}
